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

	public void adicionar(Empresa empresa) {
		manager.merge(empresa);
	}

	public Empresa pesquisaPorId(Long id) {
		return manager.find(Empresa.class, id);
	}

	public String pesquisaEmpresa(Empresa empresa) {
		Query query = manager.createQuery("From Empresa where razaoSocial = :empresa or Cnpj = :cnpj or Cpf = :cpf",
				Empresa.class);
		query.setParameter("empresa", empresa.getRazaoSocial());
		query.setParameter("cnpj", empresa.getCnpj());
		query.setParameter("cpf", empresa.getCpf());
		List<?> resultList = query.getResultList();
		if (!resultList.isEmpty()) {
			if (empresa.getTipoEmpresa().equals(""/*TipoEmpresa.FISICA*/)) {
				return "Já existe uma empresa com os dados informados! \n Empresa: " + empresa.getRazaoSocial() + "\n" + "CPF: "
						+ empresa.getCpf();
			} else {
				return "Já existe uma empresa com os dados informados! \n Empresa: " + empresa.getRazaoSocial() + "\n" + "CNPJ: "
						+ empresa.getCnpj();
			}			
		} else {
			return "OK";
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
