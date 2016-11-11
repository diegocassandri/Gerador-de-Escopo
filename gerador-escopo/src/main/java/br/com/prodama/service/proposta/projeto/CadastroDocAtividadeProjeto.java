package br.com.prodama.service.proposta.projeto;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.prodama.model.proposta.projeto.DocAtividadeProjeto;
import br.com.prodama.repository.proposta.projeto.DocsAtividadesProjeto;
import br.com.prodama.service.NegocioException;
import br.com.prodama.util.Transactional;

public class CadastroDocAtividadeProjeto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private DocsAtividadesProjeto docsAtividadesProjeto;

	@Transactional
	public void salvar(DocAtividadeProjeto docAtividadeProjeto) throws NegocioException {

		this.docsAtividadesProjeto.adicionar(docAtividadeProjeto);
	}

	@Transactional
	public void excluir(DocAtividadeProjeto docAtividadeProjeto) {
		docsAtividadesProjeto.excluir(docAtividadeProjeto);

	}

}
