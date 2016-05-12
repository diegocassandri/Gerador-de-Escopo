package br.com.prodama.service.cadastro;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.prodama.model.cadastro.Estado;
import br.com.prodama.repository.cadastros.Estados;
import br.com.prodama.service.NegocioException;
import br.com.prodama.util.Transactional;

public class CadastroEstado  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Estados estados;
	
	@Transactional
	public void salvar(Estado estado) throws NegocioException {
		if (estados.pesquisaPorNome(estado) && (estado.getCodigo() == null || estado.getCodigo()==0)) {
			throw new NegocioException(
					"Já existe um cadastro com este nome de usuário: "+estado.getNome());
		}
		
		this.estados.adicionar(estado);
	}

	@Transactional
	public void excluir(Estado estado) {
		estados.excluir(estado);
		
	}

}
