package br.com.prodama.repository.cadastros;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.prodama.model.Grupo;
import br.com.prodama.model.Usuario;

public class Usuarios implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public void adicionar(Usuario usuario) {
		manager.merge(usuario);
	}

	public Usuario pesquisaPorId(Long id) {
		return manager.find(Usuario.class, id);
	}

	public boolean pesquisaPorNome(Usuario usuario) {
		Query query = manager.createQuery("From Usuario where usuario = :usuario", Usuario.class);
		query.setParameter("usuario", usuario.getUsuario());
		List<?> resultList = query.getResultList();
		if (resultList.isEmpty()) {
			return false;
		} else {
			return true;
		}

	}
	
	public boolean autenticaUsuario(String usuario,String senha) {
		Query query = manager.createQuery("From Usuario where usuario = :usuario And senha = :senha", Usuario.class);
		query.setParameter("usuario", usuario);
		query.setParameter("senha", senha);
		
		List<?> resultList = query.getResultList();
		if (resultList.isEmpty()) {
			return false;
		} else {
			return true;
		}

	}
	public void excluir(Usuario usuario) {
		usuario = pesquisaPorId(usuario.getCodigo());
		manager.remove(usuario);

	}

	public List<Usuario> todos() {
		return manager.createQuery("from Usuario", Usuario.class).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Grupo> gruposNaoAssociados(Usuario usuario) {

		Query query = manager.createQuery("Select g From Grupo g Where not exists (Select u from g.usuarios u Where u = :usuario)", Grupo.class);
		query.setParameter("usuario", usuario);
		return query.getResultList();

	}

	public List<Grupo> gruposAssociados(Usuario usuario) {
		return usuario.getGrupos();
	}

	

}
