package br.com.prodama.repository.cadastro.produto;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.prodama.model.cadastro.produto.GestaoModulo;



public class GestaoModulos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public void adicionar(GestaoModulo gestaoModulo) {
		manager.merge(gestaoModulo);
	}
	
	public GestaoModulo pesquisaPorId(Long id) {
		return manager.find(GestaoModulo.class, id);
	}
	
	public boolean pesquisaPorNome(GestaoModulo gestaoModulo) {
		Query query = manager.createQuery("From GestaoModulo where descricao = :descricao and modulo = :modulo", GestaoModulo.class);
		query.setParameter("descricao", gestaoModulo.getDescricao());
		query.setParameter("modulo", gestaoModulo.getModulo());
		List<?> resultList = query.getResultList();
		if (resultList.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	public void excluir(GestaoModulo gestaoModulo) {
		gestaoModulo = pesquisaPorId(gestaoModulo.getCodigo());
		manager.remove(gestaoModulo);

	}

	public List<GestaoModulo> todos() {
		return manager.createQuery("from GestaoModulo", GestaoModulo.class).getResultList();
	}


}
