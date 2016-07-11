package br.com.prodama.repository.cadastro.cronograma;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.prodama.model.cadastro.cronograma.AtividadeHoraPadrao;
import br.com.prodama.model.cadastro.cronograma.CronogramaPadrao;

public class AtividadesHoraPadrao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public void adicionar(AtividadeHoraPadrao atividadeHoraPadrao) {
		manager.merge(atividadeHoraPadrao);
	}

	public AtividadeHoraPadrao pesquisaPorId(Long id) {
		return manager.find(AtividadeHoraPadrao.class, id);
	}
	
	public boolean pesquisaPorNome(AtividadeHoraPadrao atividadeHoraPadrao) {
		Query query = manager.createQuery("From AtividadeHoraPadrao where descricao = :descricao and cronogramaPadrao = :cronograma", AtividadeHoraPadrao.class);
		query.setParameter("descricao", atividadeHoraPadrao.getDescricao());
		query.setParameter("cronograma", atividadeHoraPadrao.getCronogramaPadrao());
		List<?> resultList = query.getResultList();
		if (resultList.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	
	public void excluir(AtividadeHoraPadrao atividadeHoraPadrao) {
		atividadeHoraPadrao = pesquisaPorId(atividadeHoraPadrao.getCodigo());
		manager.remove(atividadeHoraPadrao);

	}

	public List<AtividadeHoraPadrao> todos() {
		return manager.createQuery("from AtividadeHoraPadrao", AtividadeHoraPadrao.class).getResultList();
	}
	
	public List<AtividadeHoraPadrao> raizes(CronogramaPadrao cronograma) {

		return (List<AtividadeHoraPadrao>) manager.createQuery("from AtividadeHoraPadrao where atividadeHoraPai is null and cronogramaPadrao = :cronograma", AtividadeHoraPadrao.class).setParameter("cronograma", cronograma).getResultList();
		 
	}


}
