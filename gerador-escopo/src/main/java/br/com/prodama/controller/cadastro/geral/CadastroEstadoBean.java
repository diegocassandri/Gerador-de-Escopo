package br.com.prodama.controller.cadastro.geral;

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

import br.com.prodama.model.cadastro.geral.Estado;
import br.com.prodama.repository.cadastro.geral.Estados;
import br.com.prodama.service.cadastro.geral.CadastroEstado;
import br.com.prodama.util.FacesMessages;

@Named
@ViewScoped
public class CadastroEstadoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private FacesMessages messages;

	@Inject
	private CadastroEstado cadastroEstado;

	@Inject
	private Estados estados;

	private Estado estadoEdicao = new Estado();
	private Estado estadoSelecionado;
	private List<Estado> todosEstados;
	private List<Estado> filtroEstados;
	
	@PostConstruct
	public void prepararNovoCadastro() {
		estadoEdicao = new Estado();
	}

	public void salvar() {
		try {
			this.cadastroEstado.salvar(estadoEdicao);

			consultar();

			messages.info("Estado salvo com sucesso!");

			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:estado-table"));

		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao salvar estado! \n Motivo:" + mensagem.getDetail());
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:estado-table"));

		}

	}


	public void excluir() {
		try {
			this.cadastroEstado.excluir(estadoSelecionado);
			estadoSelecionado = null;

			consultar();

			messages.info("Estado excluído com sucesso!");
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao excluir estado! \n Motivo:" + mensagem);
		}

	}

	public void consultar() {
		todosEstados = estados.todos();
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

	public Estado getEstadoEdicao() {
		return estadoEdicao;
	}

	public void setEstadoEdicao(Estado estadoEdicao) {
		this.estadoEdicao = estadoEdicao;
	}

	public Estado getEstadoSelecionado() {
		return estadoSelecionado;
	}

	public void setEstadoSelecionado(Estado estadoSelecionado) {
		this.estadoSelecionado = estadoSelecionado;
	}

	public List<Estado> getTodosEstados() {
		return todosEstados;
	}

	public void setTodosEstados(List<Estado> todosEstados) {
		this.todosEstados = todosEstados;
	}

	public List<Estado> getFiltroEstados() {
		return filtroEstados;
	}

	public void setFiltroEstados(List<Estado> filtroEstados) {
		this.filtroEstados = filtroEstados;
	}
	
	

}
