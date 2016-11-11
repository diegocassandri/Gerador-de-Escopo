package br.com.prodama.repository.proposta.projeto;

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

import br.com.prodama.model.proposta.projeto.AtividadeProjeto;
import br.com.prodama.model.proposta.projeto.CronogramaProjeto;



public class AtividadesProjeto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	List<AtividadeProjeto> listaAuxiliar;

	public void adicionar(AtividadeProjeto atividadeProjeto) {
		manager.merge(atividadeProjeto);
		manager.flush();
		manager.clear();
	}

	public AtividadeProjeto pesquisaPorId(Long id) {
		return manager.find(AtividadeProjeto.class, id);
	}

	public boolean pesquisaPorNome(AtividadeProjeto atividadeProjeto) {
		Query query = manager.createQuery(
				"From AtividadeProjeto where descricao = :descricao and cronogramaProjeto = :cronograma",
				AtividadeProjeto.class);
		query.setParameter("descricao", atividadeProjeto.getDescricao());
		query.setParameter("cronograma", atividadeProjeto.getCronogramaProjeto());
		List<?> resultList = query.getResultList();
		if (resultList.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	public Boolean pesquisaPorNivel(String nivel,CronogramaProjeto cronograma) {
		Query query = manager.createQuery(
				"From AtividadeProjeto where nivelAtividade = :nivel and CronogramaProjeto = :cronograma",
				AtividadeProjeto.class);
		query.setParameter("nivel", nivel);
		query.setParameter("cronograma", cronograma);
		List<?> resultList = query.getResultList();
		if (resultList.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	public void excluir(AtividadeProjeto AtividadeProjeto) {
		AtividadeProjeto = pesquisaPorId(AtividadeProjeto.getCodigo());
		manager.remove(AtividadeProjeto);

	}

	public List<AtividadeProjeto> todos() {
		return manager.createQuery("from AtividadeProjeto", AtividadeProjeto.class).getResultList();
	}
	

	public List<AtividadeProjeto> atividadePorCronograma(CronogramaProjeto cronograma){
		return manager.createQuery("from AtividadeProjeto where CronogramaProjeto = :cronograma order by atividadeHoraPai", AtividadeProjeto.class)
				.setParameter("cronograma", cronograma)
				.getResultList();
	}

	public List<AtividadeProjeto> raizes(CronogramaProjeto cronograma) {
		return (List<AtividadeProjeto>) manager.createQuery(
				"from AtividadeProjeto a where a.atividadeHoraPai is null and a.CronogramaProjeto = :cronograma",
				AtividadeProjeto.class).setParameter("cronograma", cronograma).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<AtividadeProjeto> filhos(AtividadeProjeto AtividadeProjeto) {
		Query query = manager
				.createQuery(
						"From AtividadeProjeto a where a.atividadeHoraPai = :atividadePai and a.CronogramaProjeto = :cronograma",
						AtividadeProjeto.class)
				.setParameter("cronograma", AtividadeProjeto.getCronogramaProjeto())
				.setParameter("atividadePai", AtividadeProjeto);
		return query.getResultList();
	}

	public Long somaFilhos(AtividadeProjeto AtividadeProjeto) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(AtividadeProjeto.class).setProjection(Projections.sum("this.horaAtividade"));
		 Criterion c1 = Restrictions.eq("this.CronogramaProjeto", AtividadeProjeto.getCronogramaProjeto());
		 Criterion c2 = Restrictions.eq("this.atividadeHoraPai", AtividadeProjeto);
		 Criterion c3 = Restrictions.eq("this.codigo", AtividadeProjeto.getCodigo());
		  
		 Criterion c4 = Restrictions.or(Restrictions.and(c1, c2), c3);
		 if(AtividadeProjeto.getAtividadeHoraPai() == null){
			 criteria.add(c1);
			 criteria.add(c2);
		 } else{
			 criteria.add(c4);
		 }
		
		 Long soma = (Long) criteria.uniqueResult();
		
		return soma;

	}
	

	@SuppressWarnings("unchecked")
	public List<Object[]> retornaHierarquia(CronogramaProjeto cronograma){
		
		Query query = manager.createNativeQuery("SELECT atividadeHoraPai, SUM(horaAtividade) as soma,SUM(valor) as valor FROM   AtividadeProjeto where CronogramaProjeto = :cronograma  GROUP BY ROLLUP (atividadeHoraPai)  HAVING atividadeHoraPai IS NOT NULL")
		.setParameter("cronograma", cronograma);
		
		return query.getResultList();
	}
}
