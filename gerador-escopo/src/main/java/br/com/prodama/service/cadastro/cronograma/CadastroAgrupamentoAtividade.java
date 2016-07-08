package br.com.prodama.service.cadastro.cronograma;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.prodama.model.cadastro.cronograma.AgrupamentoAtividade;
import br.com.prodama.repository.cadastro.cronograma.AgrupamentosAtividades;
import br.com.prodama.service.NegocioException;
import br.com.prodama.util.Transactional;

public class CadastroAgrupamentoAtividade implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private AgrupamentosAtividades agrupamentosAtividades;

	@Transactional
	public void salvar(AgrupamentoAtividade agrupamentoAtividade) throws NegocioException {

		if (agrupamentosAtividades.pesquisaPorNome(agrupamentoAtividade) && (agrupamentoAtividade.getCodigo() == null || agrupamentoAtividade.getCodigo()==0)) {
			throw new NegocioException(
					"Já existe um Agrupament nome: "+agrupamentoAtividade.getDescricao());
		}
		this.agrupamentosAtividades.adicionar(agrupamentoAtividade);
	}

	@Transactional
	public void excluir(AgrupamentoAtividade agrupamentoAtividade) {
		agrupamentosAtividades.excluir(agrupamentoAtividade);

	}

}
