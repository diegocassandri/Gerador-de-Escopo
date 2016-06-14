package br.com.prodama.repository.cadastros;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.prodama.model.cadastro.TipoDeHora;

public class TipoDeHoras implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public void adicionar(TipoDeHora tipoDeHora){
		manager.merge(tipoDeHora);
	}
	
	public TipoDeHora pesquisaPorId(Long id) {
		return manager.find(TipoDeHora.class, id);
	}
	
	public boolean pesquisaPorNome(TipoDeHora tipoDeHora) {
		Query query = manager.createQuery("From TipoDeHora where descricao = :tipoDeHora", TipoDeHora.class);
		query.setParameter("tipoDeHora", tipoDeHora.getDescricao());
		List<?> resultList = query.getResultList();
		if (resultList.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	public void excluir(TipoDeHora tipoDeHora) {
		tipoDeHora = pesquisaPorId(tipoDeHora.getCodigo());
		manager.remove(tipoDeHora);
	}

	public List<TipoDeHora> todos() {
		return manager.createQuery("from TipoDeHora", TipoDeHora.class).getResultList();
	}
}
