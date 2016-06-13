package br.com.prodama.service.cadastro;

import java.io.Serializable;

import javax.inject.Inject;
import br.com.prodama.model.cadastro.Tela;
import br.com.prodama.repository.cadastros.Telas;
import br.com.prodama.service.NegocioException;
import br.com.prodama.util.Transactional;

public class CadastroTela  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Telas telas;
	
	@Transactional
	public void salvar(Tela tela) throws NegocioException {
		if (telas.pesquisaPorNome(tela) && (tela.getCodigo() == null || tela.getCodigo()==0)) {
			throw new NegocioException(
					"Já existe uma tela com este nome: "+ tela.getDescricao());
		}	
			this.telas.adicionar(tela);
	}

	@Transactional
	public void excluir(Tela tela) {
		telas.excluir(tela);
		
	}

}
