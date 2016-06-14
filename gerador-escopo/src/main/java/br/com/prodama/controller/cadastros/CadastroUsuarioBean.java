package br.com.prodama.controller.cadastros;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;

import br.com.prodama.enun.Status;
import br.com.prodama.model.cadastro.Grupo;
import br.com.prodama.model.cadastro.Usuario;
import br.com.prodama.repository.cadastros.Usuarios;
import br.com.prodama.service.cadastro.CadastroUsuario;
import br.com.prodama.util.FacesMessages;

@Named
@ViewScoped
public class CadastroUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private FacesMessages messages;

	@Inject
	private CadastroUsuario cadastrosUsuario;

	@Inject
	private Usuarios usuarios;

	private Usuario usuarioEdicao = new Usuario();
	private Usuario usuarioSelecionado;
	private List<Usuario> todosUsuario;
	private List<Usuario> filtroUsuarios;

	private DualListModel<Grupo> todosGrupos;

	List<Grupo> gruposSource = new ArrayList<Grupo>();
	List<Grupo> gruposTarget = new ArrayList<Grupo>();

	@PostConstruct
	public void prepararNovoCadastro() {
		usuarioEdicao = new Usuario();
		usuarioEdicao.setMudarSenha(true);
		todosGrupos = new DualListModel<Grupo>();
	}

	public void salvar() {
		try {
			this.cadastrosUsuario.salvar(usuarioEdicao);

			consultar();

			messages.info("Usuário salvo com sucesso!");

			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:usuario-table"));

		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao salvar usuário! \n Motivo:" + mensagem.getDetail());
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:usuario-table"));

		}

	}

	public void salvaListaGrupos() {
		usuarioEdicao.getGrupos().removeAll(todosGrupos.getSource());
		usuarioEdicao.getGrupos().removeAll(todosGrupos.getTarget());
		usuarioEdicao.getGrupos().addAll(todosGrupos.getTarget());
		salvar();
		
	}
	
	public void alterSenha(){
		usuarioEdicao.setMudarSenha(false);
		salvar();
	}

	public void excluir() {
		try {
			this.cadastrosUsuario.excluir(usuarioSelecionado);
			usuarioSelecionado = null;

			consultar();

			messages.info("Usuário excluído com sucesso!");
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao excluir usuário! \n Motivo:" + mensagem);
		}

	}

	public void consultar() {
		todosUsuario = usuarios.todos();
	}

	public List<Usuario> getTodosUsuarios() {
		return todosUsuario;
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

	public Usuario getUsuario() {
		return usuarioEdicao;
	}

	public void setUsuario(Usuario usuario) {
		this.usuarioEdicao = usuario;
	}

	public List<Status> getStatusList() {
		return Arrays.asList(Status.values());
	}

	public CadastroUsuario getUsuarioService() {
		return cadastrosUsuario;
	}

	public void setUsuarioService(CadastroUsuario usuarioService) {
		this.cadastrosUsuario = usuarioService;
	}

	public CadastroUsuario getCadastrosUsuario() {
		return cadastrosUsuario;
	}

	public void setCadastrosUsuario(CadastroUsuario cadastrosUsuario) {
		this.cadastrosUsuario = cadastrosUsuario;
	}

	public Usuario getUsuarioEdicao() {
		return usuarioEdicao;
	}

	public void setUsuarioEdicao(Usuario usuarioEdicao) {
		this.usuarioEdicao = usuarios.pesquisaPorId(usuarioEdicao.getCodigo());
		gruposSource.clear();
		gruposTarget.clear();
		gruposSource = usuarios.gruposNaoAssociados(this.usuarioEdicao);

		gruposTarget = usuarios.gruposAssociados(this.usuarioEdicao);

		todosGrupos = new DualListModel<Grupo>(gruposSource, gruposTarget);
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

	public List<Usuario> getTodosUsuario() {
		return todosUsuario;
	}

	public void setTodosUsuario(List<Usuario> todosUsuario) {
		this.todosUsuario = todosUsuario;
	}

	public List<Usuario> getFiltroUsuarios() {
		return filtroUsuarios;
	}

	public void setFiltroUsuarios(List<Usuario> filtroUsuarios) {
		this.filtroUsuarios = filtroUsuarios;
	}

	public DualListModel<Grupo> getTodosGrupos() {
		return todosGrupos;
	}

	public void setTodosGrupos(DualListModel<Grupo> todosGrupos) {
		this.todosGrupos = todosGrupos;
	}

	
}
