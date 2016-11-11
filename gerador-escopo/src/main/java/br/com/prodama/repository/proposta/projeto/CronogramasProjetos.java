package br.com.prodama.repository.proposta.projeto;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import br.com.prodama.enun.Status;
import br.com.prodama.enun.TipoPessoa;
import br.com.prodama.model.cadastro.cronograma.AtividadeHoraPadrao;
import br.com.prodama.model.cadastro.geral.Empresa;
import br.com.prodama.model.cadastro.geral.Filial;
import br.com.prodama.model.cadastro.geral.Pessoa;
import br.com.prodama.model.cadastro.produto.Produto;
import br.com.prodama.model.proposta.projeto.CronogramaProjeto;

public class CronogramasProjetos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public void adicionar(CronogramaProjeto cronogramaProjeto) {
		manager.merge(cronogramaProjeto);
		manager.flush();
		manager.clear();
	}

	public void recarregar(CronogramaProjeto cronogramaProjeto) {
		manager.refresh(cronogramaProjeto);
	}

	public CronogramaProjeto pesquisaPorId(Long id) {
		return manager.find(CronogramaProjeto.class, id);
	}

	public boolean pesquisaPorNome(CronogramaProjeto cronogramaProjeto) {
		Query query = manager.createQuery("From CronogramaProjeto where descricao = :descricao",
				CronogramaProjeto.class);
		query.setParameter("descricao", cronogramaProjeto.getDescricao());
		List<?> resultList = query.getResultList();
		if (resultList.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	public void excluir(CronogramaProjeto cronogramaProjeto) {
		cronogramaProjeto = pesquisaPorId(cronogramaProjeto.getCodigo());
		manager.remove(cronogramaProjeto);

	}

	public List<CronogramaProjeto> todos() {
		return manager.createQuery("from CronogramaProjeto", CronogramaProjeto.class).getResultList();

	}

	@SuppressWarnings("unchecked")
	public List<CronogramaProjeto> pesquisaFiltros(Long codigo, String descricao, Empresa empresa, Filial filial,
			Produto produto, Pessoa cliente) {

		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(CronogramaProjeto.class);
		/*
		 * query.setParameter("fantasia", "%" + fantasia + "%");
		 * query.setParameter("status", status);
		 */

		return criteria.list();

	}

	/*
	public List<CronogramaProjeto> pesquisaFiltros(Long codigo, String descricao, Empresa empresa, Filial filial,
			Produto produto, Pessoa cliente) {

		Query query;
		String hql = "From CronogramaProjeto where 1=1 ";

		if ((codigo != null) && (codigo != 0))
			hql += " and codigo = :codigo";
		if (empresa != null)
			hql += " and Empresa = :empresa ";
		if (filial != null)
			hql += " and Filial = :filial ";
		if (produto != null)
			hql += " and Produto = :produto ";

		query = manager.createQuery(hql, CronogramaProjeto.class);

		query.setParameter("codigo", codigo);
		query.setParameter("empresa", empresa);
		query.setParameter("filial", filial);
		query.setParameter("produto", produto);
		/*
		 * query.setParameter("fantasia", "%" + fantasia + "%");
		 * query.setParameter("status", status);
		 

		return query.getResultList();

	}
	
	 */

}
