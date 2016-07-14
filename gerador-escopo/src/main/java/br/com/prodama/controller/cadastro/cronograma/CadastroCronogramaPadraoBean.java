package br.com.prodama.controller.cadastro.cronograma;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;


import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import br.com.prodama.enun.AnaliticoSintetico;
import br.com.prodama.enun.FormatoExecucao;
import br.com.prodama.enun.ResponsavelExecucao;
import br.com.prodama.model.cadastro.cronograma.AtividadeHoraPadrao;
import br.com.prodama.model.cadastro.cronograma.CronogramaPadrao;
import br.com.prodama.model.cadastro.geral.Equipe;
import br.com.prodama.model.cadastro.geral.NivelEquipe;
import br.com.prodama.model.cadastro.produto.Produto;
import br.com.prodama.repository.cadastro.cronograma.AtividadesHoraPadrao;
import br.com.prodama.repository.cadastro.cronograma.CronogramasPadrao;
import br.com.prodama.repository.cadastro.geral.Equipes;
import br.com.prodama.repository.cadastro.geral.NiveisEquipe;
import br.com.prodama.repository.cadastro.produto.Produtos;
import br.com.prodama.service.cadastro.cronograma.CadastroCromogramaPadrao;
import br.com.prodama.util.FacesMessages;

@Named
@javax.faces.view.ViewScoped
public class CadastroCronogramaPadraoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private FacesMessages messages;

	@Inject
	private CadastroCromogramaPadrao cadastroCromogramaPadrao;
	

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

	private CronogramaPadrao cronogramaEdicao;
	private CronogramaPadrao cronogramaSelecionado;
	private List<CronogramaPadrao> todosCronogramas;
	private List<CronogramaPadrao> filtroCronogramas;
	private AtividadeHoraPadrao atividadeEdicao;
	private AtividadeHoraPadrao atividadeSelecionada;
	private TreeNode selectedNode;
	
	private List<Equipe> todasEquipes;
	private List<NivelEquipe> todosNiveisEquipe;
	
	private List<Produto> todosProdutos;
	
	private TreeNode raiz;
	
	private boolean habilitar = false;
	
	@PostConstruct
	public void prepararNovoCadastro() {
		this.raiz = new DefaultTreeNode("Raiz", null);
		todosProdutos = produtos.todos();
		todasEquipes = equipes.todos();
		todosNiveisEquipe = niveisEquipe.todos();
		atividadeEdicao = new AtividadeHoraPadrao();
		if(cronogramaEdicao == null){
			cronogramaEdicao = new CronogramaPadrao();
			atividadeEdicao = new AtividadeHoraPadrao();
		}
		
		
	}

	public void salvar() {
		try {
			this.cadastroCromogramaPadrao.salvar(cronogramaEdicao);
			consultar();
			todosProdutos = produtos.todos();
			todasEquipes = equipes.todos();
			todosNiveisEquipe = niveisEquipe.todos();
			messages.info("Cronograma Padrão salvo com sucesso!");
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:cronograma-table"));
			RequestContext.getCurrentInstance().execute("PF('edicaoCronogramaDialog').hide()");
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao salvar cronograma padrão! \n Motivo:" + mensagem.getDetail());
			RequestContext.getCurrentInstance().update(Arrays.asList("msgs" ));
		}

	}
	
	public void salvarAtividade(){
		try {
			cronogramaEdicao = this.cronogramasPadrao.pesquisaPorId(cronogramaEdicao.getCodigo());
			atividadeEdicao.setCronogramaPadrao(cronogramaEdicao);
			cronogramaEdicao.getListaAtividadesHorasPadroes().add(atividadeEdicao);
			this.cadastroCromogramaPadrao.salvar(cronogramaEdicao);
			//cronogramaEdicao = this.cronogramasPadrao.pesquisaPorId(cronogramaEdicao.getCodigo());
			carregaArvore(cronogramaEdicao);
			habilitar = false;
			messages.info("Atividade salva com sucesso!");
			RequestContext.getCurrentInstance().update(Arrays.asList("msgs","frmTree","painel-tab"));
			//RequestContext.getCurrentInstance().execute("location.reload()");
		}catch (Exception e){
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			if(mensagem.getDetail() != null){
				messages.error("Erro ao salvar atividade! \n Motivo:" + mensagem.getDetail());
				RequestContext.getCurrentInstance().update(Arrays.asList("msgs","frmTree:treeTableAtividades"));
			} else {
				RequestContext.getCurrentInstance().update(Arrays.asList("frmTree:treeTableAtividades"));
			}
		}
	}

	public void addSintetico(){
		atividadeEdicao = new AtividadeHoraPadrao();
		atividadeEdicao.setAnaliticoSitetico(AnaliticoSintetico.SINTETICO);
		if(atividadeSelecionada != null){
			atividadeEdicao.setAtividadeHoraPai(atividadeSelecionada);
		}
		habilitar = true;
		RequestContext.getCurrentInstance().update(Arrays.asList("painel-tab:frmAtividade"));
	}
	
	public void addAnalitico(){
		atividadeEdicao = new AtividadeHoraPadrao();
		atividadeEdicao.setAnaliticoSitetico(AnaliticoSintetico.ANALITICO);
		atividadeEdicao.setAtividadeHoraPai(atividadeSelecionada);
		habilitar = true;
		RequestContext.getCurrentInstance().update(Arrays.asList("painel-tab:frmAtividade"));
	}

	public void excluir() {
		try {
			this.cadastroCromogramaPadrao.excluir(cronogramaSelecionado);
			cronogramaSelecionado = null;
			consultar();
			todosProdutos = produtos.todos();
			todasEquipes = equipes.todos();
			todosNiveisEquipe = niveisEquipe.todos();
			messages.info("Cronograma Padrão excluído com sucesso!");
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao excluir estado! \n Motivo:" + mensagem);
		}

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
			RequestContext.getCurrentInstance().update(Arrays.asList("msgs","frmTree:treeTableAtividades","painel-tab"));
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao excluir atividade! \n Motivo:" + mensagem);
			RequestContext.getCurrentInstance().update(Arrays.asList("msgs","frmTree:treeTableAtividades"));
		}

	}
	
    public void carregaArvore(CronogramaPadrao cronogramaEdicao)  {
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
    
	public void listaNiveisEquipes(AjaxBehaviorEvent event) {
		carregarNiveisEquipes();
	}
	
	private void carregarNiveisEquipes(){
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
		
		if(cronogramaEdicao != null){
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
		return atividadeEdicao;
	}

	public void setAtividadeEdicao(AtividadeHoraPadrao atividadeEdicao) {
		this.atividadeEdicao = atividadeEdicao;
		if(atividadeEdicao != null ){
			RequestContext.getCurrentInstance().update(Arrays.asList("painel-tab"));
		}
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}
	
	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
		if (selectedNode != null){
			atividadeSelecionada = (AtividadeHoraPadrao) selectedNode.getData();
			atividadeEdicao = (AtividadeHoraPadrao) selectedNode.getData();
			habilitar = true;
			atividadeEdicao.setAnaliticoSitetico(AnaliticoSintetico.ANALITICO);
			RequestContext.getCurrentInstance().update(Arrays.asList("painel-tab","painel-tab:frmAtividade"));
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
	
	
	
}
