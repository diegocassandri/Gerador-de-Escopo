package br.com.prodama.controller.cadastro.produto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

import br.com.prodama.enun.Status;
import br.com.prodama.model.cadastro.produto.GestaoModulo;
import br.com.prodama.model.cadastro.produto.Modulo;
import br.com.prodama.model.cadastro.produto.ProcessoGestao;
import br.com.prodama.repository.cadastro.produto.GestaoModulos;
import br.com.prodama.repository.cadastro.produto.Processos;
import br.com.prodama.service.NegocioException;
import br.com.prodama.service.cadastro.produto.CadastroProcessoGestao;
import br.com.prodama.util.FacesMessages;

@Named
@ViewScoped
public class CadastroProcessoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FacesMessages messages;

	@Inject
	private CadastroProcessoGestao cadastroProcessoGestao;

	@Inject
	private Processos processos;

	@Inject
	private GestaoModulos gestaoModulos;

	private ProcessoGestao processoEdicao = new ProcessoGestao();
	private ProcessoGestao processoSelecionado;

	private GestaoModulo gestaoEdicao = new GestaoModulo();
	private GestaoModulo gestaoSelecionado;

	private List<ProcessoGestao> todosProcessos;
	private List<GestaoModulo> filtroGestao;
	private List<ProcessoGestao> filtroProcesso;

	public List<ProcessoGestao> getFiltroProcesso() {
		return filtroProcesso;
	}

	public void setFiltroProcesso(List<ProcessoGestao> filtroProcesso) {
		this.filtroProcesso = filtroProcesso;
	}

	private List<GestaoModulo> todosGestaoModulo;
	private List<Modulo> todosModulos;

	@PostConstruct
	public void prepararNovoCadastro() {
		processoEdicao = new ProcessoGestao();
		todosGestaoModulo = gestaoModulos.todos();
	}

	public void novoProcesso() {
		processoEdicao = new ProcessoGestao();
		todosProcessos.add(processoEdicao);

	}

	public void salvar() {
		try {
			processoEdicao.setGestaoModulo(gestaoEdicao);
			this.cadastroProcessoGestao.salvar(processoEdicao);
			consultar();
			todosGestaoModulo = gestaoModulos.todos();
			gestaoEdicao = gestaoModulos.pesquisaPorId(gestaoEdicao.getCodigo());
			todosProcessos = gestaoEdicao.getListaProcessosGestoes();
			messages.info("Processo salvo com sucesso!");
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:gestao-table"));
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao salvar Processo! \n Motivo:" + mensagem.getDetail());
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:gestao-table"));
		}

	}

	public void onRowEdit(RowEditEvent event) throws NegocioException {
		processoEdicao = (ProcessoGestao) event.getObject();
		processoEdicao.setGestaoModulo(gestaoEdicao);
		this.cadastroProcessoGestao.salvar(processoEdicao);
		todosGestaoModulo = gestaoModulos.todos();
		gestaoEdicao = gestaoModulos.pesquisaPorId(gestaoEdicao.getCodigo());
		todosProcessos = gestaoEdicao.getListaProcessosGestoes();
		todosProcessos.add(processoEdicao);
		messages.info("Processo salvo com sucesso!");
		RequestContext.getCurrentInstance()
				.update(Arrays.asList("frmProcesso:msgs-consulta", "frmProcesso:processo-table"));
	}

	public void onRowCancel(RowEditEvent event) {
		if(processoEdicao.getCodigo() == null) {
			todosProcessos.remove(processoEdicao);
			RequestContext.getCurrentInstance().update(Arrays.asList("frmProcesso:msgs-consulta", "frmProcesso:processo-table"));
		}
		
	}

	public void excluir() {
		try {
			this.cadastroProcessoGestao.excluir(processoEdicao);
			todosProcessos.remove(processoEdicao);
			processoSelecionado = null;
			consultar();
			todosGestaoModulo = gestaoModulos.todos();
			messages.info("Processo excluído com sucesso!");
			RequestContext.getCurrentInstance()
					.update(Arrays.asList("frmProcesso:msgs-consulta", "frmProcesso:processo-table"));
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao excluir Processo! \n Motivo:" + mensagem);
			RequestContext.getCurrentInstance()
					.update(Arrays.asList("frmProcesso:msgs-consulta", "frmProcesso:processo-table"));
		}

	}

	public void consultar() {
		todosGestaoModulo = gestaoModulos.todos();
	}

	public void consultaProcessos() {
		gestaoEdicao = gestaoModulos.pesquisaPorId(gestaoEdicao.getCodigo());
		todosProcessos = gestaoEdicao.getListaProcessosGestoes();
	}

	public List<Status> getStatusList() {
		return Arrays.asList(Status.values());
	}

	public List<Modulo> getTodosModulos() {
		return todosModulos;
	}

	public void setTodosModulos(List<Modulo> todosModulos) {
		this.todosModulos = todosModulos;
	}

	public ProcessoGestao getProcessoEdicao() {
		return processoEdicao;
	}

	public void setProcessoEdicao(ProcessoGestao processoEdicao) {
		this.processoEdicao = processoEdicao;
	}

	public ProcessoGestao getProcessoSelecionado() {
		return processoSelecionado;
	}

	public void setProcessoSelecionado(ProcessoGestao processoSelecionado) {
		this.processoSelecionado = processoSelecionado;
	}

	public List<ProcessoGestao> getTodosProcessos() {
		return todosProcessos;
	}

	public void setTodosProcessos(List<ProcessoGestao> todosProcessos) {
		this.todosProcessos = todosProcessos;
	}

	public List<GestaoModulo> getFiltroGestao() {
		return filtroGestao;
	}

	public void setFiltroGestao(List<GestaoModulo> filtroGestao) {
		this.filtroGestao = filtroGestao;
	}

	public List<GestaoModulo> getTodosGestaoModulo() {
		return todosGestaoModulo;
	}

	public void setTodosGestaoModulo(List<GestaoModulo> todosGestaoModulo) {
		this.todosGestaoModulo = todosGestaoModulo;
	}

	public GestaoModulo getGestaoEdicao() {
		return gestaoEdicao;
	}

	public void setGestaoEdicao(GestaoModulo gestaoEdicao) {
		this.gestaoEdicao = gestaoEdicao;
	}

	public GestaoModulo getGestaoSelecionado() {
		return gestaoSelecionado;
	}

	public void setGestaoSelecionado(GestaoModulo gestaoSelecionado) {
		this.gestaoSelecionado = gestaoSelecionado;
	}

	public Processos getProcessos() {
		return processos;
	}

	public void setProcessos(Processos processos) {
		this.processos = processos;
	}

}
