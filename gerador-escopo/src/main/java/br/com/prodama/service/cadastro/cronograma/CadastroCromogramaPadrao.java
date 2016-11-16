package br.com.prodama.service.cadastro.cronograma;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.prodama.model.cadastro.cronograma.AtividadeHoraPadrao;
import br.com.prodama.model.cadastro.cronograma.CronogramaPadrao;
import br.com.prodama.repository.cadastro.cronograma.AtividadesHoraPadrao;
import br.com.prodama.repository.cadastro.cronograma.CronogramasPadrao;
import br.com.prodama.service.NegocioException;
import br.com.prodama.util.Transactional;

public class CadastroCromogramaPadrao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CronogramasPadrao cronogramasPadrao;
	
	@Inject
	private AtividadesHoraPadrao atividades;;

	@Transactional
	public void salvar(AtividadeHoraPadrao atividade, CronogramaPadrao cronogramaPadrao) throws NegocioException {

		if (cronogramasPadrao.pesquisaPorNome(cronogramaPadrao) && (cronogramaPadrao.getCodigo() == null || cronogramaPadrao.getCodigo()==0)) {
			throw new NegocioException(
					"Já existe um cronograma padrão com este nome: "+cronogramaPadrao.getDescricao());
		} else if((atividades.pesquisaPorNivel(atividade.getNivelAtividade(), cronogramaPadrao)) && (cronogramaPadrao.getCodigo() == null || cronogramaPadrao.getCodigo()==0)) {
			throw new NegocioException(
					"Já existe uma atividade com este nível: "+atividade.getNivelAtividade() + " , neste Cronograma!");
		}
		this.cronogramasPadrao.adicionar(cronogramaPadrao);
	}
	
	@Transactional
	public void salvar(CronogramaPadrao cronogramaPadrao) throws NegocioException {

		if (cronogramasPadrao.pesquisaPorNome(cronogramaPadrao) && (cronogramaPadrao.getCodigo() == null || cronogramaPadrao.getCodigo()==0)) {
			throw new NegocioException(
					"Já existe um cronograma padrão com este nome: "+cronogramaPadrao.getDescricao());
		} 
		this.cronogramasPadrao.adicionar(cronogramaPadrao);
	}

	@Transactional
	public void excluir(CronogramaPadrao cronogramaPadrao) {
		cronogramasPadrao.excluir(cronogramaPadrao);

	}

}
