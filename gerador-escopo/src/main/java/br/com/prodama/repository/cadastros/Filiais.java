package br.com.prodama.repository.cadastros;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.prodama.model.cadastro.Filial;

public class Filiais implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public void adicionar(Filial filial){
		manager.merge(filial);
	}
	
	public Filial pesquisaPorId(Long id) {
		return manager.find(Filial.class, id);
	}
	
	public boolean pesquisaPorNome(Filial filial) {
		Query query = manager.createQuery("From Empresa where RazaoSocial = :filial", Filial.class);
		query.setParameter("filial", filial.getRazaoSocial());
		List<?> resultList = query.getResultList();
		if (resultList.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	public void excluir(Filial filial) {
		filial = pesquisaPorId(filial.getCodigo());
		manager.remove(filial);

	}

	public List<Filial> todos() {
		return manager.createQuery("from Filial", Filial.class).getResultList();
	}
	

}
