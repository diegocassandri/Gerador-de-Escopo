package br.com.prodama.repository.cadastros;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.prodama.model.cadastro.geral.TipoHora;

public class TipoHoras implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public void adicionar(TipoHora tipoDeHora){
		manager.merge(tipoDeHora);
	}
	
	public TipoHora pesquisaPorId(Long id) {
		return manager.find(TipoHora.class, id);
	}
	
	public boolean pesquisaPorNome(TipoHora tipoDeHora) {
		Query query = manager.createQuery("From TipoHora where descricao = :tipoHora", TipoHora.class);
		query.setParameter("tipoHora", tipoDeHora.getDescricao());
		List<?> resultList = query.getResultList();
		if (resultList.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	public void excluir(TipoHora tipoDeHora) {
		tipoDeHora = pesquisaPorId(tipoDeHora.getCodigo());
		manager.remove(tipoDeHora);
	}

	public List<TipoHora> todos() {
		return manager.createQuery("from TipoHora", TipoHora.class).getResultList();
	}
}
