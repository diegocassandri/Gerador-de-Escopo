package br.com.prodama.controller.cadastros;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.DualListModel;
import org.primefaces.model.TreeNode;

import br.com.prodama.model.cadastro.Grupo;
import br.com.prodama.model.cadastro.Tela;
import br.com.prodama.model.cadastro.Usuario;
import br.com.prodama.repository.cadastros.Grupos;
import br.com.prodama.repository.cadastros.Telas;
import br.com.prodama.repository.cadastros.Usuarios;
import br.com.prodama.service.cadastro.CadastroGrupo;
import br.com.prodama.service.cadastro.CadastroUsuario;
import br.com.prodama.util.FacesMessages;

@Named
@ViewScoped
public class CadastroGrupoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private FacesMessages messages;

	@Inject
	private CadastroGrupo gruposService;

	@Inject
	private Grupos grupos;

	@Inject
	private Usuarios usuarios;
	
	@Inject
	private Telas telas;
	
	@Inject
	private CadastroUsuario usuarioService;

	private Grupo grupoEdicao = new Grupo();
	private Usuario usuarioEdicao = new Usuario();
	private Grupo grupoSelecionado;
	private List<Grupo> todosGrupos;
	private List<Grupo> filtroGrupos;

	private DualListModel<Usuario> todosUsuarios;
	

	List<Usuario> usuariosSource = new ArrayList<Usuario>();
	List<Usuario> usuariosTarget = new ArrayList<Usuario>();
	
	private TreeNode [] checkboxSelectedNodes;
	
	private TreeNode raiz;
	private TreeNode selecionadas;
	


	@PostConstruct
	public void prepararNovoCadastro() {
		grupoEdicao = new Grupo();
		todosUsuarios = new DualListModel<Usuario>();
		this.raiz = new DefaultTreeNode("Raiz", null);
		checkboxSelectedNodes = null;
	}

	public void salvar() {
		try {
			this.gruposService.salvar(grupoEdicao);
			consultar();
			messages.info("Grupo salvo com sucesso!");
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:grupo-table"));
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao salvar grupo! \n Motivo:" + mensagem.getDetail());
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:grupo-table"));
			e.printStackTrace();
		}

	}
	
	private void adicionarNos(List<Tela> telas, TreeNode pai) {
		for (Tela tela : telas) {

			TreeNode no = new DefaultTreeNode(tela, pai);

			adicionarNos(tela.getTelasfilhas(), no);
		}
	}
	

	public void salvaListaUsuarios() {
		grupoEdicao.getUsuarios().clear();
		salvar();
		for (Usuario usuario : todosUsuarios.getSource()) {

			try {
				usuario = usuarios.pesquisaPorId(usuario.getCodigo());
				usuario.getGrupos().remove(grupoEdicao);
				usuarioService.salvar(usuario);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for (Usuario usuario : todosUsuarios.getTarget()) {

			try {
				usuario = usuarios.pesquisaPorId(usuario.getCodigo());
				usuario.getGrupos().remove(grupoEdicao);
				usuario.getGrupos().add(grupoEdicao);
				usuarioService.salvar(usuario);
				consultar();
			} catch (Exception e) {
				FacesMessage mensagem = new FacesMessage(e.getMessage());
				messages.error("Erro ao salvar grupo! \n Motivo:" + mensagem.getDetail());
				RequestContext.getCurrentInstance()
						.update(Arrays.asList("frmCadastro:msgs", "frmCadastro:grupo-table"));
				e.printStackTrace();
			}
		}
		RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:grupo-table"));

	}

	public void excluir() {
		try {
			this.gruposService.excluir(grupoSelecionado);
			grupoSelecionado = null;
			consultar();
			messages.info("Grupo excluído com sucesso!");
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao excluir grupo! \n Motivo:" + mensagem);
		}

	}

	public void consultar() {
		todosGrupos = grupos.todos();
		
	}

	public Grupo getGrupoEdicao() {
		return grupoEdicao;
	}

	public void setGrupoEdicao(Grupo grupoEdicao) {
		this.grupoEdicao = grupos.pesquisaPorId(grupoEdicao.getCodigo());
		usuariosSource = grupos.usariosNaoAssociados(grupoEdicao);
		usuariosTarget = grupos.usariosAssociados(this.grupoEdicao);

		todosUsuarios = new DualListModel<Usuario>(usuariosSource, usuariosTarget);
			
		carregaPermissoesGrupo(grupoEdicao);
		
	}
	
	
	public void carregaPermissoesGrupo(Grupo grupoEdicao)  {
		List<Tela> telasRaizes = telas.raizes();
		List<Tela> telasAssciadas = grupoEdicao.getTelas();
		this.raiz = new DefaultTreeNode("Raiz", null);
		adicionarNos(telasRaizes, this.raiz);
		/*raiz.setSelected(true);*/
    }
	
	
	public TreeNode getSelecionadas() {
		return selecionadas;
	}

	public void setSelecionadas(TreeNode selecionadas) {
		this.selecionadas = selecionadas;
	}

	public Grupo getGrupoSelecionado() {
		return grupoSelecionado;
	}

	public void setGrupoSelecionado(Grupo grupoSelecionado) {
		this.grupoSelecionado = grupoSelecionado;
	}

	public List<Grupo> getTodosGrupos() {
		return todosGrupos;
	}

	public void setTodosGrupos(List<Grupo> todosGrupos) {
		this.todosGrupos = todosGrupos;
	}

	public List<Grupo> getFiltroGrupos() {
		return filtroGrupos;
	}

	public void setFiltroGrupos(List<Grupo> filtroGrupos) {
		this.filtroGrupos = filtroGrupos;
	}

	public DualListModel<Usuario> getTodosUsuarios() {
		return todosUsuarios;
	}

	public void setTodosUsuarios(DualListModel<Usuario> todosUsuarios) {
		this.todosUsuarios = todosUsuarios;
	}

	public Usuario getUsuarioEdicao() {
		return usuarioEdicao;
	}

	public void setUsuarioEdicao(Usuario usuarioEdicao) {
		this.usuarioEdicao = usuarioEdicao;
	}


	public TreeNode getRaiz() {
		return raiz;
	}

	public void setRaiz(TreeNode raiz) {
		this.raiz = raiz;
	}

	public TreeNode[] getCheckboxSelectedNodes() {
		return checkboxSelectedNodes;
	}

	public void setCheckboxSelectedNodes(TreeNode[] checkboxSelectedNodes) {
		this.checkboxSelectedNodes = checkboxSelectedNodes;
	}


	
	
}
