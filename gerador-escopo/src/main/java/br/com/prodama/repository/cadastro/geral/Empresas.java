package br.com.prodama.repository.cadastro.geral;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.prodama.controller.UsuarioLogin;
import br.com.prodama.enun.TipoEmpresa;
import br.com.prodama.model.cadastro.geral.Empresa;
import br.com.prodama.model.cadastro.geral.Usuario;

public class Empresas implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	@Inject
	private UsuarioLogin usuarioLogin;
	
	public void adicionar(Empresa empresa) {
		manager.merge(empresa);
	}

	public Empresa pesquisaPorId(Long id) {
		return manager.find(Empresa.class, id);
	}

	public Empresa atualizaEmpresa(Empresa empresa) {
		manager.refresh(empresa);
		return empresa;
	}

	public String pesquisaEmpresa(Empresa empresa) {
		Query query = manager.createQuery("From Empresa where razaoSocial = :empresa or Cnpj = :cnpj or Cpf = :cpf",
				Empresa.class);
		query.setParameter("empresa", empresa.getRazaoSocial());
		query.setParameter("cnpj", empresa.getCnpj());
		query.setParameter("cpf", empresa.getCpf());
		List<?> resultList = query.getResultList();
		if (!resultList.isEmpty()) {
			if (empresa.getTipoEmpresa().equals(TipoEmpresa.FISICA)) {
				return "Já existe uma empresa com os dados informados! \n Empresa: " + empresa.getRazaoSocial() + "\n"
						+ "CPF: " + empresa.getCpf();
			} else {
				return "Já existe uma empresa com os dados informados! \n Empresa: " + empresa.getRazaoSocial() + "\n"
						+ "CNPJ: " + empresa.getCnpj();
			}
		} else {
			return "OK";
		}
	}

	@SuppressWarnings("unchecked")
	public List<Empresa> porNomeSemelhante(String nome) {
		/*return manager.createQuery("from Empresa where razaoSocial like :nome", Empresa.class)
				.setParameter("nome", "%" + nome + "%").getResultList();*/
		Query query = manager.createQuery("Select e From Empresa e Where exists (Select u from e.abrangenciaUsuarios u Where u = :usuario) and razaoSocial like :nome", Empresa.class);
		query.setParameter("usuario", usuarioLogin.getUsuarioLogin());
		query.setParameter("nome", "%" + nome + "%").getResultList();
		return query.getResultList();
	}


	@SuppressWarnings("unchecked")
	public  List<Empresa> empresasNaoAssociadas(Usuario usuario) {

		Query query = manager.createQuery("Select e From Empresa e Where not exists (Select u from e.abrangenciaUsuarios u Where u = :usuario)", Empresa.class);
		query.setParameter("usuario", usuario);
		return query.getResultList();

	}

	
	public void excluir(Empresa empresa) {
		empresa = pesquisaPorId(empresa.getCodigo());
		manager.remove(empresa);

	}

	public List<Empresa> todos() {
		return manager.createQuery("from Empresa", Empresa.class)
				                 .getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Empresa> empresasAssociadas(Usuario usuario) {
		Query query = manager.createQuery("Select e From Empresa e Where exists (Select u from e.abrangenciaUsuarios u Where u = :usuario)", Empresa.class);
		query.setParameter("usuario", usuario);
		return query.getResultList();
	}

}
