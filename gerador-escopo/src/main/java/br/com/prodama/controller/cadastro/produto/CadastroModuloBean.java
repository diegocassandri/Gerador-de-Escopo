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

import br.com.prodama.enun.Status;
import br.com.prodama.model.cadastro.produto.GestaoModulo;
import br.com.prodama.model.cadastro.produto.Modulo;
import br.com.prodama.model.cadastro.produto.Produto;
import br.com.prodama.repository.cadastro.produto.GestaoModulos;
import br.com.prodama.repository.cadastro.produto.Modulos;
import br.com.prodama.repository.cadastro.produto.Produtos;
import br.com.prodama.service.cadastro.produto.CadastroModulo;
import br.com.prodama.util.FacesMessages;

@Named
@ViewScoped
public class CadastroModuloBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FacesMessages messages;

	@Inject
	private CadastroModulo cadastroModulo;

	@Inject
	private Modulos modulos;

	@Inject
	private GestaoModulos gestaoModulos;

	@Inject
	private Produtos produtos;

	private Modulo moduloEdicao = new Modulo();
	private GestaoModulo gestaoModuloEdicao = new GestaoModulo();

	private Modulo moduloSelecionado;
	private GestaoModulo gestaoModuloSelecionado;

	private List<Modulo> todosModulos;
	private List<Modulo> filtroModulos;
	private List<Produto> todosProdutos;

	private List<GestaoModulo> todosGestaoModulo;

	@PostConstruct
	public void prepararNovoCadastro() {
		moduloEdicao = new Modulo();
		gestaoModuloEdicao = new GestaoModulo();
		todosProdutos = produtos.todos();
		todosModulos = modulos.todos();
		todosGestaoModulo = null;
		/*prepararNovoCadastroGestao();*/
	}

	public void prepararNovoCadastroGestao() {
		gestaoModuloEdicao = new GestaoModulo();
		todosModulos = modulos.todos();	
	}

	public void salvar() {
		try {
			this.cadastroModulo.salvar(moduloEdicao);
			consultar();
			todosProdutos = produtos.todos();
			todosModulos = modulos.todos();
			if ((moduloEdicao.getCodigo() != null) && (moduloEdicao.getCodigo() != 0)) {
				todosGestaoModulo = gestaoModulos.todos();
				this.moduloEdicao = modulos.pesquisaPorId(moduloEdicao.getCodigo());
				todosGestaoModulo = moduloEdicao.getListaGestoesModulo();
			}
			messages.info("Modulo salvo com sucesso!");
			//*RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:modulo-table"));*/
			moduloEdicao = modulos.pesquisaPorNomeCodigo(moduloEdicao);
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:modulo-table"));
			
		} catch (Exception e){
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao salvar Modulo! \n Motivo:" + mensagem.getDetail());
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:modulo-table"));
		}

	}

	public void salvarGestao() {
		try {
			moduloEdicao = modulos.pesquisaPorNomeCodigo(moduloEdicao);
			moduloEdicao.getListaGestoesModulo().remove(gestaoModuloEdicao);
			gestaoModuloEdicao.setModulo(moduloEdicao);
			moduloEdicao.getListaGestoesModulo().add(gestaoModuloEdicao);
			this.cadastroModulo.salvar(moduloEdicao);
			todosModulos = modulos.todos();
			todosGestaoModulo = gestaoModulos.todos();
			this.moduloEdicao = modulos.pesquisaModulo(moduloEdicao);
			todosGestaoModulo = moduloEdicao.getListaGestoesModulo();
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:painel-dialog"));
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao salvar Gestão de Módulo! \n Motivo:" + mensagem.getDetail());
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:painel-dialog"));
		}

	}

	public void excluirGestao() {
		try {
			moduloEdicao.getListaGestoesModulo().remove(gestaoModuloEdicao);
			gestaoModuloSelecionado = null;
			this.cadastroModulo.salvar(moduloEdicao);
			todosModulos = modulos.todos();
			todosGestaoModulo = gestaoModulos.todos();
			moduloEdicao = modulos.pesquisaPorId(moduloEdicao.getCodigo());
			todosGestaoModulo = moduloEdicao.getListaGestoesModulo();
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:painel-dialog"));
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao excluir Gestão do Módulo! \n Motivo:" + mensagem);
			todosGestaoModulo = moduloEdicao.getListaGestoesModulo();
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:painel-dialog"));
		}
	}

	public void excluir() {
		try {
			this.cadastroModulo.excluir(moduloEdicao);
			moduloSelecionado = null;
			consultar();
			todosProdutos = produtos.todos();
			messages.info("Modulo excluído com sucesso!");
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao excluir Modulo! \n Motivo:" + mensagem);
		}

	}

	public void consultar() {
		todosModulos = modulos.todos();
	}

	public List<Status> getStatusList() {
		return Arrays.asList(Status.values());
	}

	public Modulo getModuloEdicao() {
		return moduloEdicao;
	}

	public void setModuloEdicao(Modulo moduloEdicao) {
		if ((moduloEdicao.getCodigo() != null) && (moduloEdicao.getCodigo() != 0)) {
			moduloEdicao = modulos.pesquisaPorId(moduloEdicao.getCodigo());
			todosGestaoModulo = moduloEdicao.getListaGestoesModulo();
		}

		this.moduloEdicao = moduloEdicao;
	}

	public Modulo getModuloSelecionado() {
		return moduloSelecionado;
	}

	public void setModuloSelecionado(Modulo moduloSelecionado) {
		this.moduloSelecionado = moduloSelecionado;
	}

	public List<Modulo> getTodosModulos() {
		return todosModulos;
	}

	public void setTodosModulos(List<Modulo> todosModulos) {
		this.todosModulos = todosModulos;
	}

	public List<Modulo> getFiltroModulos() {
		return filtroModulos;
	}

	public void setFiltroModulos(List<Modulo> filtroModulos) {
		this.filtroModulos = filtroModulos;
	}

	public List<Produto> getTodosProdutos() {
		return todosProdutos;
	}

	public void setTodosProdutos(List<Produto> todosProdutos) {
		this.todosProdutos = todosProdutos;
	}

	public List<GestaoModulo> getTodosGestaoModulo() {
		return todosGestaoModulo;
	}

	public void setTodosGestaoModulo(List<GestaoModulo> todosGestaoModulo) {
		this.todosGestaoModulo = todosGestaoModulo;
	}

	public GestaoModulos getGestaoModulos() {
		return gestaoModulos;
	}

	public void setGestaoModulos(GestaoModulos gestaoModulos) {
		this.gestaoModulos = gestaoModulos;
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

}
