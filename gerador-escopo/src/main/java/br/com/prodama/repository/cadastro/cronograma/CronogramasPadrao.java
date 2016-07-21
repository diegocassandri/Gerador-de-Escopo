package br.com.prodama.repository.cadastro.cronograma;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.prodama.model.cadastro.cronograma.CronogramaPadrao;

public class CronogramasPadrao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public void adicionar(CronogramaPadrao cronogramaPadrao) {
		manager.merge(cronogramaPadrao);
		manager.flush();
		manager.clear();
	}
	
	public void recarregar(CronogramaPadrao cronogramaPadrao){
		manager.refresh(cronogramaPadrao);
	}

	public CronogramaPadrao pesquisaPorId(Long id) {
		return manager.find(CronogramaPadrao.class, id);
	}
	
	public boolean pesquisaPorNome(CronogramaPadrao cronogramaPadrao) {
		Query query = manager.createQuery("From CronogramaPadrao where descricao = :descricao", CronogramaPadrao.class);
		query.setParameter("descricao", cronogramaPadrao.getDescricao());
		List<?> resultList = query.getResultList();
		if (resultList.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	
	public void excluir(CronogramaPadrao cronogramaPadrao) {
		cronogramaPadrao = pesquisaPorId(cronogramaPadrao.getCodigo());
		manager.remove(cronogramaPadrao);

	}

	public List<CronogramaPadrao> todos() {
		return manager.createQuery("from CronogramaPadrao", CronogramaPadrao.class).getResultList();
		
	}


}
