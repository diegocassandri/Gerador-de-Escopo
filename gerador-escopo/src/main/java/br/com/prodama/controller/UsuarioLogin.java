package br.com.prodama.controller;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.com.prodama.model.cadastro.Usuario;

@Named
@SessionScoped
public class UsuarioLogin implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nome;
	private Date dataLogin;
	
	private Usuario usuarioLogin = new Usuario();
	
	public boolean isLogado() {
		return nome != null;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataLogin() {
		return dataLogin;
	}

	public void setDataLogin(Date dataLogin) {
		this.dataLogin = dataLogin;
	}

	public Usuario getUsuarioLogin() {
		return usuarioLogin;
	}

	public void setUsuarioLogin(Usuario usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}
	
	

}
