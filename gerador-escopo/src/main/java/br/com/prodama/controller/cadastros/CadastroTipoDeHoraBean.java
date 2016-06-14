package br.com.prodama.controller.cadastros;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.prodama.model.cadastro.TipoDeHora;
import br.com.prodama.repository.cadastros.TipoDeHoras;
import br.com.prodama.service.cadastro.CadastroTipoDeHora;
import br.com.prodama.util.FacesMessages;

@Named
@ViewScoped
public class CadastroTipoDeHoraBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private FacesMessages messages;

	@Inject
	private CadastroTipoDeHora cadastroTipoDeHora;
	
	@Inject
	private TipoDeHoras tipoDeHoras;
	
	private TipoDeHora tipoDeHoraEdicao = new TipoDeHora();
	private TipoDeHora tipoDeHoraSelecionado;
	private List<TipoDeHora> todosTipoDeHora;
	
	
	@PostConstruct
	public void prepararNovoCadastro() {
		tipoDeHoraEdicao = new TipoDeHora();
	}
	
	public void salvar() {
		try {
			this.cadastroTipoDeHora.salvar(tipoDeHoraEdicao);
			consultar();
			messages.info("Tipo de Hora salvo com sucesso!");
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:tipo-table"));

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

	public TipoDeHora getTipoDeHoraEdicao() {
		return tipoDeHoraEdicao;
	}

	public void setTipoDeHoraEdicao(TipoDeHora tipoDeHoraEdicao) {
		this.tipoDeHoraEdicao = tipoDeHoraEdicao;
	}

	public TipoDeHora getTipoDeHoraSelecionado() {
		return tipoDeHoraSelecionado;
	}

	public void setTipoDeHoraSelecionado(TipoDeHora tipoDeHoraSelecionado) {
		this.tipoDeHoraSelecionado = tipoDeHoraSelecionado;
	}

	public List<TipoDeHora> getTodosTipoDeHora() {
		return todosTipoDeHora;
	}

	public void setTodosTipoDeHora(List<TipoDeHora> todosTipoDeHora) {
		this.todosTipoDeHora = todosTipoDeHora;
	}
	
	
	
}
