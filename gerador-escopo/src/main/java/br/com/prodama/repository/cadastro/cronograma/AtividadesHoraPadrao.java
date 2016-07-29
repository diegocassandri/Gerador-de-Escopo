package br.com.prodama.repository.cadastro.cronograma;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import br.com.prodama.model.cadastro.cronograma.AtividadeHoraPadrao;
import br.com.prodama.model.cadastro.cronograma.CronogramaPadrao;

public class AtividadesHoraPadrao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	List<AtividadeHoraPadrao> listaAuxiliar;

	public void adicionar(AtividadeHoraPadrao atividadeHoraPadrao) {
		manager.merge(atividadeHoraPadrao);
		manager.flush();
		manager.clear();
	}

	public AtividadeHoraPadrao pesquisaPorId(Long id) {
		return manager.find(AtividadeHoraPadrao.class, id);
	}

	public boolean pesquisaPorNome(AtividadeHoraPadrao atividadeHoraPadrao) {
		Query query = manager.createQuery(
				"From AtividadeHoraPadrao where descricao = :descricao and cronogramaPadrao = :cronograma",
				AtividadeHoraPadrao.class);
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
		return (List<AtividadeHoraPadrao>) manager.createQuery(
				"from AtividadeHoraPadrao a where a.atividadeHoraPai is null and a.cronogramaPadrao = :cronograma",
				AtividadeHoraPadrao.class).setParameter("cronograma", cronograma).getResultList();
		/*
		 * Query query = manager.
		 * createQuery("From AtividadeHoraPadrao a JOIN FETCH a.subAtividadeHoras  where a.cronogramaPadrao = :cronograma"
		 * , AtividadeHoraPadrao.class).setParameter("cronograma", cronograma);
		 * List<AtividadeHoraPadrao> listaAtividades = query.getResultList(); if
		 * (!listaAtividades.isEmpty()) { for (AtividadeHoraPadrao
		 * ativiadeHoraPadrao : listaAtividades) { if
		 * (ativiadeHoraPadrao.getAtividadeHoraPai() != null) {
		 * listaAtividades.remove(ativiadeHoraPadrao); } }
		 * 
		 * } return listaAtividades;
		 */

	}

	@SuppressWarnings("unchecked")
	public List<AtividadeHoraPadrao> filhos(AtividadeHoraPadrao atividadeHoraPadrao) {
		Query query = manager
				.createQuery(
						"From AtividadeHoraPadrao a where a.atividadeHoraPai = :atividadePai and a.cronogramaPadrao = :cronograma",
						AtividadeHoraPadrao.class)
				.setParameter("cronograma", atividadeHoraPadrao.getCronogramaPadrao())
				.setParameter("atividadePai", atividadeHoraPadrao);
		return query.getResultList();
	}

	public Long somaFilhos(AtividadeHoraPadrao atividadeHoraPadrao) {
		Session session = manager.unwrap(Session.class);
		/*Criteria criteria = session.createCriteria(AtividadeHoraPadrao.class)
				.add(Restrictions.eq("cronogramaPadrao", atividadeHoraPadrao.getCronogramaPadrao()))
				.add(Restrictions.eq("atividadeHoraPai", atividadeHoraPadrao));
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.sum("horaAtividade"));
		criteria.setProjection(projList);
		List list = criteria.list();*/

		Criteria criteria = session.createCriteria(AtividadeHoraPadrao.class)
				.setProjection(Projections.sum("this.horaAtividade"));

		 Criterion c1 = Restrictions.eq("this.cronogramaPadrao", atividadeHoraPadrao.getCronogramaPadrao());
		 Criterion c2 = Restrictions.eq("this.atividadeHoraPai", atividadeHoraPadrao);
		 Criterion c3 = Restrictions.eq("this.codigo", atividadeHoraPadrao.getCodigo());
		  
		 Criterion c4 = Restrictions.or(Restrictions.and(c1, c2), c3);
		 if(atividadeHoraPadrao.getAtividadeHoraPai()  == null){
			 criteria.add(c4);
		 }else{
			 criteria.add(c1);
			 criteria.add(c2);
			 criteria.add(c3);
			 
		 } 
		 Long soma = (Long) criteria.uniqueResult();
		
		return soma;

	}

}
