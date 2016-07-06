package br.com.prodama.repository.cadastro.geral;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.prodama.model.cadastro.geral.Equipe;
import br.com.prodama.model.cadastro.geral.NivelEquipe;

public class NiveisEquipe implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public void adicionar(NivelEquipe nivelEquipe) {
		manager.merge(nivelEquipe);
	}

	public NivelEquipe pesquisaPorId(Long id) {
		return manager.find(NivelEquipe.class, id);
	}
	
	public boolean pesquisaPorNome(NivelEquipe nivelEquipe) {
		Query query = manager.createQuery("From NivelEquipe where descricao = :descricao and equipe = :equipe", NivelEquipe.class);
		query.setParameter("descricao", nivelEquipe.getDescricao());
		query.setParameter("equipe", nivelEquipe.getEquipe());
		List<?> resultList = query.getResultList();
		if (resultList.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	public void excluir(NivelEquipe nivelEquipe) {
		nivelEquipe = pesquisaPorId(nivelEquipe.getCodigo());
		manager.remove(nivelEquipe);

	}

	public List<NivelEquipe> todos() {
		return manager.createQuery("from NivelEquipe", NivelEquipe.class).getResultList();
	}
	
	public List<NivelEquipe> niveisPorEquipe(Equipe equipe) {
		Query query = manager.createQuery("From NivelEquipe where equipe = :equipe", NivelEquipe.class);
		query.setParameter("equipe", equipe);
		@SuppressWarnings("unchecked")
		List<NivelEquipe> resultList = query.getResultList();
		return resultList;
	}
}
