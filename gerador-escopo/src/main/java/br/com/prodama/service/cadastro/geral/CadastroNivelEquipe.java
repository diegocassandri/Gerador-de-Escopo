package br.com.prodama.service.cadastro.geral;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.prodama.model.cadastro.geral.NivelEquipe;
import br.com.prodama.repository.cadastro.geral.NiveisEquipe;
import br.com.prodama.service.NegocioException;
import br.com.prodama.util.Transactional;

public class CadastroNivelEquipe implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private NiveisEquipe niveisEquipe;
	
	@Transactional
	public void salvar(NivelEquipe nivelEquipe) throws NegocioException {
		if (niveisEquipe.pesquisaPorNome(nivelEquipe) && (nivelEquipe.getCodigo() == null || nivelEquipe.getCodigo()==0)) {
			throw new NegocioException(
					"Já existe um cadastro com este nome de Nível para essa Equipe: "+nivelEquipe.getDescricao());
		}
		
		this.niveisEquipe.adicionar(nivelEquipe);
	}

	@Transactional
	public void excluir(NivelEquipe nivelEquipe) {
		niveisEquipe.excluir(nivelEquipe);
		
	}

}
