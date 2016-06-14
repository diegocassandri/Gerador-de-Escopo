package br.com.prodama.service.cadastro;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.prodama.model.cadastro.TipoDeHora;
import br.com.prodama.repository.cadastros.TipoDeHoras;
import br.com.prodama.service.NegocioException;
import br.com.prodama.util.Transactional;

public class CadastroTipoDeHora implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private TipoDeHoras tipoDeHoras;
	
	@Transactional
	public void salvar(TipoDeHora tipoDeHora) throws NegocioException {
		if (tipoDeHoras.pesquisaPorNome(tipoDeHora) && (tipoDeHora.getCodigo() == null || tipoDeHora.getCodigo()==0)) {
			throw new NegocioException(
					"Já existe um Tipo de Hora com esta descrição: "+tipoDeHora.getDescricao());
		}
		
		this.tipoDeHoras.adicionar(tipoDeHora);
	}

	@Transactional
	public void excluir(TipoDeHora tipoDeHora) {
		tipoDeHoras.excluir(tipoDeHora);
		
	}
}
