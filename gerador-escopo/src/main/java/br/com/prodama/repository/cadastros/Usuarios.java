package br.com.prodama.repository.cadastros;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.prodama.model.cadastro.Grupo;
import br.com.prodama.model.cadastro.Usuario;

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
	
	public Usuario pesquisaPorN(String usuario) {
		return manager.find(Usuario.class, usuario);
	}
	public void alterarMudarSenha(Usuario usuario){
		usuario.setMudarSenha(false);
		manager.merge(usuario);
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
		System.out.println(usuario + " " + senha);
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
	
	public boolean verificaMudarSenha(String usuario){
		Query query = manager.createQuery("From Usuario where usuario = :usuario And mudarSenha = 1 ", Usuario.class);
		query.setParameter("usuario", usuario);
		List<?> resultList = query.getResultList();
		if (resultList.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	public Usuario retornaUsuarioPorNome(String usuario) {
		
		Query query = manager.createQuery("From Usuario where usuario = :usuario", Usuario.class);
		query.setParameter("usuario", usuario);
		List<?> resultList = query.getResultList();
		System.out.println(" ---------> " + resultList.get(0));
		return (Usuario) resultList.get(0);
		
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

	@SuppressWarnings("unchecked")
	public List<Grupo> gruposAssociados(Usuario usuario) {
		Query query = manager.createQuery("Select g From Grupo g Where  exists (Select u from g.usuarios u Where u = :usuario)", Grupo.class);
		query.setParameter("usuario", usuario);
		return query.getResultList();
		/*return usuario.getGrupos();*/
	}

	

}
