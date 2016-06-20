package br.com.prodama.controller;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import br.com.prodama.model.cadastro.Empresa;
import br.com.prodama.model.cadastro.Filial;
import br.com.prodama.model.cadastro.Usuario;
import br.com.prodama.repository.cadastros.Usuarios;
import br.com.prodama.service.cadastro.CadastroUsuario;
import br.com.prodama.util.FacesMessages;

@Named
@SessionScoped
public class UsuarioLogin implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nome;
	private Date dataLogin;
	private Empresa empresaSelecionada;
	private Filial filialSelecionada;

	private Usuario usuarioEdicao;
	
	@Inject 
	private FacesMessages mensagem;
	
	@Inject
	private Usuarios usuarios;
	
	@Inject
	private CadastroUsuario cadastrosUsuario;
	
	private Usuario usuarioLogin = new Usuario();
	
	public void SelecionarEmpresa(SelectEvent event){
		empresaSelecionada =  (Empresa) event.getObject();
	}
	
	public void gravaSessaoUsuario(){
		usuarioEdicao = this.getUsuarioLogin();
		
	}
	
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

	public Empresa getEmpresaSelecionada() {
		return empresaSelecionada;
	}

	public void setEmpresaSelecionada(Empresa empresaSelecionada) {
		this.empresaSelecionada = empresaSelecionada;
	}

	public Filial getFilialSelecionada() {
		return filialSelecionada;
	}

	public void setFilialSelecionada(Filial filialSelecionada) {
		this.filialSelecionada = filialSelecionada;
	}
	
	

}
