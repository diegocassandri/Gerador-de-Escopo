package br.com.prodama.controller.cadastros;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.model.TreeNode;
import br.com.prodama.model.cadastro.Tela;
import br.com.prodama.repository.cadastros.Telas;
import br.com.prodama.service.cadastro.CadastroTela;
import br.com.prodama.util.FacesMessages;

@Named
@ViewScoped
public class CadastroTelaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private FacesMessages messages;
	
	@Inject
	private CadastroTela cadastroTela;
	
	@Inject
	private Telas telas;
	
	private Tela telaEdicao = new Tela();
	private Tela telaSelecionada;
	private List<Tela> todasTelas;
	private List<Tela> filtroTelas;
	
	private TreeNode raiz;
	
	@PostConstruct
	public void prepararNovoCadastro(){
		telaEdicao = new Tela();
	}
	
	public void consultar() {
		todasTelas = telas.todos();
	}
	
	public void salvar() {
		try {
			this.cadastroTela.salvar(telaEdicao);
			consultar();
			messages.info("Tela salva com sucesso!");
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:tela-table"));
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao salvar tela! \n Motivo:" + mensagem.getDetail());
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:tela-table"));

		}

	}
	
	
	
	public void excluir() {
		try {
			this.cadastroTela.excluir(telaSelecionada);
			telaSelecionada = null;

			consultar();

			messages.info("Tela excluída com sucesso!");
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao excluir tela! \n Motivo:" + mensagem);
		}

	}

	public Tela getTelaEdicao() {
		return telaEdicao;
	}

	public void setTelaEdicao(Tela telaEdicao) {
		this.telaEdicao = telaEdicao;
	}

	public Tela getTelaSelecionada() {
		return telaSelecionada;
	}

	public void setTelaSelecionada(Tela telaSelecionada) {
		this.telaSelecionada = telaSelecionada;
	}

	public List<Tela> getTodasTelas() {
		return todasTelas;
	}

	public void setTodasTelas(List<Tela> todasTelas) {
		this.todasTelas = todasTelas;
	}

	public List<Tela> getFiltroTelas() {
		return filtroTelas;
	}

	public void setFiltroTelas(List<Tela> filtroTelas) {
		this.filtroTelas = filtroTelas;
	}

	public TreeNode getRaiz() {
		return raiz;
	}

	public void setRaiz(TreeNode raiz) {
		this.raiz = raiz;
	}
	
	

}
