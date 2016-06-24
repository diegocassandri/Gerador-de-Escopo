package br.com.prodama.service.cadastro;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.prodama.model.cadastro.FaixaColaborador;
import br.com.prodama.repository.cadastros.FaixaColaboradores;
import br.com.prodama.service.NegocioException;
import br.com.prodama.util.Transactional;

public class CadastroFaixaColaborador implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private FaixaColaboradores faixaColaboradores;
	
	@Transactional
	public void salvar(FaixaColaborador faixaColaborador) throws NegocioException {
		if (faixaColaboradores.pesquisaPorNome(faixaColaborador) && (faixaColaborador.getCodigo() == null || faixaColaborador.getCodigo()==0)) {
			throw new NegocioException(
					"Já existe uma faixa de colaboradores com este Indice: "+faixaColaborador.getIndice());
		}
		
		this.faixaColaboradores.adicionar(faixaColaborador);
	}

	@Transactional
	public void excluir(FaixaColaborador faixaColaborador) {
		faixaColaboradores.excluir(faixaColaborador);
		
	}

}
