package br.com.prodama.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.prodama.model.cadastro.Empresa;
import br.com.prodama.model.cadastro.Filial;
import br.com.prodama.model.cadastro.Usuario;
import br.com.prodama.repository.cadastros.Usuarios;
import br.com.prodama.service.NegocioException;
import br.com.prodama.service.cadastro.CadastroUsuario;


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
	private Usuarios usuarios;
	
	@Inject
	private CadastroUsuario cadastrosUsuario;
	
	private Usuario usuarioLogin = new Usuario();
	
	public void SelecionarEmpresa(SelectEvent event){
		empresaSelecionada =  (Empresa) event.getObject();
		filialSelecionada = null;
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:painel"));
		try {
			gravaSessaoUsuario();
		} catch (NegocioException e) {
			e.printStackTrace();
		}
	}
	
	public void SelecionarFilial(SelectEvent event){
		filialSelecionada =  (Filial) event.getObject();
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:painel"));
		try {
			gravaSessaoUsuario();
		} catch (NegocioException e) {
			e.printStackTrace();
		}
	}
	
	public void gravaSessaoUsuario() throws NegocioException{
		usuarioEdicao = this.getUsuarioLogin();
		usuarioEdicao = usuarios.pesquisaPorId(usuarioEdicao.getCodigo());
		usuarioEdicao.setEmpresaSelecionada(this.empresaSelecionada);
		usuarioEdicao.setFilialSelecionada(this.filialSelecionada);
		System.out.println(usuarioEdicao.getEmpresaSelecionada().getCodigo());
		
		this.cadastrosUsuario.salvar(usuarioEdicao);
		
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
		RequestContext.getCurrentInstance().update(Arrays.asList("frm"));
		filialSelecionada = null;
		this.empresaSelecionada = empresaSelecionada;
	}

	public Filial getFilialSelecionada() {
		return filialSelecionada;
	}

	public void setFilialSelecionada(Filial filialSelecionada) {
		RequestContext.getCurrentInstance().update(Arrays.asList("frm"));
		this.filialSelecionada = filialSelecionada;
	}
	
	

}
