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
import br.com.prodama.model.cadastro.geral.Filial;
import br.com.prodama.repository.cadastro.geral.Filiais;


@Named
@ViewScoped
public class SelecaoFilialBean implements Serializable {

	private static final long serialVersionUID = 1L;
	

	
	@Inject
	private Filiais filiais;
	
	@Inject
	private UsuarioLogin usuarioLogin;
	
    private String nome;
	
	private List<Filial> filiaisFiltradas;
	
	
	
	public void pesquisar() {
		filiaisFiltradas = filiais.porNomeSemelhante(nome,usuarioLogin.getEmpresaSelecionada());
	}
	
	@PostConstruct
	public void todos(){
		filiaisFiltradas = filiais.todos(usuarioLogin.getEmpresaSelecionada());
	}
	
	public void abrirDialogo() {
		Map<String, Object> opcoes = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("resizable", false);
		opcoes.put("contentHeight", 500);
		opcoes.put("contentWidth", 480);
		
		RequestContext.getCurrentInstance().openDialog("/cadastros/SelecaoFilial", opcoes, null);

	}
	
	
	public void selecionar(Filial filial) {
		usuarioLogin.setFilialSelecionada(filial);
		RequestContext.getCurrentInstance().closeDialog(filial);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Filial> getFiliaisFiltradas() {
		return filiaisFiltradas;
	}

	public void setFiliaisFiltradas(List<Filial> filiaisFiltradas) {
		this.filiaisFiltradas = filiaisFiltradas;
	}
	
	

}
