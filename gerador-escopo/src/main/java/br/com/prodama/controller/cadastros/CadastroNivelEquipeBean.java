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

import br.com.prodama.model.cadastro.Equipe;
import br.com.prodama.model.cadastro.NivelEquipe;
import br.com.prodama.repository.cadastros.Equipes;
import br.com.prodama.repository.cadastros.NiveisEquipe;
import br.com.prodama.service.cadastro.CadastroNivelEquipe;
import br.com.prodama.util.FacesMessages;

@Named
@ViewScoped
public class CadastroNivelEquipeBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private FacesMessages messages;

	@Inject
	private CadastroNivelEquipe cadastroNivelEquipe;

	@Inject
	private NiveisEquipe niveisEquipe;
	
	@Inject
	private Equipes equipes;
	
	private NivelEquipe nivelEquipeEdicao = new NivelEquipe();
	private NivelEquipe nivelEquipeSelecionado;
	private List<NivelEquipe> todosNiveisEquipe;
	private List<Equipe> todasEquipes;
	private List<NivelEquipe> filtroNiveisEquipe;
	
	@PostConstruct
	public void prepararNovoCadastro() {
		nivelEquipeEdicao = new NivelEquipe();
	    todasEquipes = equipes.todos();
	}
	
	public void salvar() {
		try {
			this.cadastroNivelEquipe.salvar(nivelEquipeEdicao);
			consultar();
			todosNiveisEquipe = niveisEquipe.todos();
			messages.info("Nível de Equipe salva com sucesso!");
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:equipe-table"));
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao salvar cidade! \n Motivo:" + mensagem.getDetail());
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:equipe-table"));

		}

	}


	public void excluir() {
		try {
			this.cadastroNivelEquipe.excluir(nivelEquipeSelecionado);
			nivelEquipeSelecionado = null;

			consultar();
			todasEquipes= equipes.todos();
			messages.info("Nível de equipe excluída com sucesso!");
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao excluir nível de equipe! \n Motivo:" + mensagem);
		}

	}

	public void consultar() {
		todosNiveisEquipe = niveisEquipe.todos();
	}

	public NiveisEquipe getNiveisEquipe() {
		return niveisEquipe;
	}

	public void setNiveisEquipe(NiveisEquipe niveisEquipe) {
		this.niveisEquipe = niveisEquipe;
	}

	public NivelEquipe getNivelEquipeEdicao() {
		return nivelEquipeEdicao;
	}

	public void setNivelEquipeEdicao(NivelEquipe nivelEquipeEdicao) {
		this.nivelEquipeEdicao = nivelEquipeEdicao;
	}

	public NivelEquipe getNivelEquipeSelecionado() {
		return nivelEquipeSelecionado;
	}

	public void setNivelEquipeSelecionado(NivelEquipe nivelEquipeSelecionado) {
		this.nivelEquipeSelecionado = nivelEquipeSelecionado;
	}

	public List<NivelEquipe> getTodosNiveisEquipe() {
		return todosNiveisEquipe;
	}

	public void setTodosNiveisEquipe(List<NivelEquipe> todosNiveisEquipe) {
		this.todosNiveisEquipe = todosNiveisEquipe;
	}

	public List<Equipe> getTodasEquipes() {
		return todasEquipes;
	}

	public void setTodasEquipes(List<Equipe> todasEquipes) {
		this.todasEquipes = todasEquipes;
	}

	public List<NivelEquipe> getFiltroNiveisEquipe() {
		return filtroNiveisEquipe;
	}

	public void setFiltroNiveisEquipe(List<NivelEquipe> filtroNiveisEquipe) {
		this.filtroNiveisEquipe = filtroNiveisEquipe;
	}
	
	

}
