package br.com.prodama.repository.cadastros;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.prodama.model.cadastro.Tela;

public class Telas implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public void adicionar(Tela tela){
		manager.merge(tela);
	}
	
	public Tela pesquisaPorId(Long id) {
		return manager.find(Tela.class, id);
	}
	
	public boolean pesquisaPorNome(Tela tela) {
		Query query = manager.createQuery("From Tela where descricao = :tela", Tela.class);
		query.setParameter("tela", tela.getDescricao());
		List<?> resultList = query.getResultList();
		if (resultList.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	public void excluir(Tela tela) {
		tela = pesquisaPorId(tela.getCodigo());
		manager.remove(tela);
	}

	public List<Tela> todos() {
		return manager.createQuery("from Tela", Tela.class).getResultList();
	}
	
	public List<Tela> raizes() {

		return (List<Tela>) manager.createQuery("from Tela where telaPai is null", Tela.class).getResultList();
		 
	}
	
}
