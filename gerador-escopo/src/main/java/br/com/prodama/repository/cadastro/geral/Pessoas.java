package br.com.prodama.repository.cadastro.geral;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.prodama.enun.TipoEmpresa;
import br.com.prodama.model.cadastro.geral.Pessoa;

public class Pessoas implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public void adicionar(Pessoa pessoa) {
		manager.merge(pessoa);
	}
	
	public Pessoa pesquisaPorId(Long id) {
		return manager.find(Pessoa.class, id);
	}
	
	public String pesquisaPessoa(Pessoa pessoa) {
		Query query = manager.createQuery("From Pessoa where razaoSocial = :pessoa or Cnpj = :cnpj or Cpf = :cpf",
				Pessoa.class);
		query.setParameter("pessoa", pessoa.getNomeRazaoSocial());
		query.setParameter("cnpj", pessoa.getCnpj());
		query.setParameter("cpf", pessoa.getCpf());
		List<?> resultList = query.getResultList();
		if (!resultList.isEmpty()) {
			if (pessoa.getTipoPessoa().equals(TipoEmpresa.FISICA)) {
				return "Já existe uma pessoa com os dados informados! \n pessoa: " + pessoa.getNomeRazaoSocial() + "\n"
						+ "CPF: " + pessoa.getCpf();
			} else {
				return "Já existe uma empresa com os dados informados! \n Empresa: " + pessoa.getNomeRazaoSocial() + "\n"
						+ "CNPJ: " + pessoa.getCnpj();
			}
		} else {
			return "OK";
		}
	}
	
	public void excluir(Pessoa pessoa) {
		pessoa = pesquisaPorId(pessoa.getCodigo());
		manager.remove(pessoa);

	}

	public List<Pessoa> todos() {
		return manager.createQuery("from Pessoa", Pessoa.class).getResultList();
	}
	

}
