package br.com.prodama.repository.proposta.projeto;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.prodama.model.proposta.projeto.AnexoAtividadeProjeto;


public class AnexosAtividadesProjeto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public void adicionar(AnexoAtividadeProjeto anexoAtividadeProjeto) {
		manager.merge(anexoAtividadeProjeto);
		manager.flush();
		manager.clear();
	}

	public AnexoAtividadeProjeto pesquisaPorId(Long id) {
		return manager.find(AnexoAtividadeProjeto.class, id);
	}

	public boolean pesquisaPorNome(AnexoAtividadeProjeto anexoAtividadeProjeto) {
		Query query = manager.createQuery(
				"From AnexoAtividadeProjeto where descricao = :descricao and docAtividadeProjeto = :doc",
				AnexoAtividadeProjeto.class);
		query.setParameter("descricao", anexoAtividadeProjeto.getDescricao());
		query.setParameter("doc", anexoAtividadeProjeto.getDocAtividadeProjeto());
		List<?> resultList = query.getResultList();
		if (resultList.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	public void excluir(AnexoAtividadeProjeto anexoAtividadeProjeto) {
		anexoAtividadeProjeto = pesquisaPorId(anexoAtividadeProjeto.getCodigo());
		manager.remove(anexoAtividadeProjeto);

	}

	public List<AnexoAtividadeProjeto> todos(DocsAtividadesProjeto documento) {
		return manager.createQuery("from AnexoAtividadeProjeto a join fetch a.docAtividadeProjeto where a.docAtividadeProjeto = :documento",
				AnexoAtividadeProjeto.class).setParameter("documento", documento).getResultList();

	}

}
