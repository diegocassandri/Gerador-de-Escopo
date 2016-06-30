package br.com.prodama.service.cadastro.produto;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.prodama.model.cadastro.produto.ProcessoGestao;
import br.com.prodama.service.NegocioException;
import br.com.prodama.util.Transactional;
import br.com.prodama.repository.cadastro.produto.Processos;

public class CadastroProcessoGestao implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Processos processos;
	
	@Transactional
	public void salvar(ProcessoGestao processoGestao) throws NegocioException {
		if (processos.pesquisaPorNome(processoGestao) && (processoGestao.getCodigo() == null || processoGestao.getCodigo()==0)) {
			throw new NegocioException(
					"Já existe um Processo com essa descrição para essa Gestão: "+ processoGestao.getDescricao());
		}
		
		this.processos.adicionar(processoGestao);
	}

	@Transactional
	public void excluir(ProcessoGestao processoGestao) {
		processos.excluir(processoGestao);
		
	}


}
