package br.com.prodama.repository.cadastro.produto;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.prodama.model.cadastro.produto.Modulo;



public class Modulos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public void adicionar(Modulo modulo) {
		manager.merge(modulo);
	}
	
	public Modulo pesquisaPorId(Long id) {
		return manager.find(Modulo.class, id);
	}

	public Modulo pesquisaModulo(Modulo modulo) {
		
		Query query = manager.createQuery("From Modulo m JOIN FETCH m.listaGestoesModulo  where  m = :codigo", Modulo.class);
		query.setParameter("codigo", modulo);
		return (Modulo) query.getSingleResult();
	}
	
	public boolean pesquisaPorNome(Modulo modulo) {
		Query query = manager.createQuery("From Modulo where descricao = :descricao and produto = :produto", Modulo.class);
		query.setParameter("descricao", modulo.getDescricao());
		query.setParameter("produto", modulo.getProduto());
		List<?> resultList = query.getResultList();
		if (resultList.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	public Modulo pesquisaPorNomeCodigo(Modulo modulo) {
		Query query = manager.createQuery("From Modulo where descricao = :descricao and produto = :produto", Modulo.class);
		query.setParameter("descricao", modulo.getDescricao());
		query.setParameter("produto", modulo.getProduto());
		return (Modulo) query.getSingleResult();
		
	}
	
	public void excluir(Modulo modulo) {
		modulo = pesquisaPorId(modulo.getCodigo());
		manager.remove(modulo);

	}

	public List<Modulo> todos() {
		return manager.createQuery("from Modulo", Modulo.class).getResultList();
	}


}
