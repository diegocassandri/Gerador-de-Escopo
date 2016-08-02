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
	
	public Boolean pesquisaPorNivel(String nivel,CronogramaPadrao cronograma) {
		Query query = manager.createQuery(
				"From AtividadeHoraPadrao where nivelAtividade = :nivel and cronogramaPadrao = :cronograma",
				AtividadeHoraPadrao.class);
		query.setParameter("nivel", nivel);
		query.setParameter("cronograma", cronograma);
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
	

	public List<AtividadeHoraPadrao> atividadePorCronograma(CronogramaPadrao cronograma){
		return manager.createQuery("from AtividadeHoraPadrao where cronogramaPadrao = :cronograma order by atividadeHoraPai", AtividadeHoraPadrao.class)
				.setParameter("cronograma", cronograma)
				.getResultList();
	}

	public List<AtividadeHoraPadrao> raizes(CronogramaPadrao cronograma) {
		return (List<AtividadeHoraPadrao>) manager.createQuery(
				"from AtividadeHoraPadrao a where a.atividadeHoraPai is null and a.cronogramaPadrao = :cronograma",
				AtividadeHoraPadrao.class).setParameter("cronograma", cronograma).getResultList();
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
		Criteria criteria = session.createCriteria(AtividadeHoraPadrao.class).setProjection(Projections.sum("this.horaAtividade"));
		 Criterion c1 = Restrictions.eq("this.cronogramaPadrao", atividadeHoraPadrao.getCronogramaPadrao());
		 Criterion c2 = Restrictions.eq("this.atividadeHoraPai", atividadeHoraPadrao);
		 Criterion c3 = Restrictions.eq("this.codigo", atividadeHoraPadrao.getCodigo());
		  
		 Criterion c4 = Restrictions.or(Restrictions.and(c1, c2), c3);
		 if(atividadeHoraPadrao.getAtividadeHoraPai() == null){
			 criteria.add(c1);
			 criteria.add(c2);
		 } else{
			 criteria.add(c4);
		 }
		
		 Long soma = (Long) criteria.uniqueResult();
		
		return soma;

	}
	

	@SuppressWarnings("unchecked")
	public List<Object[]> retornaHierarquia(CronogramaPadrao cronograma){
		
		Query query = manager.createNativeQuery("SELECT atividadeHoraPai, SUM(horaAtividade) as soma FROM   atividadehorapadrao where cronogramaPadrao = :cronograma  GROUP BY ROLLUP (atividadeHoraPai)  HAVING atividadeHoraPai IS NOT NULL")
		.setParameter("cronograma", cronograma);
		
		return query.getResultList();
	}
	


}
