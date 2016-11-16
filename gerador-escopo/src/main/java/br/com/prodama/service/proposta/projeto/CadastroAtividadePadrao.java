package br.com.prodama.service.proposta.projeto;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.prodama.model.proposta.projeto.AtividadeProjeto;
import br.com.prodama.repository.proposta.projeto.AtividadesProjeto;
import br.com.prodama.service.NegocioException;
import br.com.prodama.util.Transactional;

public class CadastroAtividadePadrao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private AtividadesProjeto atividadesProjeto;

	@Transactional
	public void salvar(AtividadeProjeto atividadeProjeto) throws NegocioException {

		if (atividadesProjeto.pesquisaPorNome(atividadeProjeto) && (atividadeProjeto.getCodigo() == null || atividadeProjeto.getCodigo()==0)) {
			throw new NegocioException(
					"Já existe uma atividade nesse projeto com este nome: "+atividadeProjeto.getDescricao());
		}
		this.atividadesProjeto.adicionar(atividadeProjeto);
	}

	@Transactional
	public void excluir(AtividadeProjeto atividadeProjeto) {
		atividadesProjeto.excluir(atividadeProjeto);

	}

}
