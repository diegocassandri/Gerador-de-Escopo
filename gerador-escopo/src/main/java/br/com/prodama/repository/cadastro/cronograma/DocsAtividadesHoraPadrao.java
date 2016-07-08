package br.com.prodama.repository.cadastro.cronograma;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.prodama.model.cadastro.cronograma.DocAtividadeHoraPadrao;

public class DocsAtividadesHoraPadrao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public void adicionar(DocAtividadeHoraPadrao docAtividadeHoraPadrao) {
		manager.merge(docAtividadeHoraPadrao);
	}

	public DocAtividadeHoraPadrao pesquisaPorId(Long id) {
		return manager.find(DocAtividadeHoraPadrao.class, id);
	}
	
	public boolean pesquisaPorNome(DocAtividadeHoraPadrao docAtividadeHoraPadrao) {
		Query query = manager.createQuery("From DocAtividadeHoraPadrao where descricao = :descricao and AtividadeHoraPadrao = :atividade", DocAtividadeHoraPadrao.class);
		query.setParameter("descricao", docAtividadeHoraPadrao.getDescricao());
		query.setParameter("atividade", docAtividadeHoraPadrao.getAtividadeHoraPadrao());
		List<?> resultList = query.getResultList();
		if (resultList.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	
	public void excluir(DocAtividadeHoraPadrao docAtividadeHoraPadrao) {
		docAtividadeHoraPadrao = pesquisaPorId(docAtividadeHoraPadrao.getCodigo());
		manager.remove(docAtividadeHoraPadrao);

	}

	public List<DocAtividadeHoraPadrao> todos() {
		return manager.createQuery("from DocAtividadeHoraPadrao", DocAtividadeHoraPadrao.class).getResultList();
	}


}