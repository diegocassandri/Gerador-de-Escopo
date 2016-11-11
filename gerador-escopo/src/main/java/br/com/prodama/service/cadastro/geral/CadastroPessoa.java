package br.com.prodama.service.cadastro.geral;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.prodama.model.cadastro.geral.Pessoa;
import br.com.prodama.repository.cadastro.geral.Pessoas;
import br.com.prodama.service.NegocioException;
import br.com.prodama.util.Transactional;

public class CadastroPessoa implements Serializable {

	private static final long serialVersionUID = 1L;

	private String validacao;

	@Inject
	private Pessoas pessoas;

	@Transactional
	public void salvar(Pessoa pessoa) throws NegocioException {

		if ((pessoa.getCodigo() == null || pessoa.getCodigo() == 0)) {
			validacao = pessoas.pesquisaPessoa(pessoa);
			if (!validacao.equals("OK")) {
				throw new NegocioException(validacao);
			}

		}
		this.pessoas.adicionar(pessoa);
	}

	@Transactional
	public void excluir(Pessoa pessoa) {
		pessoas.excluir(pessoa);

	}

}
