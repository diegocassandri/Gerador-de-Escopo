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
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import br.com.prodama.enun.AnaliticoSintetico;
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
import br.com.prodama.service.cadastro.cronograma.CadastroAtividadePadrao;
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
	private CadastroAtividadePadrao cadastroAtividadePadrao;

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
	private TreeNode selectedNode;
	
	private List<Equipe> todasEquipes;
	private List<NivelEquipe> todosNiveisEquipe;
	
	private List<Produto> todosProdutos;
	
	private TreeNode raiz;
	
	@PostConstruct
	public void prepararNovoCadastro() {
		this.raiz = new DefaultTreeNode("Raiz", null);
		todosProdutos = produtos.todos();
		todasEquipes = equipes.todos();
		todosNiveisEquipe = niveisEquipe.todos();
		if(cronogramaEdicao == null){
			cronogramaEdicao = new CronogramaPadrao();
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
			this.cadastroAtividadePadrao.salvar(atividadeEdicao);
			messages.info("Atividade salva com sucesso!");
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:cronograma-table"));
		}catch (Exception e){
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao salvar atividade! \n Motivo:" + mensagem.getDetail());
			RequestContext.getCurrentInstance().update(Arrays.asList("msgs" ));
		}
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
	
	 public void onRowSelect(SelectEvent event) {
	     
	    }
	
	 public void onCellEdit(CellEditEvent event) {
	        Object oldValue = event.getOldValue();
	        Object newValue = event.getNewValue();
	         
	        if(newValue != null && !newValue.equals(oldValue)) {
	            
	        }
	    } 
	
	public void onRowEdit(RowEditEvent event) {
        
    }
     
    public void onRowCancel(RowEditEvent event) {
       
    }
    
    public void carregaArvore(CronogramaPadrao cronogramaEdicao)  {
		List<AtividadeHoraPadrao> atividadesRaizes = atividades.raizes(cronogramaEdicao);
		this.raiz = new DefaultTreeNode("Raiz", null);
		adicionarNos(atividadesRaizes, this.raiz);
	}
    

	private void adicionarNos(List<AtividadeHoraPadrao> atividades, TreeNode pai) {
		for (AtividadeHoraPadrao atividade : atividades) {
			TreeNode no = new DefaultTreeNode(atividade, pai);
			adicionarNos(atividade.getSubAtividadeHoras(), no);
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
			RequestContext.getCurrentInstance().update(Arrays.asList("frmAtividade:pnlAtividade"));
		}
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}
	
	public List<AnaliticoSintetico> getTipoList() {
		return Arrays.asList(AnaliticoSintetico.values());
	}

	
	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
		if(selectedNode != null){
			atividadeEdicao =  (AtividadeHoraPadrao) selectedNode.getData();
			RequestContext.getCurrentInstance().update(Arrays.asList("frmAtividade"));
		}
		
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
	
	
	
}
