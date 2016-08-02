package br.com.prodama.service.cadastro.cronograma;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.prodama.model.cadastro.cronograma.DocAtividadeHoraPadrao;
import br.com.prodama.repository.cadastro.cronograma.DocsAtividadesHoraPadrao;
import br.com.prodama.service.NegocioException;
import br.com.prodama.util.Transactional;

public class CadastroDocAtividadePadrao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private DocsAtividadesHoraPadrao docsAtividadesHoraPadrao;

	@Transactional
	public void salvar(DocAtividadeHoraPadrao docAtividadeHoraPadrao) throws NegocioException {

		this.docsAtividadesHoraPadrao.adicionar(docAtividadeHoraPadrao);
	}

	@Transactional
	public void excluir(DocAtividadeHoraPadrao docAtividadeHoraPadrao) {
		docsAtividadesHoraPadrao.excluir(docAtividadeHoraPadrao);

	}

}
