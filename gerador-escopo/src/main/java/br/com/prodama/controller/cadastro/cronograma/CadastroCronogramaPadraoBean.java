package br.com.prodama.controller.cadastro.cronograma;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.TreeNode;

import br.com.prodama.enun.AnaliticoSintetico;
import br.com.prodama.enun.FormatoExecucao;
import br.com.prodama.enun.ResponsavelExecucao;
import br.com.prodama.model.cadastro.cronograma.AnexoAtividadeHoraPadrao;
import br.com.prodama.model.cadastro.cronograma.AtividadeHoraPadrao;
import br.com.prodama.model.cadastro.cronograma.CronogramaPadrao;
import br.com.prodama.model.cadastro.cronograma.DocAtividadeHoraPadrao;
import br.com.prodama.model.cadastro.geral.Equipe;
import br.com.prodama.model.cadastro.geral.NivelEquipe;
import br.com.prodama.model.cadastro.produto.Produto;
import br.com.prodama.repository.cadastro.cronograma.AnexosAtividadesHoraPadrao;
import br.com.prodama.repository.cadastro.cronograma.AtividadesHoraPadrao;
import br.com.prodama.repository.cadastro.cronograma.CronogramasPadrao;
import br.com.prodama.repository.cadastro.cronograma.DocsAtividadesHoraPadrao;
import br.com.prodama.repository.cadastro.geral.Equipes;
import br.com.prodama.repository.cadastro.geral.NiveisEquipe;
import br.com.prodama.repository.cadastro.produto.Produtos;
import br.com.prodama.service.NegocioException;
import br.com.prodama.service.cadastro.cronograma.CadastroAnexoAtividadePadrao;
import br.com.prodama.service.cadastro.cronograma.CadastroCromogramaPadrao;
import br.com.prodama.service.cadastro.cronograma.CadastroDocAtividadePadrao;
import br.com.prodama.util.FacesMessages;
import br.com.prodama.util.componentes.ConversorHora;

@Named
@ViewScoped
public class CadastroCronogramaPadraoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private FacesMessages messages;

	@Inject
	private CadastroCromogramaPadrao cadastroCromogramaPadrao;
	
	
	@Inject
	private CadastroAnexoAtividadePadrao cadastroAnexo;
	
	@Inject
	private CadastroDocAtividadePadrao cadastroDocumento;

	@Inject
	private CronogramasPadrao cronogramasPadrao;


	@Inject
	private Produtos produtos;

	@Inject
	private Equipes equipes;

	@Inject
	private NiveisEquipe niveisEquipe;

	@Inject
	private AtividadesHoraPadrao atividades;

	@Inject
	private DocsAtividadesHoraPadrao documentos;

	@Inject
	private AnexosAtividadesHoraPadrao anexos;

	@Inject
	private ConversorHora conversorHora;

	private CronogramaPadrao cronogramaEdicao;
	private CronogramaPadrao cronogramaSelecionado;
	private DocAtividadeHoraPadrao documentoSelecionado;
	private List<CronogramaPadrao> todosCronogramas;
	private List<CronogramaPadrao> filtroCronogramas;
	private AtividadeHoraPadrao atividadeEdicao;
	private AtividadeHoraPadrao atividadeSelecionada;
	private TreeNode selectedNode;
	private DocAtividadeHoraPadrao documentoEdicao;
	private AnexoAtividadeHoraPadrao anexoEdicao;

	private List<Equipe> todasEquipes;
	private List<NivelEquipe> todosNiveisEquipe;

	private List<Produto> todosProdutos;
	private List<DocAtividadeHoraPadrao> todosDocumentos;
	private List<AnexoAtividadeHoraPadrao> todosAnexos;

	private TreeNode raiz;

	private boolean habilitar = false;
	private String horaString;
	private StreamedContent arquivoDownload;

	@PostConstruct
	public void prepararNovoCadastro() {
		this.raiz = new DefaultTreeNode("Raiz", null);
		todosProdutos = produtos.todos();
		todasEquipes = equipes.todos();
		todosNiveisEquipe = niveisEquipe.todos();
		atividadeEdicao = new AtividadeHoraPadrao();
		if (cronogramaEdicao == null) {
			cronogramaEdicao = new CronogramaPadrao();
			atividadeEdicao = new AtividadeHoraPadrao();
			atividadeEdicao.setAnaliticoSitetico(AnaliticoSintetico.SINTETICO);
		}
	}

	public void prepararNovoCadastroDocumento() {
		documentoEdicao = new DocAtividadeHoraPadrao();
	}

	public void salvarAtividade() {
		try {
		    cronogramaEdicao =  this.cronogramasPadrao.pesquisaPorId(cronogramaEdicao.getCodigo());
			atividadeEdicao.setCronogramaPadrao(cronogramaEdicao);

			if (atividadeEdicao.getAnaliticoSitetico() == AnaliticoSintetico.ANALITICO) {
				atividadeEdicao.setDescricao(atividadeEdicao.getDescricao().toLowerCase());
			} else {
				atividadeEdicao.setDescricao(atividadeEdicao.getDescricao().toUpperCase());
			}
			atividadeEdicao.setHoraAtividade(conversorHora.converteHoraMinuto(this.horaString));
			cronogramaEdicao.getListaAtividadesHorasPadroes().add(atividadeEdicao);
			this.cadastroCromogramaPadrao.salvar(cronogramaEdicao);
			cronogramaEdicao = this.cronogramasPadrao.pesquisaPorId(cronogramaEdicao.getCodigo());
			//recarregarArvore(cronogramaEdicao);
			carregaArvore(cronogramaEdicao);
			habilitar = false;
			messages.info("Atividade salva com sucesso!");
			RequestContext.getCurrentInstance().update(Arrays.asList("msgs", "frmTree", "painel-tab"));
			// RequestContext.getCurrentInstance().execute("location.reload()");
			horaString = "";
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			if (mensagem.getDetail() != null) {
				messages.error("Erro ao salvar atividade! \n Motivo:" + mensagem.getDetail());
				RequestContext.getCurrentInstance().update(Arrays.asList("msgs", "frmTree:treeTableAtividades"));
			} else {
				messages.error("Erro ao salvar atividade! \n Motivo:" + mensagem.getDetail());
				RequestContext.getCurrentInstance().update(Arrays.asList("frmTree:treeTableAtividades"));
			}
		}
	}

	public void salvarDocumento() {
		documentoEdicao.setAtividadeHoraPadrao(atividadeEdicao);
		atividadeEdicao.getListaDocAtividadesHorasPadroes().add(documentoEdicao);
		try {
			this.cadastroDocumento.salvar(documentoEdicao);
			cronogramaEdicao = this.cronogramasPadrao.pesquisaPorId(cronogramaEdicao.getCodigo());
			messages.info("Documento salvo com sucesso!");
			todosDocumentos = documentos.todos(atividadeEdicao);
			prepararNovoCadastroDocumento();
			RequestContext.getCurrentInstance().update(Arrays.asList("painel-tab:frmDoc:documento-table"));
		} catch (NegocioException e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao salvar documento! \n Motivo:" + mensagem.getDetail());
			RequestContext.getCurrentInstance().update(Arrays.asList("painel-tab:frmDoc:documento-table"));
			e.printStackTrace();
		}

	}

	public void carregarAnexos(FileUploadEvent event) {
		cronogramaEdicao = this.cronogramasPadrao.pesquisaPorId(cronogramaEdicao.getCodigo());
		if (event.getFile() != null) {
			byte[] conteudo = event.getFile().getContents();
			anexoEdicao = new AnexoAtividadeHoraPadrao();
			anexoEdicao.setDescricao(event.getFile().getFileName());
			anexoEdicao.setArquivo(conteudo);
			anexoEdicao.setDocAtividadeHoraPadrao(documentoEdicao);
			documentoEdicao.getListaAnexoAtividadesHorasPadroes().add(anexoEdicao);
			try {
				this.cadastroAnexo.salvar(anexoEdicao);
				cronogramaEdicao = this.cronogramasPadrao.pesquisaPorId(cronogramaEdicao.getCodigo());
				messages.info("Documento salvo com sucesso!");
				todosAnexos = anexos.todos(documentoEdicao);
				RequestContext.getCurrentInstance().update(Arrays.asList("painel-tab:frmDoc:documento-table"));
			} catch (Exception e) {
				messages.info("Erro ao salvar documento!");
				FacesMessage mensagem = new FacesMessage(e.getMessage());
				messages.error("Erro ao salvar documento! \n Motivo:" + mensagem.getDetail());
			}
		}
	}
	
	public void baixarAnexo() throws IOException {
		 Faces.sendFile(anexoEdicao.getArquivo(), anexoEdicao.getDescricao(), true);
	}
	
	public void removerAnexo(){
		anexoEdicao.setDocAtividadeHoraPadrao(documentoEdicao);
		documentoEdicao.getListaAnexoAtividadesHorasPadroes().remove(anexoEdicao);
		try {
			this.cadastroAnexo.excluir(anexoEdicao);
			cronogramaEdicao = this.cronogramasPadrao.pesquisaPorId(cronogramaEdicao.getCodigo());
			messages.info("Anexo excluido com sucesso!");
			todosAnexos = anexos.todos(documentoEdicao);
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao remover anexo! \n Motivo:" + mensagem.getDetail());
		}
	}


	public void addSintetico() {
		atividadeEdicao = new AtividadeHoraPadrao();
		atividadeEdicao.setAnaliticoSitetico(AnaliticoSintetico.SINTETICO);
		if (atividadeSelecionada != null) {
			atividadeEdicao.setAtividadeHoraPai(atividadeSelecionada);
		}
		habilitar = true;
		horaString = "";
		RequestContext.getCurrentInstance().update(Arrays.asList("painel-tab:frmAtividade","painel-tab:frmAtividade:tipo"));
	}
	
	public void addSinteticoAvulso() {
		atividadeEdicao = new AtividadeHoraPadrao();
		atividadeEdicao.setAnaliticoSitetico(AnaliticoSintetico.SINTETICO);
		habilitar = true;
		horaString = "";
		RequestContext.getCurrentInstance().update(Arrays.asList("painel-tab:frmAtividade","painel-tab:frmAtividade:hora"));
	}

	public void addAnalitico() {
		atividadeEdicao = new AtividadeHoraPadrao();
		atividadeEdicao.setAnaliticoSitetico(AnaliticoSintetico.ANALITICO);
		atividadeEdicao.setAtividadeHoraPai(atividadeSelecionada);
		habilitar = true;
		horaString = "";
		RequestContext.getCurrentInstance().update(Arrays.asList("painel-tab:frmAtividade"));
	}

	public void excluirAtividade() {
		try {
			cronogramaEdicao.getListaAtividadesHorasPadroes().remove(atividadeEdicao);
			this.cadastroCromogramaPadrao.salvar(cronogramaEdicao);
			todosProdutos = produtos.todos();
			todasEquipes = equipes.todos();
			todosNiveisEquipe = niveisEquipe.todos();
			carregaArvore(cronogramaEdicao);
			messages.info("Atividade excluída com sucesso!");
			habilitar = false;
			horaString = "";
			RequestContext.getCurrentInstance()	.update(Arrays.asList("msgs", "frmTree:treeTableAtividades", "painel-tab"));
			todosDocumentos = documentos.todos(atividadeEdicao);
			RequestContext.getCurrentInstance().update(Arrays.asList("painel-tab", "painel-tab:frmDoc"));
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao excluir atividade! \n Motivo:" + mensagem);
			RequestContext.getCurrentInstance().update(Arrays.asList("msgs", "frmTree:treeTableAtividades"));
		}

	}

	public void excluirDocumento() {
		documentoEdicao.setAtividadeHoraPadrao(atividadeEdicao);
		atividadeEdicao.getListaDocAtividadesHorasPadroes().remove(documentoEdicao);
		try {
			this.cadastroCromogramaPadrao.salvar(cronogramaEdicao);
			messages.info("Documento excluído com sucesso!");
			habilitar = false;
			horaString = "";
			todosDocumentos = documentos.todos(atividadeEdicao);
			prepararNovoCadastroDocumento();
			cronogramaEdicao = this.cronogramasPadrao.pesquisaPorId(cronogramaEdicao.getCodigo());
			RequestContext.getCurrentInstance().update(Arrays.asList("painel-tab:frmDoc:documento-table"));
		} catch (NegocioException e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao excluir atividade! \n Motivo:" + mensagem);
			RequestContext.getCurrentInstance().update(Arrays.asList("painel-tab:frmDoc:documento-table"));
		}
	}

	public void carregaArvore(CronogramaPadrao cronogramaEdicao) {
		this.cronogramaEdicao = cronogramasPadrao.pesquisaPorId(cronogramaEdicao.getCodigo());
		List<AtividadeHoraPadrao> atividadesRaizes = atividades.raizes(this.cronogramaEdicao);
		this.raiz = new DefaultTreeNode("Raiz", null);
		adicionarNos(atividadesRaizes, this.raiz);
	}

	private void adicionarNos(List<AtividadeHoraPadrao> atividadesRaizes, TreeNode pai) {
		for (AtividadeHoraPadrao atividade : atividadesRaizes) {
			TreeNode no = new DefaultTreeNode(atividade, pai);
			adicionarNos(atividades.filhos(atividade), no);
		}
	}
	
	public void recarregarArvore(CronogramaPadrao cronogramaEdicao){
	 	List<AtividadeHoraPadrao> atividadesLoop=  this.cronogramaEdicao.getListaAtividadesHorasPadroes();
	 	for(AtividadeHoraPadrao atividade : atividadesLoop){
	 		Long x = (atividades.somaFilhos(atividade));
	 		Integer i = x != null ? x.intValue() : null;
	 		atividade.setHoraAtividade(i);
	 	}
	 	try {
			this.cadastroCromogramaPadrao.salvar(cronogramaEdicao);
		} catch (NegocioException e) {
			e.printStackTrace();
		}
	}
	

	public void listaNiveisEquipes(AjaxBehaviorEvent event) {
		carregarNiveisEquipes();
	}

	private void carregarNiveisEquipes() {
		todosNiveisEquipe = niveisEquipe.niveisPorEquipe(this.atividadeEdicao.getEquipe());
	}

	public void consultar() {
		todosCronogramas = cronogramasPadrao.todos();
	}

	public FacesMessages getMessages() {
		return messages;
	}

	public void setMessages(FacesMessages messages) {
		this.messages = messages;
	}

	public CadastroCromogramaPadrao getCadastroCromogramaPadrao() {
		return cadastroCromogramaPadrao;
	}

	public void setCadastroCromogramaPadrao(CadastroCromogramaPadrao cadastroCromogramaPadrao) {
		this.cadastroCromogramaPadrao = cadastroCromogramaPadrao;

	}

	public CronogramasPadrao getCronogramasPadrao() {
		return cronogramasPadrao;
	}

	public void setCronogramasPadrao(CronogramasPadrao cronogramasPadrao) {
		this.cronogramasPadrao = cronogramasPadrao;
	}

	public Produtos getProdutos() {
		return produtos;
	}

	public void setProdutos(Produtos produtos) {
		this.produtos = produtos;
	}

	public CronogramaPadrao getCronogramaEdicao() {
		return cronogramaEdicao;
	}

	public void setCronogramaEdicao(CronogramaPadrao cronogramaEdicao) {
		this.cronogramaEdicao = cronogramaEdicao;

		if (cronogramaEdicao != null) {
			this.carregaArvore(cronogramaEdicao);
		} else {
			cronogramaEdicao = new CronogramaPadrao();
		}
	}

	public CronogramaPadrao getCronogramaSelecionado() {
		return cronogramaSelecionado;
	}

	public void setCronogramaSelecionado(CronogramaPadrao cronogramaSelecionado) {
		this.cronogramaSelecionado = cronogramaSelecionado;
	}

	public List<CronogramaPadrao> getTodosCronogramas() {
		return todosCronogramas;
	}

	public void setTodosCronogramas(List<CronogramaPadrao> todosCronogramas) {
		this.todosCronogramas = todosCronogramas;
	}

	public List<CronogramaPadrao> getFiltroCronogramas() {
		return filtroCronogramas;
	}

	public void setFiltroCronogramas(List<CronogramaPadrao> filtroCronogramas) {
		this.filtroCronogramas = filtroCronogramas;
	}

	public List<Produto> getTodosProdutos() {
		return todosProdutos;
	}

	public void setTodosProdutos(List<Produto> todosProdutos) {
		this.todosProdutos = todosProdutos;
	}

	public TreeNode getRaiz() {
		return raiz;
	}

	public void setRaiz(TreeNode raiz) {
		this.raiz = raiz;
	}

	public AtividadeHoraPadrao getAtividadeEdicao() {
		if (atividadeEdicao.getHoraAtividade() != null) {
			horaString = (conversorHora.converteMinutoHora(atividadeEdicao.getHoraAtividade()));
		}
		return atividadeEdicao;
	}

	public void setAtividadeEdicao(AtividadeHoraPadrao atividadeEdicao) {
		this.atividadeEdicao = atividadeEdicao;
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
		if (selectedNode != null) {
			atividadeSelecionada = (AtividadeHoraPadrao) selectedNode.getData();
			atividadeEdicao = (AtividadeHoraPadrao) selectedNode.getData();
			habilitar = true;
			todosDocumentos = documentos.todos(atividadeEdicao);
			RequestContext.getCurrentInstance().update(Arrays.asList("painel-tab:frmAtividade", "painel-tab:frmDoc"));
			RequestContext.getCurrentInstance().execute("PF('tab').select(0);");
		}
	}

	public AnaliticoSintetico[] getTipoList() {
		return AnaliticoSintetico.values();
	}

	public List<FormatoExecucao> getFormatoList() {
		return Arrays.asList(FormatoExecucao.values());
	}

	public List<ResponsavelExecucao> getResponsavelList() {
		return Arrays.asList(ResponsavelExecucao.values());
	}

	public Equipes getEquipes() {
		return equipes;
	}

	public List<Equipe> getTodasEquipes() {
		return todasEquipes;
	}

	public void setTodasEquipes(List<Equipe> todasEquipes) {
		this.todasEquipes = todasEquipes;
	}

	public void setEquipes(Equipes equipes) {
		this.equipes = equipes;
	}

	public List<NivelEquipe> getTodosNiveisEquipe() {
		return todosNiveisEquipe;
	}

	public void setTodosNiveisEquipe(List<NivelEquipe> todosNiveisEquipe) {
		this.todosNiveisEquipe = todosNiveisEquipe;
	}

	public AtividadeHoraPadrao getAtividadeSelecionada() {
		return atividadeSelecionada;
	}

	public void setAtividadeSelecionada(AtividadeHoraPadrao atividadeSelecionada) {
		this.atividadeSelecionada = atividadeSelecionada;
	}

	public boolean isHabilitar() {
		return habilitar;
	}

	public void setHabilitar(boolean habilitar) {
		this.habilitar = habilitar;
	}

	public String getHoraString() {
		return horaString;
	}

	public void setHoraString(String horaString) {
		this.horaString = horaString;
	}

	public DocAtividadeHoraPadrao getDocumentoEdicao() {
		return documentoEdicao;
	}

	public void setDocumentoEdicao(DocAtividadeHoraPadrao documentoEdicao) {
		if (documentoEdicao != null) {
			todosAnexos = anexos.todos(documentoEdicao); 
		}
		this.documentoEdicao = documentoEdicao;
	}

	public DocAtividadeHoraPadrao getDocumentoSelecionado() {
		return documentoSelecionado;
	}

	public void setDocumentoSelecionado(DocAtividadeHoraPadrao documentoSelecionado) {
		this.documentoSelecionado = documentoSelecionado;
	}

	public List<DocAtividadeHoraPadrao> getTodosDocumentos() {
		return todosDocumentos;
	}

	public void setTodosDocumentos(List<DocAtividadeHoraPadrao> todosDocumentos) {
		this.todosDocumentos = todosDocumentos;
	}

	public AnexoAtividadeHoraPadrao getAnexoEdicao() {
		return anexoEdicao;
	}

	public void setAnexoEdicao(AnexoAtividadeHoraPadrao anexoEdicao) {
		this.anexoEdicao = anexoEdicao;
	}

	public List<AnexoAtividadeHoraPadrao> getTodosAnexos() {
		return todosAnexos;
	}

	public void setTodosAnexos(List<AnexoAtividadeHoraPadrao> todosAnexos) {
		this.todosAnexos = todosAnexos;
	}

	public StreamedContent getArquivoDownload() {
		return arquivoDownload;
	}

	public void setArquivoDownload(StreamedContent arquivoDownload) {
		this.arquivoDownload = arquivoDownload;
	}

	
}
