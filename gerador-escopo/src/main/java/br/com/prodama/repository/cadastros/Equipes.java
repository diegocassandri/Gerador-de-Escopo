package br.com.prodama.repository.cadastros;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.prodama.model.cadastro.Equipe;



public class Equipes implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public void adicionar(Equipe equipe){
		manager.merge(equipe);
	}
	
	public Equipe pesquisaPorId(Long id) {
		return manager.find(Equipe.class, id);
	}
	
	public boolean pesquisaPorNome(Equipe equipe) {
		Query query = manager.createQuery("From Equipe where descricao = :equipe", Equipe.class);
		query.setParameter("equipe",equipe.getDescricao());
		List<?> resultList = query.getResultList();
		if (resultList.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	

	public void excluir(Equipe equipe) {
		equipe = pesquisaPorId(equipe.getCodigo());
		manager.remove(equipe);

	}

	public List<Equipe> todos() {
		return manager.createQuery("from Equipe", Equipe.class).getResultList();
	}
	

}
