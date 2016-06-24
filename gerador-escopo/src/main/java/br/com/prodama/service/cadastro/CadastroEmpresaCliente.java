package br.com.prodama.service.cadastro;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.prodama.model.cadastro.geral.EmpresaCliente;
import br.com.prodama.repository.cadastros.EmpresasCliente;
import br.com.prodama.service.NegocioException;
import br.com.prodama.util.Transactional;

public class CadastroEmpresaCliente implements Serializable {

	private static final long serialVersionUID = 1L;

	private String validacao;

	@Inject
	private EmpresasCliente empresasCliente;

	@Transactional
	public void salvar(EmpresaCliente empresaCliente) throws NegocioException {

		if ((empresaCliente.getCodigo() == null || empresaCliente.getCodigo() == 0)) {
			validacao = empresasCliente.pesquisaEmpresa(empresaCliente);
			if (!validacao.equals("OK")) {
				throw new NegocioException(validacao);
			}

		}
		this.empresasCliente.adicionar(empresaCliente);
	}

	@Transactional
	public void excluir(EmpresaCliente empresaCliente) {
		empresasCliente.excluir(empresaCliente);

	}

}
