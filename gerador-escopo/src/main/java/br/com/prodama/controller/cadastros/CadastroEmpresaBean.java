package br.com.prodama.controller.cadastros;

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

import br.com.prodama.model.cadastro.Empresa;
import br.com.prodama.repository.cadastros.Empresas;
import br.com.prodama.service.cadastro.CadastroEmpresa;
import br.com.prodama.util.FacesMessages;

@Named
@ViewScoped
public class CadastroEmpresaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private FacesMessages messages;
	
	@Inject
	private CadastroEmpresa cadastroEmpresa;
	
	@Inject
	private Empresas empresas;
	
	private Empresa empresaEdicao = new Empresa();
	private Empresa empresaSelecionada = new Empresa();
    private List<Empresa> todasEmpresas = new ArrayList<Empresa>();
    
    @PostConstruct
	public void prepararNovoCadastro() {
		empresaEdicao = new Empresa();
	}
    
    public void salvar() {
		try {
			this.cadastroEmpresa.salvar(empresaEdicao);
			consultar();
			messages.info("Empresa salva com sucesso!");
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:empresa-table"));
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao salvar empresa! \n Motivo:" + mensagem.getDetail());
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:usuario-table"));

		}

	}
    
    public void excluir() {
		try {
			this.cadastroEmpresa.excluir(empresaSelecionada);
			empresaSelecionada = null;

			consultar();

			messages.info("Empresa excluída com sucesso!");
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao excluir Empresa! \n Motivo:" + mensagem);
		}

	}
    
    public void consultar() {
    	todasEmpresas = empresas.todos();
	}

	public Empresa getEmpresaEdicao() {
		return empresaEdicao;
	}

	public void setEmpresaEdicao(Empresa empresaEdicao) {
		this.empresaEdicao = empresaEdicao;
	}

	public Empresa getEmpresaSelecionada() {
		return empresaSelecionada;
	}

	public void setEmpresaSelecionada(Empresa empresaSelecionada) {
		this.empresaSelecionada = empresaSelecionada;
	}

	public List<Empresa> getTodasEmpresas() {
		return todasEmpresas;
	}

	public void setTodasEmpresas(List<Empresa> todasEmpresas) {
		this.todasEmpresas = todasEmpresas;
	}
    
}
