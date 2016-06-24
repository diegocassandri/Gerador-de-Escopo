package br.com.prodama.service.cadastro.geral;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.prodama.model.cadastro.geral.Cidade;
import br.com.prodama.repository.cadastro.geral.Cidades;
import br.com.prodama.service.NegocioException;
import br.com.prodama.util.Transactional;

public class CadastroCidade  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Cidades cidades;
	
	@Transactional
	public void salvar(Cidade cidade) throws NegocioException {
		if (cidades.pesquisaPorNome(cidade) && (cidade.getCodigo() == null || cidade.getCodigo()==0)) {
			throw new NegocioException(
					"Já existe um cadastro com este nome de Cidade: "+cidade.getNome());
		}
		
		this.cidades.adicionar(cidade);
	}

	@Transactional
	public void excluir(Cidade cidade) {
		cidades.excluir(cidade);
		
	}

}
