package br.com.prodama.repository.proposta.projeto;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.prodama.model.proposta.projeto.AtividadeProjeto;
import br.com.prodama.model.proposta.projeto.DocAtividadeProjeto;



public class DocsAtividadesProjeto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public void adicionar(DocAtividadeProjeto docAtividadeProjeto) {
		manager.merge(docAtividadeProjeto);
		manager.flush();
		manager.clear();
	}

	public DocAtividadeProjeto pesquisaPorId(Long id) {
		return manager.find(DocAtividadeProjeto.class, id);
	}
	

	
	public void excluir(DocAtividadeProjeto docAtividadeProjeto) {
		docAtividadeProjeto = pesquisaPorId(docAtividadeProjeto.getCodigo());
		manager.remove(docAtividadeProjeto);

	}

	public List<DocAtividadeProjeto> todos(AtividadeProjeto atividade) {
		return manager.createQuery("from DocAtividadeProjeto d join fetch d.AtividadeProjeto  where d.AtividadeProjeto = :atividade", DocAtividadeProjeto.class)
				.setParameter("atividade", atividade).getResultList();
	}


}
