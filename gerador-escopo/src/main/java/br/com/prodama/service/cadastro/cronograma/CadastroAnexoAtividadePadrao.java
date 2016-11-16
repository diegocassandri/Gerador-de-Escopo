package br.com.prodama.service.cadastro.cronograma;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.prodama.model.cadastro.cronograma.AnexoAtividadeHoraPadrao;
import br.com.prodama.repository.cadastro.cronograma.AnexosAtividadesHoraPadrao;
import br.com.prodama.util.Transactional;

public class CadastroAnexoAtividadePadrao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private AnexosAtividadesHoraPadrao anexosAtividadesHoraPadrao;

	@Transactional
	public void salvar(AnexoAtividadeHoraPadrao anexoAtividadeHoraPadrao) {

		this.anexosAtividadesHoraPadrao.adicionar(anexoAtividadeHoraPadrao);
	}

	@Transactional
	public void excluir(AnexoAtividadeHoraPadrao anexoAtividadeHoraPadrao) {
		anexosAtividadesHoraPadrao.excluir(anexoAtividadeHoraPadrao);

	}

}
