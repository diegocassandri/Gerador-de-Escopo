package br.com.prodama.controller.cadastro.cronograma;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.prodama.model.cadastro.cronograma.CronogramaPadrao;
import br.com.prodama.model.cadastro.produto.Produto;
import br.com.prodama.repository.cadastro.cronograma.CronogramasPadrao;
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

	private CronogramaPadrao cronogramaEdicao = new CronogramaPadrao();
	private CronogramaPadrao cronogramaSelecionado;
	private List<CronogramaPadrao> todosCronogramas;
	private List<CronogramaPadrao> filtroCronogramas;
	private List<Produto> todosProdutos;
	
	@PostConstruct
	public void prepararNovoCadastro() {
		cronogramaEdicao = new CronogramaPadrao();
		todosProdutos = produtos.todos();
	}

	public void salvar() {
		try {
			this.cadastroCromogramaPadrao.salvar(cronogramaEdicao);
			consultar();
			todosProdutos = produtos.todos();
			messages.info("Cronograma Padrão salvo com sucesso!");
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:cronograma-table"));
			RequestContext.getCurrentInstance().execute("PF('edicaoCronogramaDialog').hide()");
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao salvar cronograma padrão! \n Motivo:" + mensagem.getDetail());
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:cronograma-table"));
		}

	}


	public void excluir() {
		try {
			this.cadastroCromogramaPadrao.excluir(cronogramaSelecionado);
			cronogramaSelecionado = null;
			consultar();
			todosProdutos = produtos.todos();
			messages.info("Cronograma Padrão excluído com sucesso!");
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao excluir estado! \n Motivo:" + mensagem);
		}

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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean filterByPrice(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim();
		if (filterText == null || filterText.equals("")) {
			return true;
		}

		if (value == null) {
			return false;
		}

		return ((Comparable) value).compareTo(Integer.valueOf(filterText)) > 0;
	}
	
	
	
	

}
