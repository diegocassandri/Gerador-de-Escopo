package br.com.prodama.service.cadastro.produto;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.prodama.model.cadastro.produto.Produto;
import br.com.prodama.repository.cadastro.produto.Produtos;
import br.com.prodama.service.NegocioException;
import br.com.prodama.util.Transactional;

public class CadastroProduto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Produtos produtos;
	
	@Transactional
	public void salvar(Produto produto) throws NegocioException {
		if (produtos.pesquisaPorNome(produto) && (produto.getCodigo() == null || produto.getCodigo()==0)) {
			throw new NegocioException(
					"Já existe um produto com essa descrição: "+ produto.getDescricao());
		}
		
		this.produtos.adicionar(produto);
	}

	@Transactional
	public void excluir(Produto produto) {
		produtos.excluir(produto);
		
	}


}
