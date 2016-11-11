package br.com.prodama.repository.cadastro.produto;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.prodama.model.cadastro.produto.ProcessoGestao;



public class Processos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public void adicionar(ProcessoGestao processoGestao) {
		manager.merge(processoGestao);
	}
	
	public ProcessoGestao pesquisaPorId(Long id) {
		return manager.find(ProcessoGestao.class, id);
	}
	
	public boolean pesquisaPorNome(ProcessoGestao processoGestao) {
		Query query = manager.createQuery("From ProcessoGestao where descricao = :descricao and gestaoModulo = :gestao", ProcessoGestao.class);
		query.setParameter("descricao", processoGestao.getDescricao());
		query.setParameter("gestao", processoGestao.getGestaoModulo());
		List<?> resultList = query.getResultList();
		if (resultList.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	public void excluir(ProcessoGestao processoGestao) {
		processoGestao = pesquisaPorId(processoGestao.getCodigo());
		manager.remove(processoGestao);

	}

	public List<ProcessoGestao> todos() {
		return manager.createQuery("from ProcessoGestao", ProcessoGestao.class).getResultList();
	}


}
