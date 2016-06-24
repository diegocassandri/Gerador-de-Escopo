package br.com.prodama.repository.cadastro.geral;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.prodama.controller.UsuarioLogin;
import br.com.prodama.enun.TipoEmpresa;
import br.com.prodama.model.cadastro.geral.EmpresaCliente;
import br.com.prodama.model.cadastro.geral.FilialCliente;


public class FiliaisCliente implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	@Inject
	private UsuarioLogin usuarioLogin;
	
	public void adicionar(FilialCliente filialCliente){
		manager.merge(filialCliente);
	}
	
	public FilialCliente pesquisaPorId(Long id) {
		return manager.find(FilialCliente.class, id);
	}
	
	public boolean pesquisaPorNome(FilialCliente filialCliente) {
		Query query = manager.createQuery("From filialCliente where RazaoSocial = :filial", FilialCliente.class);
		query.setParameter("filial", filialCliente.getRazaoSocial());
		List<?> resultList = query.getResultList();
		if (resultList.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	public List<FilialCliente> porNomeSemelhante(String nome,EmpresaCliente filialCliente) {
		return manager.createQuery("from FilialCliente where razaoSocial like :nome and empresa = :empresa" , FilialCliente.class)
				.setParameter("nome", "%" + nome + "%").setParameter("empresa", usuarioLogin.getEmpresaSelecionada().getCodigo()).getResultList();
	}
	
	
	public String pesquisaFilial(FilialCliente filialCliente) {
		Query query = manager.createQuery("From FilialCliente where razaoSocial = :empresa ",
				FilialCliente.class);
		query.setParameter("empresa", filialCliente.getRazaoSocial());
		List<?> resultList = query.getResultList();
		if (!resultList.isEmpty()) {
			if (filialCliente.getTipoEmpresa().equals(TipoEmpresa.FISICA)) {
				return "Já existe uma filial com os dados informados! \n Empresa: " + filialCliente.getRazaoSocial() + "\n" + "CPF: "
						+ filialCliente.getCpf();
			} else {
				return "Já existe uma filial com os dados informados! \n Empresa: " + filialCliente.getRazaoSocial() + "\n" + "CNPJ: "
						+ filialCliente.getCnpj();
			}			
		} else {
			return "OK";
		}
	}
	
	public void excluir(FilialCliente filialCliente) {
		filialCliente = pesquisaPorId(filialCliente.getCodigo());
		manager.remove(filialCliente);

	}

	public List<FilialCliente> todos() {
		return manager.createQuery("from FilialCliente ", FilialCliente.class).getResultList();
	}



}
