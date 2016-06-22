package br.com.prodama.service.cadastro;

import java.io.Serializable;

import javax.inject.Inject;
import br.com.prodama.model.cadastro.FilialCliente;
import br.com.prodama.repository.cadastros.FiliaisCliente;
import br.com.prodama.service.NegocioException;
import br.com.prodama.util.Transactional;

public class CadastroFilialCliente implements Serializable {

	private static final long serialVersionUID = 1L;

	private String validacao;

	@Inject
	private FiliaisCliente filiaisCliente;

	@Transactional
	public void salvar(FilialCliente filialCliente) throws NegocioException {

		if ((filialCliente.getCodigo() == null || filialCliente.getCodigo() == 0)) {
			validacao = filiaisCliente.pesquisaFilial(filialCliente);
			if (!validacao.equals("OK")) {
				throw new NegocioException(validacao);
			}

		}
		this.filiaisCliente.adicionar(filialCliente);
	}

	@Transactional
	public void excluir(FilialCliente filialCliente) {
		filiaisCliente.excluir(filialCliente);

	}

}

