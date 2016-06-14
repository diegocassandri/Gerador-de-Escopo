package br.com.prodama.controller;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.prodama.model.cadastro.Usuario;
import br.com.prodama.repository.cadastros.Usuarios;
import br.com.prodama.service.NegocioException;
import br.com.prodama.service.cadastro.CadastroUsuario;
import br.com.prodama.util.CriptografaSenha;
import br.com.prodama.util.FacesMessages;


@Named
@RequestScoped
public class LoginBean {

	@Inject
	private UsuarioLogin usuario;
	
	@Inject 
	private FacesMessages mensagem;
	
	@Inject
	private Usuarios usuarios;
	
	@Inject
	private CadastroUsuario cadastrosUsuario;
	
	private Usuario usuarioEdicao = new Usuario();

	
	private String nomeUsuario;
	private String senha;
	private String senha2;
	private String novaSenha;
	private String confirmacaoSenha;
	
	private Usuario usuarioLogin = new Usuario();
	
	private boolean mudarSenha = false;

	public String login() throws NoSuchAlgorithmException {
		FacesContext context = FacesContext.getCurrentInstance();
		
		if ("admin".equals(this.nomeUsuario) && "123".equals(this.senha)) {
			this.usuario.setNome(this.nomeUsuario);
			this.usuario.setDataLogin(new Date());
			return "/Home?faces-redirect=true";
		} else if (usuarios.autenticaUsuario(this.nomeUsuario,CriptografaSenha.criptografa(this.senha))){
			this.usuario.setNome(this.nomeUsuario);
			this.usuario.setDataLogin(new Date());
			this.usuario.setUsuarioLogin(usuarios.retornaUsuarioPorNome(usuario.getNome()));
			if(usuarios.verificaMudarSenha(this.nomeUsuario)){
				mudarSenha = true;
				RequestContext contexto = RequestContext.getCurrentInstance();
				contexto.execute("PF('senha-dialog').show()");
				return "";
			}else{
				return "/Home?faces-redirect=true";
			}
			
		} else {
			FacesMessage mensagem = new FacesMessage("Usuário/senha inválidos!");
			mensagem.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, mensagem);
		}
		
		return null;
	}
	
	public String alterarSenha() throws NoSuchAlgorithmException, NegocioException{
		if(senha.equals(senha2)){
			usuarioEdicao = usuarios.retornaUsuarioPorNome(usuario.getNome());
			usuarioEdicao.setSenha(senha);
			usuarioEdicao.setMudarSenha(false);
			cadastrosUsuario.salvar(usuarioEdicao);
			mensagem.info("Senha alterada com sucesso!");
			return "/Home.xhtml";
		}else{
			mensagem.error("As senhas devem ser iguais!");
			return "";
		}
	}
	
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/Login?faces-redirect=true";
	}
	
	public void alteraSenha(){
		System.out.println(usuarioLogin);
	}
	
	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}

	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
	}

	public boolean isMudarSenha() {
		return mudarSenha;
	}

	public void setMudarSenha(boolean mudarSenha) {
		this.mudarSenha = mudarSenha;
	}

	public Usuario getUsuarioLogin() {
		return usuarioLogin;
	}

	public void setUsuarioLogin(Usuario usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}

	public String getSenha2() {
		return senha2;
	}

	public void setSenha2(String senha2) {
		this.senha2 = senha2;
	}

	public Usuario getUsuarioEdicao() {
		return usuarioEdicao;
	}

	public void setUsuarioEdicao(Usuario usuarioEdicao) {
		this.usuarioEdicao = usuarioEdicao;
	}

	
	
}