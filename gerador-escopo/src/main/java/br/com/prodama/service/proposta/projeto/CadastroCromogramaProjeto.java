package br.com.prodama.service.proposta.projeto;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.prodama.model.proposta.projeto.AtividadeProjeto;
import br.com.prodama.model.proposta.projeto.CronogramaProjeto;
import br.com.prodama.repository.proposta.projeto.AtividadesProjeto;
import br.com.prodama.repository.proposta.projeto.CronogramasProjetos;
import br.com.prodama.service.NegocioException;
import br.com.prodama.util.Transactional;

public class CadastroCromogramaProjeto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CronogramasProjetos cronogramasProjeto;
	
	@Inject
	private AtividadesProjeto atividades;

	@Transactional
	public void salvar(AtividadeProjeto atividade, CronogramaProjeto cronogramaProjeto) throws NegocioException {

		if (cronogramasProjeto.pesquisaPorNome(cronogramaProjeto) && (cronogramaProjeto.getCodigo() == null || cronogramaProjeto.getCodigo()==0)) {
			throw new NegocioException(
					"Já existe um cronograma com este nome: "+cronogramaProjeto.getDescricao());
		} else if((atividades.pesquisaPorNivel(atividade.getNivelAtividade(), cronogramaProjeto)) && (cronogramaProjeto.getCodigo() == null || cronogramaProjeto.getCodigo()==0)) {
			throw new NegocioException(
					"Já existe uma atividade com este nível: "+atividade.getNivelAtividade() + " , neste Projeto!");
		}
		this.cronogramasProjeto.adicionar(cronogramaProjeto);
	}
	
	@Transactional
	public void salvar(CronogramaProjeto cronogramaProjeto) throws NegocioException {

		if (cronogramasProjeto.pesquisaPorNome(cronogramaProjeto) && (cronogramaProjeto.getCodigo() == null || cronogramaProjeto.getCodigo()==0)) {
			throw new NegocioException(
					"Já existe um projeto com este nome: "+cronogramaProjeto.getDescricao());
		} 
		this.cronogramasProjeto.adicionar(cronogramaProjeto);
	}

	@Transactional
	public void excluir(CronogramaProjeto cronogramaProjeto) {
		cronogramasProjeto.excluir(cronogramaProjeto);

	}

}
