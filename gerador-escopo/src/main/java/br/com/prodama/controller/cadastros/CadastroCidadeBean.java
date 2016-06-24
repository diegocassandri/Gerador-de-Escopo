package br.com.prodama.controller.cadastros;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.prodama.model.cadastro.geral.Cidade;
import br.com.prodama.model.cadastro.geral.Estado;
import br.com.prodama.repository.cadastros.Cidades;
import br.com.prodama.repository.cadastros.Estados;
import br.com.prodama.service.cadastro.CadastroCidade;

import br.com.prodama.util.FacesMessages;

@Named
@ViewScoped
public class CadastroCidadeBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private FacesMessages messages;

	@Inject
	private CadastroCidade cadastroCidade;

	@Inject
	private Cidades cidades;
	
	@Inject
	private Estados estados;

	private Cidade cidadeEdicao = new Cidade();
	private Cidade cidadeSelecionado;
	private List<Cidade> todasCidades;
	private List<Estado> todosEstados;
	private List<Cidade> filtroCidades;
	
	@PostConstruct
	public void prepararNovoCadastro() {
	   cidadeEdicao = new Cidade();
	   todosEstados = estados.todos();
	}

	public void salvar() {
		try {
			this.cadastroCidade.salvar(cidadeEdicao);

			consultar();
			todosEstados = estados.todos();
			messages.info("Cidade salva com sucesso!");

			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:cidade-table"));

		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao salvar cidade! \n Motivo:" + mensagem.getDetail());
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:estado-table"));

		}

	}


	public void excluir() {
		try {
			System.out.println("exlcuir");
			this.cadastroCidade.excluir(cidadeSelecionado);
			cidadeSelecionado = null;

			consultar();
			todosEstados = estados.todos();
			messages.info("Cidade excluída com sucesso!");
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao excluir Cidade! \n Motivo:" + mensagem);
		}

	}

	public void consultar() {
		todasCidades = cidades.todos();
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

	public CadastroCidade getCadastroCidade() {
		return cadastroCidade;
	}

	public void setCadastroCidade(CadastroCidade cadastroCidade) {
		this.cadastroCidade = cadastroCidade;
	}

	public Cidades getCidades() {
		return cidades;
	}

	public void setCidades(Cidades cidades) {
		this.cidades = cidades;
	}

	public Cidade getCidadeEdicao() {
		return cidadeEdicao;
	}

	public void setCidadeEdicao(Cidade cidadeEdicao) {
		this.cidadeEdicao = cidadeEdicao;
	}

	public Cidade getCidadeSelecionado() {
		return cidadeSelecionado;
	}

	public void setCidadeSelecionado(Cidade cidadeSelecionado) {
		this.cidadeSelecionado = cidadeSelecionado;
	}

	public List<Cidade> getTodasCidades() {
		return todasCidades;
	}

	public void setTodasCidades(List<Cidade> todasCidades) {
		this.todasCidades = todasCidades;
	}

	public List<Cidade> getFiltroCidades() {
		return filtroCidades;
	}

	public void setFiltroCidades(List<Cidade> filtroCidades) {
		this.filtroCidades = filtroCidades;
	}

	public List<Estado> getTodosEstados() {
		return todosEstados;
	}

	public void setTodosEstados(List<Estado> todosEstados) {
		this.todosEstados = todosEstados;
	}
}
