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

import br.com.prodama.model.cadastro.geral.Equipe;
import br.com.prodama.repository.cadastros.Equipes;
import br.com.prodama.service.cadastro.CadastroEquipe;

import br.com.prodama.util.FacesMessages;

@Named
@ViewScoped
public class CadastroEquipeBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private FacesMessages messages;

	@Inject
	private CadastroEquipe cadastroEquipe;

	@Inject
	private Equipes equipes;

	private Equipe equipeEdicao = new Equipe();
	private Equipe equipeSelecionado;
	private List<Equipe> todasEquipes;
	private List<Equipe> filtroEquipes;
	
	@PostConstruct
	public void prepararNovoCadastro() {
		equipeEdicao = new Equipe();
	}

	public void salvar() {
		try {
			this.cadastroEquipe.salvar(equipeEdicao);

			consultar();

			messages.info("Equipe salva com sucesso!");

			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:equipe-table"));

		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao salvar equipe! \n Motivo:" + mensagem.getDetail());
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:equipe-table"));

		}

	}


	public void excluir() {
		try {
			this.cadastroEquipe.excluir(equipeSelecionado);
			equipeSelecionado = null;

			consultar();

			messages.info("Equipe excluída com sucesso!");
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao excluir equipe! \n Motivo:" + mensagem);
		}

	}

	public void consultar() {
		todasEquipes = equipes.todos();
	}

	
	public Equipe getEquipeEdicao() {
		return equipeEdicao;
	}

	public void setEquipeEdicao(Equipe equipeEdicao) {
		this.equipeEdicao = equipeEdicao;
	}

	public Equipe getEquipeSelecionado() {
		return equipeSelecionado;
	}

	public void setEquipeSelecionado(Equipe equipeSelecionado) {
		this.equipeSelecionado = equipeSelecionado;
	}

	public List<Equipe> getTodasEquipes() {
		return todasEquipes;
	}

	public void setTodosEquipes(List<Equipe> todasEquipes) {
		this.todasEquipes = todasEquipes;
	}

	public List<Equipe> getFiltroEquipes() {
		return filtroEquipes;
	}

	public void setFiltroEquipes(List<Equipe> filtroEquipes) {
		this.filtroEquipes = filtroEquipes;
	}
	
	

}
