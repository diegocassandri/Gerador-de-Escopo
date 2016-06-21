package br.com.prodama.repository.cadastros;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.prodama.controller.UsuarioLogin;
import br.com.prodama.enun.TipoEmpresa;
import br.com.prodama.model.cadastro.Empresa;
import br.com.prodama.model.cadastro.Filial;

public class Filiais implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	@Inject
	private UsuarioLogin usuarioLogin;
	
	public void adicionar(Filial filial){
		manager.merge(filial);
	}
	
	public Filial pesquisaPorId(Long id) {
		return manager.find(Filial.class, id);
	}
	
	public boolean pesquisaPorNome(Filial filial) {
		Query query = manager.createQuery("From filial where RazaoSocial = :filial", Filial.class);
		query.setParameter("filial", filial.getRazaoSocial());
		List<?> resultList = query.getResultList();
		if (resultList.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	public List<Filial> porNomeSemelhante(String nome,Empresa empresa) {
		return manager.createQuery("from Filial where razaoSocial like :nome and empresa = :empresa" , Filial.class)
				.setParameter("nome", "%" + nome + "%").setParameter("empresa", usuarioLogin.getEmpresaSelecionada().getCodigo()).getResultList();
	}
	
	
	public String pesquisaFilial(Filial filial) {
		Query query = manager.createQuery("From Filial where razaoSocial = :empresa or Cnpj = :cnpj or Cpf = :cpf",
				Filial.class);
		query.setParameter("empresa", filial.getRazaoSocial());
		query.setParameter("cnpj", filial.getCnpj());
		query.setParameter("cpf", filial.getCpf());
		List<?> resultList = query.getResultList();
		if (!resultList.isEmpty()) {
			if (filial.getTipoEmpresa().equals(TipoEmpresa.FISICA)) {
				return "Já existe uma filial com os dados informados! \n Empresa: " + filial.getRazaoSocial() + "\n" + "CPF: "
						+ filial.getCpf();
			} else {
				return "Já existe uma filial com os dados informados! \n Empresa: " + filial.getRazaoSocial() + "\n" + "CNPJ: "
						+ filial.getCnpj();
			}			
		} else {
			return "OK";
		}
	}
	
	public void excluir(Filial filial) {
		filial = pesquisaPorId(filial.getCodigo());
		manager.remove(filial);

	}

	public List<Filial> todos(Empresa empresa) {
		return manager.createQuery("from Filial where empresa = :empresa", Filial.class)
				.setParameter("empresa", usuarioLogin.getEmpresaSelecionada()).getResultList();
	}
}
