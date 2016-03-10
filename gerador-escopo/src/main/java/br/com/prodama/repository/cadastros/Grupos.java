package br.com.prodama.repository.cadastros;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.prodama.model.Grupo;
import br.com.prodama.model.Usuario;



public class Grupos implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;

	public void adicionar(Grupo grupo) {
		manager.merge(grupo);
	}
	
	public Grupo pesquisaPorId(Long id) {
		return manager.find(Grupo.class, id);
	}
	
	public boolean pesquisaPorNome(Grupo grupo) {
		Query query = manager.createQuery("From Grupo where descricao = :descricao", Grupo.class);
		query.setParameter("descricao", grupo.getNome());
		List<?> resultList = query.getResultList();
		if (resultList.isEmpty()) {
			return false;
		} else {
			return true;
		}

	} 
	
	public void excluir(Grupo grupo) {
		grupo = pesquisaPorId(grupo.getCodigo());
		manager.remove(grupo);

	}

	public List<Grupo> todos() {
		return manager.createQuery("from Grupo", Grupo.class).getResultList();
	}

   public List<Usuario> usariosAssociados(Grupo grupo){	
		return grupo.getUsuarios();
	}
	

}
