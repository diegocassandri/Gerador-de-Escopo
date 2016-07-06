package br.com.prodama.controller.cadastro.geral;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.prodama.controller.UsuarioLogin;
import br.com.prodama.model.cadastro.geral.TipoHora;
import br.com.prodama.repository.cadastro.geral.TipoHoras;
import br.com.prodama.service.cadastro.geral.CadastroTipoHora;
import br.com.prodama.util.FacesMessages;

@Named
@ViewScoped
public class CadastroTipoHoraBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private FacesMessages messages;

	@Inject
	private CadastroTipoHora cadastroTipoDeHora;
	
	@Inject
	private TipoHoras tipoDeHoras;
	
	@Inject
	UsuarioLogin usuarioLogin;
	
	private TipoHora tipoDeHoraEdicao = new TipoHora();
	private TipoHora tipoDeHoraSelecionado;
	private List<TipoHora> todosTipoDeHora;
	
	
	@PostConstruct
	public void prepararNovoCadastro() {
		tipoDeHoraEdicao = new TipoHora();
		tipoDeHoraEdicao.setCodigoUsuarioInclusao(usuarioLogin.getUsuarioLogin());
		tipoDeHoraEdicao.setDataIniclusao(new Date());
		tipoDeHoraEdicao.setCodigoUsuarioAlteracao(usuarioLogin.getUsuarioLogin());
		tipoDeHoraEdicao.setDataAlteracao(new Date());
	}
	
	public void salvar() {
		try {
			if (tipoDeHoraEdicao.getCodigo()!=null) {
				tipoDeHoraEdicao.setCodigoUsuarioAlteracao(usuarioLogin.getUsuarioLogin());
				tipoDeHoraEdicao.setDataAlteracao(new Date());
			}
			this.cadastroTipoDeHora.salvar(tipoDeHoraEdicao);
			consultar();
			messages.info("Tipo de Hora salvo com sucesso!");
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:tipo-table"));
			RequestContext.getCurrentInstance().execute("PF('edicaoTipoDialog').hide()");
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao salvar Tipo de Hora! \n Motivo:" + mensagem.getDetail());
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:tipo-table"));

		}

	}
	
	public void excluir() {
		try {
			this.cadastroTipoDeHora.excluir(tipoDeHoraEdicao);
			tipoDeHoraSelecionado = null;

			consultar();

			messages.info("Tipo de Hora excluído com sucesso!");
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao excluir tipo de Hora! \n Motivo:" + mensagem);
		}

	}
	
	public void consultar() {
		todosTipoDeHora = tipoDeHoras.todos();
	}

	public TipoHora getTipoDeHoraEdicao() {
		return tipoDeHoraEdicao;
	}

	public void setTipoDeHoraEdicao(TipoHora tipoDeHoraEdicao) {
		this.tipoDeHoraEdicao = tipoDeHoraEdicao;
	}

	public TipoHora getTipoDeHoraSelecionado() {
		return tipoDeHoraSelecionado;
	}

	public void setTipoDeHoraSelecionado(TipoHora tipoDeHoraSelecionado) {
		this.tipoDeHoraSelecionado = tipoDeHoraSelecionado;
	}

	public List<TipoHora> getTodosTipoDeHora() {
		return todosTipoDeHora;
	}

	public void setTodosTipoDeHora(List<TipoHora> todosTipoDeHora) {
		this.todosTipoDeHora = todosTipoDeHora;
	}
	
	
	
}
