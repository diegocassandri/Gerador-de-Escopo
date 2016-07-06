package br.com.prodama.controller.cadastro.geral;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.prodama.controller.UsuarioLogin;
import br.com.prodama.model.cadastro.geral.FaixaColaborador;
import br.com.prodama.repository.cadastro.geral.FaixaColaboradores;
import br.com.prodama.service.cadastro.geral.CadastroFaixaColaborador;
import br.com.prodama.util.FacesMessages;

@Named
@ViewScoped
public class CadastroFaixaColaboradorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FacesMessages messages;

	@Inject
	private CadastroFaixaColaborador cadastroFaixaColaborador;

	@Inject
	private FaixaColaboradores faixaColaboradores;

	@Inject
	private UsuarioLogin usuarioLogin;

	private FaixaColaborador faixaEdicao = new FaixaColaborador();
	private FaixaColaborador faixaSelecionada;
	private List<FaixaColaborador> todasFaixas;
	private List<FaixaColaborador> filtroFaixas;

	@PostConstruct
	public void prepararNovoCadastro() {
		faixaEdicao = new FaixaColaborador();
	}

	public void salvar() {
		try {
			if ((faixaEdicao.getCodigo() == null || faixaEdicao.getCodigo() == 0)) {
				faixaEdicao.setCodigoUsuarioInclusao(usuarioLogin.getUsuarioLogin());
				faixaEdicao.setCodigoUsuarioAlteracao(usuarioLogin.getUsuarioLogin());
			} else {
				faixaEdicao.setCodigoUsuarioAlteracao(usuarioLogin.getUsuarioLogin());
			}
			this.cadastroFaixaColaborador.salvar(faixaEdicao);
			consultar();
			messages.info("Faixa de Colaborador salva com sucesso!");
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:faixa-table"));
			RequestContext.getCurrentInstance().execute("PF('edicaoFaixaDialog').hide()");
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao salvar faixa de Colaborador! \n Motivo:" + mensagem.getDetail());
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:faixa-table"));
		}

	}

	public void excluir() {
		try {
			this.cadastroFaixaColaborador.excluir(faixaSelecionada);
			faixaSelecionada = null;
			consultar();
			messages.info("Faixa de Colaborador excluída com sucesso!");
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao excluir faixa de Colaborador! \n Motivo:" + mensagem);
		}

	}

	public void consultar() {
		todasFaixas = faixaColaboradores.todos();
	}

	public FaixaColaborador getFaixaEdicao() {
		return faixaEdicao;
	}

	public void setFaixaEdicao(FaixaColaborador faixaEdicao) {
		this.faixaEdicao = faixaEdicao;
	}

	public FaixaColaborador getFaixaSelecionada() {
		return faixaSelecionada;
	}

	public void setFaixaSelecionada(FaixaColaborador faixaSelecionada) {
		this.faixaSelecionada = faixaSelecionada;
	}

	public List<FaixaColaborador> getTodasFaixas() {
		return todasFaixas;
	}

	public void setTodasFaixas(List<FaixaColaborador> todasFaixas) {
		this.todasFaixas = todasFaixas;
	}

	public List<FaixaColaborador> getFiltroFaixas() {
		return filtroFaixas;
	}

	public void setFiltroFaixas(List<FaixaColaborador> filtroFaixas) {
		this.filtroFaixas = filtroFaixas;
	}
	
	

}
