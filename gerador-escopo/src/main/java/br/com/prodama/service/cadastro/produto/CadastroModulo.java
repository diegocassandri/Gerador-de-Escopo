package br.com.prodama.service.cadastro.produto;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.prodama.model.cadastro.produto.Modulo;
import br.com.prodama.service.NegocioException;
import br.com.prodama.util.Transactional;
import br.com.prodama.repository.cadastro.produto.Modulos;

public class CadastroModulo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Modulos modulos;
	
	@Transactional
	public void salvar(Modulo modulo) throws NegocioException {
		if (modulos.pesquisaPorNome(modulo) && (modulo.getCodigo() == null || modulo.getCodigo()==0)) {
			throw new NegocioException(
					"Já existe um Modulo com essa descrição para esse produto: "+ modulo.getDescricao());
		}
		
		this.modulos.adicionar(modulo);
	}

	@Transactional
	public void excluir(Modulo modulo) {
		modulos.excluir(modulo);
		
	}


}
