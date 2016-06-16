package br.com.prodama.repository.cadastros;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.prodama.model.cadastro.Cidade;
import br.com.prodama.model.cadastro.Estado;


public class Cidades implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public void adicionar(Cidade cidade) {
		manager.merge(cidade);
	}

	public Cidade pesquisaPorId(Long id) {
		return manager.find(Cidade.class, id);
	}

	public boolean pesquisaPorNome(Cidade cidade) {
		Query query = manager.createQuery("From Cidade where nome = :cidade", Cidade.class);
		query.setParameter("cidade", cidade.getNome());
		List<?> resultList = query.getResultList();
		if (resultList.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	public void excluir(Cidade cidade) {
		cidade = pesquisaPorId(cidade.getCodigo());
		manager.remove(cidade);

	}

	public List<Cidade> todos() {
		return manager.createQuery("from Cidade", Cidade.class).getResultList();
	}

	public List<Cidade> CidadesPorEstado(Estado estado) {
		Query query = manager.createQuery("From Cidade where estado = :estado", Cidade.class);
		query.setParameter("estado", estado);
		@SuppressWarnings("unchecked")
		List<Cidade> resultList = query.getResultList();
		System.out.println("lista de Cidades " + resultList);
		return resultList;
	}

}
