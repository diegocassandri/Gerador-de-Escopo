package br.com.prodama.controller.cadastro.geral;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.prodama.controller.UsuarioLogin;
import br.com.prodama.model.cadastro.geral.Empresa;
import br.com.prodama.repository.cadastro.geral.Empresas;


@Named
@ViewScoped
public class SelecaoEmpresaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	

	
	@Inject
	private Empresas empresas;
	
	@Inject
	private UsuarioLogin usuarioLogin;
	
    private String nome;
	
	private List<Empresa> empresasFiltradas;
	
	
	
	public void pesquisar() {
		empresasFiltradas = empresas.porNomeSemelhante(nome);
	}
	
	@PostConstruct
	public void todos(){
		empresasFiltradas = usuarioLogin.getAbrangeciaEmpresa();
	}
	
	public void abrirDialogo() {
		Map<String, Object> opcoes = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("resizable", false);
		opcoes.put("contentHeight", 500);
		opcoes.put("contentWidth", 480);
		
		RequestContext.getCurrentInstance().openDialog("/cadastros/SelecaoEmpresa", opcoes, null);

	}
	
	
	public void selecionar(Empresa empresa) {
		usuarioLogin.setEmpresaSelecionada(empresa);
		RequestContext.getCurrentInstance().closeDialog(empresa);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Empresa> getEmpresasFiltradas() {
		return empresasFiltradas;
	}

	public void setEmpresasFiltradas(List<Empresa> empresasFiltradas) {
		this.empresasFiltradas = empresasFiltradas;
	}
	
	

}
