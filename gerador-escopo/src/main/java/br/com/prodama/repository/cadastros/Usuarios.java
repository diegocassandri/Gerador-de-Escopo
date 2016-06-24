package br.com.prodama.repository.cadastros;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.prodama.model.cadastro.geral.Empresa;
import br.com.prodama.model.cadastro.geral.Filial;
import br.com.prodama.model.cadastro.geral.Grupo;
import br.com.prodama.model.cadastro.geral.Usuario;


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

	public void alterarMudarSenha(Usuario usuario) {
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

	public boolean autenticaUsuario(String usuario, String senha) {
		System.out.println(usuario + " " + senha);
		Query query = manager.createQuery("From Usuario where usuario = :usuario And senha = :senha and status = 'ATIVO' ", Usuario.class);
		query.setParameter("usuario", usuario);
		query.setParameter("senha", senha);

		List<?> resultList = query.getResultList();
		if (resultList.isEmpty()) {
			return false;
		} else {
			return true;
		}

	}

	public boolean verificaMudarSenha(String usuario) {
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
		return (Usuario) resultList.get(0);

	}

	public void excluir(Usuario usuario) {
		usuario = pesquisaPorId(usuario.getCodigo());
		manager.remove(usuario);

	}

	public List<Usuario> todos() {
		return manager.createQuery("from Usuario", Usuario.class).getResultList();
	}

	public Empresa retPriEmp(Usuario usuario) {
		if (!usuario.getAbrangenciaEmpresas().isEmpty()) {
			return usuario.getAbrangenciaEmpresas().get(0);
		} else {
			return null;
		}
	}
	
	public Filial retPriFil(Usuario usuario) {
		if (!usuario.getAbrangenciaFiliais().isEmpty()) {
			return usuario.getAbrangenciaFiliais().get(0);
		} else {
			return null;
		}
	}

	/*public Filial retPriFilEmp(Usuario usuario) {
		Filial filialEncontrada = null;
		if (!usuario.getAbrangenciaFiliais().isEmpty()) {
			for (Filial filial : usuario.getAbrangenciaFiliais()) {
				System.out.println("!!!!!!!!!!!!!!!!!!!!!!!! " + filial.getRazaoSocial());
				if (filial.getEmpresa() == usuario.getEmpresaSelecionada()) {
					filialEncontrada = filial;
					break;
				}
			}
			return filialEncontrada;
		} else {
			return null;
		}
	}*/

	@SuppressWarnings("unchecked")
	public List<Grupo> gruposNaoAssociados(Usuario usuario) {

		Query query = manager.createQuery(
				"Select g From Grupo g Where not exists (Select u from g.usuarios u Where u = :usuario)", Grupo.class);
		query.setParameter("usuario", usuario);
		return query.getResultList();

	}

	@SuppressWarnings("unchecked")
	public List<Grupo> gruposAssociados(Usuario usuario) {
		Query query = manager.createQuery(
				"Select g From Grupo g Where  exists (Select u from g.usuarios u Where u = :usuario)", Grupo.class);
		query.setParameter("usuario", usuario);
		return query.getResultList();
	}

}
