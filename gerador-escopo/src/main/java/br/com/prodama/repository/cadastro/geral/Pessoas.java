package br.com.prodama.repository.cadastro.geral;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.prodama.enun.Status;
import br.com.prodama.enun.TipoEmpresa;
import br.com.prodama.enun.TipoPessoa;
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
		Query query = manager.createQuery("From Pessoa where nomeRazaoSocial = :pessoa or Cnpj = :cnpj or Cpf = :cpf",
				Pessoa.class);
		query.setParameter("pessoa", pessoa.getNomeRazaoSocial());
		query.setParameter("cnpj", pessoa.getCnpj());
		query.setParameter("cpf", pessoa.getCpf());
		List<?> resultList = query.getResultList();
		if (!resultList.isEmpty()) {
			if (pessoa.getTipoPessoa().equals(TipoEmpresa.FISICA)) {
				return "Já existe uma pessoa com os dados informados! \n Pessoa: " + pessoa.getNomeRazaoSocial() + "\n"
						+ "CPF: " + pessoa.getCpf();
			} else {
				return "Já existe uma pessoa com os dados informados! \n Pessoa: " + pessoa.getNomeRazaoSocial()
						+ "\n" + "CNPJ: " + pessoa.getCnpj();
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

	@SuppressWarnings("unchecked")
	public List<Pessoa> pesquisaFiltros(String razao, String fantasia, TipoPessoa tipo, Status status) {
		Query query;
		if (tipo == null) {
			query = manager.createQuery("From Pessoa where nomeRazaoSocial like :razao and apelidoFantasia like :fantasia and status = :status ",Pessoa.class);
			
		} else {
			 query = manager.createQuery("From Pessoa where nomeRazaoSocial like :razao and apelidoFantasia like :fantasia and status = :status and tipoPessoa in (:tipo)",Pessoa.class);
			query.setParameter("tipo", tipo);
		}
		
		query.setParameter("razao", "%" + razao + "%");
		query.setParameter("fantasia", "%" + fantasia + "%");
		query.setParameter("status", status);

		return query.getResultList();

	}

}
