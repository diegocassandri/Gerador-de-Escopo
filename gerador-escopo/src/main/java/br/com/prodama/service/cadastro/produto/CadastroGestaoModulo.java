package br.com.prodama.service.cadastro.produto;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.prodama.model.cadastro.produto.GestaoModulo;
import br.com.prodama.service.NegocioException;
import br.com.prodama.util.Transactional;
import br.com.prodama.repository.cadastro.produto.GestaoModulos;

public class CadastroGestaoModulo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private GestaoModulos gestaoModulos;
	
	@Transactional
	public void salvar(GestaoModulo gestaoModulo) throws NegocioException {
		if (gestaoModulos.pesquisaPorNome(gestaoModulo) && (gestaoModulo.getCodigo() == null || gestaoModulo.getCodigo()==0)) {
			throw new NegocioException(
					"Já existe uma Gestão de Módulo com essa descrição para esse Módulo: "+ gestaoModulo.getDescricao());
		}
		
		this.gestaoModulos.adicionar(gestaoModulo);
	}

	@Transactional
	public void excluir(GestaoModulo gestaoModulo) {
		gestaoModulos.excluir(gestaoModulo);
		
	}


}
