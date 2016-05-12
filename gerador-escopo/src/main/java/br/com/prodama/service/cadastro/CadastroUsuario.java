package br.com.prodama.service.cadastro;

import java.io.Serializable;
import javax.inject.Inject;

import br.com.prodama.model.cadastro.Usuario;
import br.com.prodama.repository.cadastros.Usuarios;
import br.com.prodama.service.NegocioException;
import br.com.prodama.util.Transactional;


public class CadastroUsuario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Usuarios usuarios;
	
	@Transactional
	public void salvar(Usuario usuario) throws NegocioException {
		if (usuarios.pesquisaPorNome(usuario) && (usuario.getCodigo() == null || usuario.getCodigo()==0)) {
			throw new NegocioException(
					"Já existe um cadastro com este nome de usuário: "+usuario.getUsuario());
		}
		
		this.usuarios.adicionar(usuario);
	}

	@Transactional
	public void excluir(Usuario usuario) {
		usuarios.excluir(usuario);
		
	}

}
