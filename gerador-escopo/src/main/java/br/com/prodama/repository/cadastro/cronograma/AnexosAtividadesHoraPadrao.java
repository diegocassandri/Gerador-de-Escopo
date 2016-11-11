package br.com.prodama.repository.cadastro.cronograma;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.prodama.model.cadastro.cronograma.AnexoAtividadeHoraPadrao;
import br.com.prodama.model.cadastro.cronograma.DocAtividadeHoraPadrao;

public class AnexosAtividadesHoraPadrao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public void adicionar(AnexoAtividadeHoraPadrao anexoAtividadeHoraPadrao) {
		manager.merge(anexoAtividadeHoraPadrao);
		manager.flush();
		manager.clear();
	}

	public AnexoAtividadeHoraPadrao pesquisaPorId(Long id) {
		return manager.find(AnexoAtividadeHoraPadrao.class, id);
	}

	public boolean pesquisaPorNome(AnexoAtividadeHoraPadrao anexoAtividadeHoraPadrao) {
		Query query = manager.createQuery(
				"From AnexoAtividadeHoraPadrao where descricao = :descricao and docAtividadeHoraPadrao = :doc",
				AnexoAtividadeHoraPadrao.class);
		query.setParameter("descricao", anexoAtividadeHoraPadrao.getDescricao());
		query.setParameter("doc", anexoAtividadeHoraPadrao.getDocAtividadeHoraPadrao());
		List<?> resultList = query.getResultList();
		if (resultList.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	public void excluir(AnexoAtividadeHoraPadrao anexoAtividadeHoraPadrao) {
		anexoAtividadeHoraPadrao = pesquisaPorId(anexoAtividadeHoraPadrao.getCodigo());
		manager.remove(anexoAtividadeHoraPadrao);

	}

	public List<AnexoAtividadeHoraPadrao> todos(DocAtividadeHoraPadrao documento) {
		return manager.createQuery("from AnexoAtividadeHoraPadrao a join fetch a.docAtividadeHoraPadrao where a.docAtividadeHoraPadrao = :documento",
				AnexoAtividadeHoraPadrao.class).setParameter("documento", documento).getResultList();

	}

}
