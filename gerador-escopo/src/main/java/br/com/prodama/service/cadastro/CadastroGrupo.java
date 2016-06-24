package br.com.prodama.service.cadastro;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.prodama.model.cadastro.geral.Grupo;
import br.com.prodama.repository.cadastros.Grupos;
import br.com.prodama.service.NegocioException;
import br.com.prodama.util.Transactional;

public class CadastroGrupo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Grupos grupos;
	
	@Transactional
	public void salvar(Grupo grupo) throws Exception {
		if (grupos.pesquisaPorNome(grupo) && (grupo.getCodigo() == null || grupo.getCodigo()==0)) {
			throw new NegocioException(
					"Já existe um cadastro com este nome de usuário: " + grupo.getNome());
		}
		
		this.grupos.adicionar(grupo);
	}

	@Transactional
	public void excluir(Grupo grupo) {
		grupos.excluir(grupo);
		
	}
	
}
