package br.com.prodama.controller.cadastros;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.prodama.model.cadastro.Empresa;
import br.com.prodama.repository.cadastros.Empresas;
import br.com.prodama.util.FacesMessages;

@Named
@ViewScoped
public class SelecaoEmpresaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private FacesMessages messages;
	
	@Inject
	private Empresas empresas;
	
    private String nome;
	
	private List<Empresa> empresasFiltradas;
	
	public void pesquisar() {
		empresasFiltradas = empresas.porNomeSemelhante(nome);
	}
	
	public void abrirDialogo() {
		Map<String, Object> opcoes = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("resizable", false);
		opcoes.put("contentHeight", 430);
		opcoes.put("contentWidth", 430);
		
		RequestContext.getCurrentInstance().openDialog("/cadastros/SelecaoEmpresa.xhtml", opcoes, null);
		RequestContext contexto = RequestContext.getCurrentInstance();
		contexto.execute("PF('dialog').show()");
	}
	
	
	public void selecionar(Empresa empresa) {
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
