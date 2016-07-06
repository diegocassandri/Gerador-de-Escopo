package br.com.prodama.controller.cadastro.produto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.prodama.enun.Status;
import br.com.prodama.model.cadastro.produto.Produto;
import br.com.prodama.repository.cadastro.produto.Produtos;
import br.com.prodama.service.cadastro.produto.CadastroProduto;
import br.com.prodama.util.FacesMessages;

@Named
@ViewScoped
public class CadastroProdutoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FacesMessages messages;
	
	@Inject
	private CadastroProduto cadastroProduto;

	@Inject
	private Produtos produtos;


	private Produto produtoEdicao = new Produto();
	private Produto produtoSelecionado;
	private List<Produto> todosProdutos;
	private List<Produto> filtroProdutos;

	@PostConstruct
	public void prepararNovoCadastro() {
		produtoEdicao = new Produto();
	}

	public void salvar() {
		try {
			this.cadastroProduto.salvar(produtoEdicao);
			consultar();
			messages.info("Produto salvo com sucesso!");
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:produto-table"));
			RequestContext.getCurrentInstance().execute("PF('edicaoProdutoDialog').hide()");
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao salvar Produto! \n Motivo:" + mensagem.getDetail());
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:produto-table"));
		}

	}

	public void excluir() {
		try {
			this.cadastroProduto.excluir(produtoEdicao);
			produtoSelecionado = null;
			consultar();
			messages.info("Produto excluído com sucesso!");
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao excluir Produto! \n Motivo:" + mensagem);
		}

	}

	public void consultar() {
		todosProdutos = produtos.todos();
	}

	public List<Status> getStatusList() {
		return Arrays.asList(Status.values());
	}

	public Produto getProdutoEdicao() {
		return produtoEdicao;
	}

	public void setProdutoEdicao(Produto produtoEdicao) {
		this.produtoEdicao = produtoEdicao;
	}

	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}

	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}

	public List<Produto> getTodosProdutos() {
		return todosProdutos;
	}

	public void setTodosProdutos(List<Produto> todosProdutos) {
		this.todosProdutos = todosProdutos;
	}

	public List<Produto> getFiltroProdutos() {
		return filtroProdutos;
	}

	public void setFiltroProdutos(List<Produto> filtroProdutos) {
		this.filtroProdutos = filtroProdutos;
	}
	
	

}
