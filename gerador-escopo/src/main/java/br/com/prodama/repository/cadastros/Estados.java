package br.com.prodama.repository.cadastros;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.prodama.model.cadastro.geral.Estado;


public class Estados implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public void adicionar(Estado estado){
		manager.merge(estado);
	}
	
	public Estado pesquisaPorId(Long id) {
		return manager.find(Estado.class, id);
	}
	
	public boolean pesquisaPorNome(Estado estado) {
		Query query = manager.createQuery("From Estado where nome = :estado", Estado.class);
		query.setParameter("estado", estado.getNome());
		List<?> resultList = query.getResultList();
		if (resultList.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	public Estado pesquisaPorUf(String sigla) {
		Query query = manager.createQuery("From Estado where sigla = :estado", Estado.class);
		query.setParameter("estado", sigla);
		return (Estado) query.getSingleResult();
		
	}
	
	public void excluir(Estado estado) {
		estado = pesquisaPorId(estado.getCodigo());
		manager.remove(estado);

	}

	public List<Estado> todos() {
		return manager.createQuery("from Estado", Estado.class).getResultList();
	}
	

}
