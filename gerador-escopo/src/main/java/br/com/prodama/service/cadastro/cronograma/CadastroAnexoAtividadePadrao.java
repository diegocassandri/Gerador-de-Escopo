package br.com.prodama.service.cadastro.cronograma;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.prodama.model.cadastro.cronograma.AnexoAtividadeHoraPadrao;
import br.com.prodama.repository.cadastro.cronograma.AnexosAtividadesHoraPadrao;
import br.com.prodama.service.NegocioException;
import br.com.prodama.util.Transactional;

public class CadastroAnexoAtividadePadrao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private AnexosAtividadesHoraPadrao anexosAtividadesHoraPadrao;

	@Transactional
	public void salvar(AnexoAtividadeHoraPadrao anexoAtividadeHoraPadrao) throws NegocioException {

		if (anexosAtividadesHoraPadrao.pesquisaPorNome(anexoAtividadeHoraPadrao) && (anexoAtividadeHoraPadrao.getCodigo() == null || anexoAtividadeHoraPadrao.getCodigo()==0)) {
			throw new NegocioException(
					"Já existe um Anexo nesse documento com este nome: "+anexoAtividadeHoraPadrao.getDescricao());
		}
		this.anexosAtividadesHoraPadrao.adicionar(anexoAtividadeHoraPadrao);
	}

	@Transactional
	public void excluir(AnexoAtividadeHoraPadrao anexoAtividadeHoraPadrao) {
		anexosAtividadesHoraPadrao.excluir(anexoAtividadeHoraPadrao);

	}

}
