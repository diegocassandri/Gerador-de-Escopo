package br.com.prodama.controller.cadastro.produto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.prodama.enun.Status;
import br.com.prodama.model.cadastro.produto.GestaoModulo;
import br.com.prodama.model.cadastro.produto.Modulo;
import br.com.prodama.repository.cadastro.produto.GestaoModulos;
import br.com.prodama.repository.cadastro.produto.Modulos;
import br.com.prodama.service.cadastro.produto.CadastroGestaoModulo;
import br.com.prodama.util.FacesMessages;

@Named
@ViewScoped
public class CadastroGestaoModuloBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FacesMessages messages;
	
	@Inject
	private CadastroGestaoModulo cadastroGestaoModulo;

	@Inject
	private GestaoModulos gestaoModulos;
	
	@Inject
	private Modulos modulos;


	private GestaoModulo gestaoModuloEdicao = new GestaoModulo();
	private GestaoModulo gestaoModuloSelecionado;
	private List<GestaoModulo> todosGestaoModulos;
	private List<GestaoModulo> filtroModulos;
	private List<Modulo> todosModulos;

	@PostConstruct
	public void prepararNovoCadastro() {
		gestaoModuloEdicao = new GestaoModulo();
		todosModulos = modulos.todos();
	}

	public void salvar() {
		try {
			this.cadastroGestaoModulo.salvar(gestaoModuloEdicao);
			consultar();
			todosModulos = modulos.todos();
			messages.info("Gestão de Módulo salva com sucesso!");
			
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao salvar Gestão de Módulo! \n Motivo:" + mensagem.getDetail());
		}

	}
	
	public void salvarGestao(SelectEvent event){
		RequestContext.getCurrentInstance().closeDialog(event);
	}

	public void excluir() {
		try {
			this.cadastroGestaoModulo.excluir(gestaoModuloEdicao);
			gestaoModuloSelecionado = null;
			consultar();
			todosModulos = modulos.todos();
			messages.info("Gestão de Módulo excluído com sucesso!");
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao excluir Gestão de Módulo! \n Motivo:" + mensagem);
		}

	}

	public void consultar() {
		todosGestaoModulos = gestaoModulos.todos();
	}

	public List<Status> getStatusList() {
		return Arrays.asList(Status.values());
	}

	public GestaoModulo getGestaoModuloEdicao() {
		return gestaoModuloEdicao;
	}

	public void setGestaoModuloEdicao(GestaoModulo gestaoModuloEdicao) {
		this.gestaoModuloEdicao = gestaoModuloEdicao;
	}

	public GestaoModulo getGestaoModuloSelecionado() {
		return gestaoModuloSelecionado;
	}

	public void setGestaoModuloSelecionado(GestaoModulo gestaoModuloSelecionado) {
		this.gestaoModuloSelecionado = gestaoModuloSelecionado;
	}

	public List<GestaoModulo> getTodosGestaoModulos() {
		return todosGestaoModulos;
	}

	public void setTodosGestaoModulos(List<GestaoModulo> todosGestaoModulos) {
		this.todosGestaoModulos = todosGestaoModulos;
	}

	public List<GestaoModulo> getFiltroModulos() {
		return filtroModulos;
	}

	public void setFiltroModulos(List<GestaoModulo> filtroModulos) {
		this.filtroModulos = filtroModulos;
	}

	public List<Modulo> getTodosModulos() {
		return todosModulos;
	}

	public void setTodosModulos(List<Modulo> todosModulos) {
		this.todosModulos = todosModulos;
	}

	

}
