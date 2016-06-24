package br.com.prodama.repository.cadastro.geral;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.prodama.enun.TipoEmpresa;
import br.com.prodama.model.cadastro.geral.EmpresaCliente;


public class EmpresasCliente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public void adicionar(EmpresaCliente empresaCliente) {
		manager.merge(empresaCliente);
	}

	public EmpresaCliente pesquisaPorId(Long id) {
		return manager.find(EmpresaCliente.class, id);
	}

	public EmpresaCliente atualizaEmpresa(EmpresaCliente empresaCliente) {
		manager.refresh(empresaCliente);
		return empresaCliente;
	}

	public String pesquisaEmpresa(EmpresaCliente empresaCliente) {
		Query query = manager.createQuery("From EmpresaCliente where razaoSocial = :empresa or Cnpj = :cnpj or Cpf = :cpf",
				EmpresaCliente.class);
		query.setParameter("empresa", empresaCliente.getRazaoSocial());
		query.setParameter("cnpj", empresaCliente.getCnpj());
		query.setParameter("cpf", empresaCliente.getCpf());
		List<?> resultList = query.getResultList();
		if (!resultList.isEmpty()) {
			if (empresaCliente.getTipoEmpresa().equals(TipoEmpresa.FISICA)) {
				return "Já existe uma empresa com os dados informados! \n Empresa: " + empresaCliente.getRazaoSocial() + "\n"
						+ "CPF: " + empresaCliente.getCpf();
			} else {
				return "Já existe uma empresa com os dados informados! \n Empresa: " + empresaCliente.getRazaoSocial() + "\n"
						+ "CNPJ: " + empresaCliente.getCnpj();
			}
		} else {
			return "OK";
		}
	}

	public List<EmpresaCliente> porNomeSemelhante(String nome) {
		return manager.createQuery("from EmpresaCliente where razaoSocial like :nome", EmpresaCliente.class)
				.setParameter("nome", "%" + nome + "%").getResultList();
	}


	
	public void excluir(EmpresaCliente empresaCliente) {
		empresaCliente = pesquisaPorId(empresaCliente.getCodigo());
		manager.remove(empresaCliente);

	}

	public List<EmpresaCliente> todos() {
		return manager.createQuery("from EmpresaCliente", EmpresaCliente.class)
				                 .getResultList();
	}


}
