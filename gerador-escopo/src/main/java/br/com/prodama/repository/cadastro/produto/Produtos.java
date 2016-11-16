package br.com.prodama.repository.cadastro.produto;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.prodama.model.cadastro.produto.Produto;

public class Produtos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public void adicionar(Produto produto) {
		manager.merge(produto);
	}
	
	public Produto pesquisaPorId(Long id) {
		return manager.find(Produto.class, id);
	}
	
	public boolean pesquisaPorNome(Produto produto) {
		Query query = manager.createQuery("From Produto where descricao = :descricao", Produto.class);
		query.setParameter("descricao", produto.getDescricao());
		List<?> resultList = query.getResultList();
		if (resultList.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	public void excluir(Produto produto) {
		produto = pesquisaPorId(produto.getCodigo());
		manager.remove(produto);

	}

	public List<Produto> todos() {
		return manager.createQuery("from Produto", Produto.class).getResultList();
	}

}
