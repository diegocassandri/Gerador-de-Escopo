package br.com.prodama.controller.cadastro.geral;

import java.io.Serializable;
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

import br.com.prodama.model.cadastro.geral.Grupo;
import br.com.prodama.model.cadastro.geral.Tela;
import br.com.prodama.model.cadastro.geral.Usuario;
import br.com.prodama.repository.cadastro.geral.Grupos;
import br.com.prodama.repository.cadastro.geral.Telas;
import br.com.prodama.repository.cadastro.geral.Usuarios;
import br.com.prodama.service.cadastro.geral.CadastroGrupo;
import br.com.prodama.service.cadastro.geral.CadastroUsuario;
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
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:grupo-table","frmCadastro:associaTelas-Dialog"));
			RequestContext.getCurrentInstance().execute("PF('edicaoGrupoDialog').hide()");
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao salvar grupo! \n Motivo:" + mensagem.getDetail());
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:grupo-table"));
			e.printStackTrace();
		}

	}
	


	public void salvaListaUsuarios() {
		System.out.println("Salva Usuarios");
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
		todosUsuarios = null;
		usuariosTarget = null;
		this.grupoEdicao = grupos.pesquisaPorId(grupoEdicao.getCodigo());
		usuariosSource = grupos.usariosNaoAssociados(grupoEdicao);

		usuariosTarget = grupos.usariosAssociados(this.grupoEdicao);
		todosUsuarios = new DualListModel<Usuario>(usuariosSource, usuariosTarget);
			
		carregaPermissoesGrupo(this.grupoEdicao);
		
	}
	
	private void adicionarNos(List<Tela> telas, TreeNode pai,List<Tela> telasSelecionadas) {
		for (Tela tela : telas) {

			TreeNode no = new DefaultTreeNode(tela, pai);

			adicionarNos(tela.getTelasfilhas(), no,telasSelecionadas);
			for (Tela tela2 : telasSelecionadas){
				if(tela2.equals(tela) ){
					no.setSelected(true);
				}
			}
		}
	}
	
	public void salvaPermissoesGrupo(){
	   Tela telaTemp;
	   grupoEdicao.getTelas().clear();
		for (TreeNode no : checkboxSelectedNodes){
				telaTemp = ((Tela)no.getData());
				grupoEdicao.getTelas().remove(telaTemp);
				grupoEdicao.getTelas().add(telaTemp);
		}
		salvar();
	}
	
	public void carregaPermissoesGrupo(Grupo grupoEdicao)  {
		List<Tela> telasRaizes = telas.raizes();
		List<Tela> telasAssociadas = grupoEdicao.getTelas();
		this.raiz = new DefaultTreeNode("Raiz", null);
		adicionarNos(telasRaizes, this.raiz,telasAssociadas);
		RequestContext.getCurrentInstance().update(Arrays.asList(":frmCadastro:associaTelas-Dialog"));
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
