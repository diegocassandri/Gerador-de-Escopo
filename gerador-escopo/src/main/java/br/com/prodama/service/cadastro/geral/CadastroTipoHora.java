package br.com.prodama.service.cadastro.geral;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.prodama.model.cadastro.geral.TipoHora;
import br.com.prodama.repository.cadastro.geral.TipoHoras;
import br.com.prodama.service.NegocioException;
import br.com.prodama.util.Transactional;

public class CadastroTipoHora implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private TipoHoras tipoDeHoras;
	
	@Transactional
	public void salvar(TipoHora tipoDeHora) throws NegocioException {
		if (tipoDeHoras.pesquisaPorNome(tipoDeHora) && (tipoDeHora.getCodigo() == null || tipoDeHora.getCodigo()==0)) {
			throw new NegocioException(
					"Já existe um Tipo de Hora com esta descrição: "+tipoDeHora.getDescricao());
		}
		
		this.tipoDeHoras.adicionar(tipoDeHora);
	}

	@Transactional
	public void excluir(TipoHora tipoDeHora) {
		tipoDeHoras.excluir(tipoDeHora);
		
	}
}
