package br.com.prodama.repository.cadastros;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.prodama.model.cadastro.Empresa;

public class Empresas implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public void adicionar(Empresa empresa){
		manager.merge(empresa);
	}
	
	public Empresa pesquisaPorId(Long id) {
		return manager.find(Empresa.class, id);
	}
	
	public boolean pesquisaPorNome(Empresa empresa) {
		Query query = manager.createQuery("From Empresa where RazaoSocial = :empresa", Empresa.class);
		query.setParameter("empresa", empresa.getRazaoSocial());
		List<?> resultList = query.getResultList();
		if (resultList.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	public void excluir(Empresa empresa) {
		empresa = pesquisaPorId(empresa.getCodigo());
		manager.remove(empresa);

	}

	public List<Empresa> todos() {
		return manager.createQuery("from Empresa", Empresa.class).getResultList();
	}
	

}
