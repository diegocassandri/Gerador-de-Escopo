package br.com.prodama.service.cadastro.cronograma;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.prodama.model.cadastro.cronograma.AtividadeHoraPadrao;
import br.com.prodama.repository.cadastro.cronograma.AtividadesHoraPadrao;
import br.com.prodama.service.NegocioException;
import br.com.prodama.util.Transactional;

public class CadastroAtividadePadrao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private AtividadesHoraPadrao atividadesHoraPadrao;

	@Transactional
	public void salvar(AtividadeHoraPadrao atividadeHoraPadrao) throws NegocioException {

		if (atividadesHoraPadrao.pesquisaPorNome(atividadeHoraPadrao) && (atividadeHoraPadrao.getCodigo() == null || atividadeHoraPadrao.getCodigo()==0)) {
			throw new NegocioException(
					"Já existe uma atividade nesse cronograma cronograma padrão com este nome: "+atividadeHoraPadrao.getDescricao());
		}
		this.atividadesHoraPadrao.adicionar(atividadeHoraPadrao);
	}

	@Transactional
	public void excluir(AtividadeHoraPadrao atividadeHoraPadrao) {
		atividadesHoraPadrao.excluir(atividadeHoraPadrao);

	}

}
