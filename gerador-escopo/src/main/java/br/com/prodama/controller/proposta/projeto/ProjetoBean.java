package br.com.prodama.controller.proposta.projeto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;


import br.com.prodama.util.FacesMessages;
import br.com.prodama.controller.UsuarioLogin;
import br.com.prodama.model.cadastro.geral.Empresa;
import br.com.prodama.model.cadastro.geral.FaixaColaborador;
import br.com.prodama.model.cadastro.geral.Filial;
import br.com.prodama.model.cadastro.geral.Pessoa;
import br.com.prodama.model.cadastro.produto.Produto;
import br.com.prodama.model.proposta.projeto.CronogramaProjeto;
import br.com.prodama.repository.cadastro.geral.FaixaColaboradores;
import br.com.prodama.repository.cadastro.geral.Filiais;
import br.com.prodama.repository.cadastro.geral.Pessoas;
import br.com.prodama.repository.cadastro.produto.Produtos;
import br.com.prodama.repository.proposta.projeto.CronogramasProjetos;
import br.com.prodama.service.proposta.projeto.CadastroCromogramaProjeto;


@Named
@ViewScoped
public class ProjetoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private FacesMessages messages;

	@Inject
	private CadastroCromogramaProjeto cadastroCromogramaProjeto;

	@Inject
	private CronogramasProjetos cronogramasProjeto;

	@Inject
	private Produtos produtos;
	
	/*@Inject
	private Empresas empresas;*/
	
	@Inject
	private Filiais filiais;

	@Inject
	private Pessoas clientes;
	
	@Inject
	private FaixaColaboradores faixas;
	
	@Inject
	private UsuarioLogin usuarioLogin;
	
	/* Filtros*/
	private Long codigo;
	private String descricao;
	private Empresa empresa;
	private Filial filial;
	private Pessoa cliente;
	private Produto produto;
	/**/
	
	
	private CronogramaProjeto cronogramaEdicao;
	private CronogramaProjeto cronogramaSelecionado;
	private List<CronogramaProjeto> todosCronogramas;
	private List<CronogramaProjeto> filtroCronogramas;
	private List<Produto> todosProdutos;
	private List<Empresa> todasEmpresas;
	private List<Filial> todasFiliais;
	private List<Pessoa> todosClientes;
	private List<FaixaColaborador> todasFaixas;
	
	@PostConstruct
	public void prepararNovoCadastro() {
		cronogramaEdicao = new CronogramaProjeto();
		todosProdutos = produtos.todos();
		todasEmpresas = usuarioLogin.getAbrangeciaEmpresa();
		todasFiliais = usuarioLogin.getAbrangenciaFilial();
		todosClientes = clientes.todos();
		todasFaixas = faixas.todos();

	}

	public void salvar() {
		try {
			this.cadastroCromogramaProjeto.salvar(cronogramaEdicao);
			consultar();
			todosProdutos = produtos.todos();
			todasEmpresas = usuarioLogin.getAbrangeciaEmpresa();
			todasFiliais = usuarioLogin.getAbrangenciaFilial();
			todosClientes = clientes.todos();
			todasFaixas = faixas.todos();
			messages.info("Projeto salvo com sucesso!");
			RequestContext.getCurrentInstance()
					.update(Arrays.asList("frmCadastro:msgs", "frmCadastro:cronograma-table"));
			RequestContext.getCurrentInstance().execute("PF('edicaoCronogramaDialog').hide()");
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao salvar Projeto! \n Motivo:" + mensagem.getDetail());
			RequestContext.getCurrentInstance().update(Arrays.asList("msgs"));
		}

	}

	public void excluir() {
		try {
			this.cadastroCromogramaProjeto.excluir(cronogramaSelecionado);
			cronogramaSelecionado = null;
			consultar();
			todosProdutos = produtos.todos();
			todasEmpresas = usuarioLogin.getAbrangeciaEmpresa();
			todasFiliais = usuarioLogin.getAbrangenciaFilial();
			todosClientes = clientes.todos();
			todasFaixas = faixas.todos();
			messages.info("Projeto excluído com sucesso!");
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao excluir projeto! \n Motivo:" + mensagem);
		}

	}
	
	public void consultarFiltros() {
		todosCronogramas = cronogramasProjeto.pesquisaFiltros(codigo,descricao,empresa,filial,produto,cliente);
	}
	

	public void consultar() {
		todosCronogramas = cronogramasProjeto.todos();
	}
	
	public void listaFiliais(AjaxBehaviorEvent event) {
		carregarFiliais();
	}
	
	private void carregarFiliais(){ 
		todasFiliais = filiais.filiaisPorEmpresa(this.cronogramaEdicao.getEmpresa()); 
	}

	public CronogramaProjeto getCronogramaEdicao() {
		return cronogramaEdicao;
	}

	public void setCronogramaEdicao(CronogramaProjeto cronogramaEdicao) {
		this.cronogramaEdicao = cronogramaEdicao;
	}

	public CronogramaProjeto getCronogramaSelecionado() {
		return cronogramaSelecionado;
	}

	public void setCronogramaSelecionado(CronogramaProjeto cronogramaSelecionado) {
		this.cronogramaSelecionado = cronogramaSelecionado;
	}

	public List<CronogramaProjeto> getTodosCronogramas() {
		return todosCronogramas;
	}

	public void setTodosCronogramas(List<CronogramaProjeto> todosCronogramas) {
		this.todosCronogramas = todosCronogramas;
	}

	public List<CronogramaProjeto> getFiltroCronogramas() {
		return filtroCronogramas;
	}

	public void setFiltroCronogramas(List<CronogramaProjeto> filtroCronogramas) {
		this.filtroCronogramas = filtroCronogramas;
	}

	public List<Produto> getTodosProdutos() {
		return todosProdutos;
	}

	public void setTodosProdutos(List<Produto> todosProdutos) {
		this.todosProdutos = todosProdutos;
	}

	public List<Empresa> getTodasEmpresas() {
		return todasEmpresas;
	}

	public void setTodasEmpresas(List<Empresa> todasEmpresas) {
		this.todasEmpresas = todasEmpresas;
	}

	public List<Filial> getTodasFiliais() {
		return todasFiliais;
	}

	public void setTodasFiliais(List<Filial> todasFiliais) {
		this.todasFiliais = todasFiliais;
	}

	public List<Pessoa> getTodosClientes() {
		return todosClientes;
	}

	public void setTodosClientes(List<Pessoa> todosClientes) {
		this.todosClientes = todosClientes;
	}

	public List<FaixaColaborador> getTodasFaixas() {
		return todasFaixas;
	}

	public void setTodasFaixas(List<FaixaColaborador> todasFaixas) {
		this.todasFaixas = todasFaixas;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}

	public Pessoa getCliente() {
		return cliente;
	}

	public void setCliente(Pessoa cliente) {
		this.cliente = cliente;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	

}
