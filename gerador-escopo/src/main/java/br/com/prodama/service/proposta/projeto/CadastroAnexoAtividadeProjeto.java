package br.com.prodama.service.proposta.projeto;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.prodama.model.proposta.projeto.AnexoAtividadeProjeto;
import br.com.prodama.repository.proposta.projeto.AnexosAtividadesProjeto;
import br.com.prodama.util.Transactional;

public class CadastroAnexoAtividadeProjeto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private AnexosAtividadesProjeto anexosAtividadesProjeto;

	@Transactional
	public void salvar(AnexoAtividadeProjeto anexoAtividadeProjeto) {

		this.anexosAtividadesProjeto.adicionar(anexoAtividadeProjeto);
	}

	@Transactional
	public void excluir(AnexoAtividadeProjeto anexoAtividadeProjeto) {
		anexosAtividadesProjeto.excluir(anexoAtividadeProjeto);

	}

}
