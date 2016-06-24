package br.com.prodama.repository.cadastro.geral;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.prodama.model.cadastro.geral.FaixaColaborador;



public class FaixaColaboradores implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public void adicionar(FaixaColaborador faixaColaborador) {
		manager.merge(faixaColaborador);
	}
	
	public FaixaColaborador pesquisaPorId(Long id) {
		return manager.find(FaixaColaborador.class, id);
	}
	
	public boolean pesquisaPorNome(FaixaColaborador faixaColaborador) {
		Query query = manager.createQuery("From FaixaColaborador where indice = :indice", FaixaColaborador.class);
		query.setParameter("indice", faixaColaborador.getIndice());
		List<?> resultList = query.getResultList();
		if (resultList.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	public void excluir(FaixaColaborador faixaColaborador) {
		faixaColaborador = pesquisaPorId(faixaColaborador.getCodigo());
		manager.remove(faixaColaborador);

	}

	public List<FaixaColaborador> todos() {
		return manager.createQuery("from FaixaColaborador", FaixaColaborador.class).getResultList();
	}

}
