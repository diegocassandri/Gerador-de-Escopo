package br.com.prodama.service.cadastro.geral;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.prodama.model.cadastro.geral.Empresa;
import br.com.prodama.repository.cadastro.geral.Empresas;
import br.com.prodama.service.NegocioException;
import br.com.prodama.util.Transactional;

public class CadastroEmpresa implements Serializable {

	private static final long serialVersionUID = 1L;

	private String validacao;

	@Inject
	private Empresas empresas;

	@Transactional
	public void salvar(Empresa empresa) throws NegocioException {

		if ((empresa.getCodigo() == null || empresa.getCodigo() == 0)) {
			validacao = empresas.pesquisaEmpresa(empresa);
			if (!validacao.equals("OK")) {
				throw new NegocioException(validacao);
			}

		}
		this.empresas.adicionar(empresa);
	}

	@Transactional
	public void excluir(Empresa empresa) {
		empresas.excluir(empresa);

	}

}
