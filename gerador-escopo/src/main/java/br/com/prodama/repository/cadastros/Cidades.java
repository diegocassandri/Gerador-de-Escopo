package br.com.prodama.repository.cadastros;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.prodama.model.cadastro.geral.Cidade;
import br.com.prodama.model.cadastro.geral.Estado;
import br.com.prodama.util.Transactional;
import br.com.prodama.util.componentes.Webservicecep;

public class Cidades implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@Inject
	private Estados estados;

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

	@Transactional
	public Cidade pesquisaPorNome(Webservicecep xmlCep) {
		try {
			Query query = manager.createQuery("From Cidade where Upper(nome) = :cidade", Cidade.class);
			query.setParameter("cidade", xmlCep.getCidade().toUpperCase());
			return (Cidade) query.getSingleResult();
		} catch (Exception e) {
			if (e.getMessage().equals("No entity found for query")) {
				try {
					Cidade cidade = new Cidade();
					cidade.setNome(xmlCep.getCidade());
					cidade.setEstado(estados.pesquisaPorUf(xmlCep.getUf()));
					manager.merge(cidade);
					return cidade;
				} catch (Exception ex) {
					return null;
				}
			}
		}
		return null;
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
