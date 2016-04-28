package br.com.prodama.service.cadastro;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.prodama.model.cadastro.Empresa;
import br.com.prodama.repository.cadastros.Empresas;
import br.com.prodama.service.NegocioException;
import br.com.prodama.util.Transactional;

public class CadastroEmpresa  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Empresas empresas;
	
	@Transactional
	public void salvar(Empresa empresa) throws NegocioException {
		if (empresas.pesquisaPorNome(empresa) && (empresa.getCodigo() == null || empresa.getCodigo()==0)) {
			throw new NegocioException(
					"Já existe um cadastro com esta razão social: "+empresa.getRazaoSocial());
		}
		
		this.empresas.adicionar(empresa);
	}

	@Transactional
	public void excluir(Empresa empresa) {
		empresas.excluir(empresa);
		
	}

}
