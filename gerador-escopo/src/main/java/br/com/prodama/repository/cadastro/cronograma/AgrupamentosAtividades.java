package br.com.prodama.repository.cadastro.cronograma;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.prodama.model.cadastro.cronograma.AgrupamentoAtividade;


public class AgrupamentosAtividades implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public void adicionar(AgrupamentoAtividade agrupamentoAtidade) {
		manager.merge(agrupamentoAtidade);
	}

	public AgrupamentoAtividade pesquisaPorId(Long id) {
		return manager.find(AgrupamentoAtividade.class, id);
	}
	
	public boolean pesquisaPorNome(AgrupamentoAtividade agrupamentoAtidade) {
		Query query = manager.createQuery("From AgrupamentoAtidade where descricao = :descricao and atividadeHoraPadra = :atividade", AgrupamentoAtividade.class);
		query.setParameter("descricao", agrupamentoAtidade.getDescricao());
		query.setParameter("atividade", agrupamentoAtidade.getAtividadeHoraPrao());
		List<?> resultList = query.getResultList();
		if (resultList.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	
	public void excluir(AgrupamentoAtividade agrupamentoAtidade) {
		agrupamentoAtidade = pesquisaPorId(agrupamentoAtidade.getCodigo());
		manager.remove(agrupamentoAtidade);

	}

	public List<AgrupamentoAtividade> todos() {
		return manager.createQuery("from AgrupamentoAtidade", AgrupamentoAtividade.class).getResultList();
	}


}
