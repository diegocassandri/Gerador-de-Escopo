package br.com.prodama.service.cadastro.geral;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.prodama.model.cadastro.geral.Filial;
import br.com.prodama.repository.cadastro.geral.Filiais;
import br.com.prodama.service.NegocioException;
import br.com.prodama.util.Transactional;

public class CadastroFilial implements Serializable {

	private static final long serialVersionUID = 1L;

	private String validacao;

	@Inject
	private Filiais filiais;

	@Transactional
	public void salvar(Filial filial) throws NegocioException {

		if ((filial.getCodigo() == null || filial.getCodigo() == 0)) {
			validacao = filiais.pesquisaFilial(filial);
			if (!validacao.equals("OK")) {
				throw new NegocioException(validacao);
			}

		}
		this.filiais.adicionar(filial);
	}

	@Transactional
	public void excluir(Filial filial) {
		filiais.excluir(filial);

	}

}

