package br.com.prodama.controller.cadastro.geral;

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

import br.com.prodama.enun.Status;
import br.com.prodama.enun.TipoEmpresa;
import br.com.prodama.enun.TipoPessoa;
import br.com.prodama.model.cadastro.geral.Cidade;
import br.com.prodama.model.cadastro.geral.Empresa;
import br.com.prodama.model.cadastro.geral.Equipe;
import br.com.prodama.model.cadastro.geral.Estado;
import br.com.prodama.model.cadastro.geral.Filial;
import br.com.prodama.model.cadastro.geral.NivelEquipe;
import br.com.prodama.model.cadastro.geral.Pessoa;
import br.com.prodama.repository.cadastro.geral.Cidades;
import br.com.prodama.repository.cadastro.geral.Empresas;
import br.com.prodama.repository.cadastro.geral.Equipes;
import br.com.prodama.repository.cadastro.geral.Estados;
import br.com.prodama.repository.cadastro.geral.Filiais;
import br.com.prodama.repository.cadastro.geral.NiveisEquipe;
import br.com.prodama.repository.cadastro.geral.Pessoas;
import br.com.prodama.service.cadastro.geral.CadastroPessoa;
import br.com.prodama.util.FacesMessages;
import br.com.prodama.util.componentes.BuscaCEP;

@Named
@ViewScoped
public class CadastroPessoaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private FacesMessages messages;

	@Inject
	private CadastroPessoa cadastroPessoa;

	@Inject
	private Pessoas pessoas;

	@Inject
	private Estados estados;

	@Inject
	private Cidades cidades;
	
	@Inject
	private Empresas empresas;
	
	@Inject
	private Filiais filiais;
	
	@Inject
	private Equipes equipes;
	
	@Inject
	private NiveisEquipe niveisEquipe;

	private TipoEmpresa tipoEmpresa;
	private TipoPessoa tipoPessoa;
	
	private Pessoa pessoaEdicao = new Pessoa();
	private Pessoa pessoaSelecionado;
	private Estado estadoSelecionado;
	private BuscaCEP buscarCep = new BuscaCEP();
	private List<Pessoa> todasPessoas;
	private List<Empresa> todasEmpresas;
	private List<Filial> todasFiliais;
	private List<Estado> todosEstados;
	private List<Equipe> todasEquipes;
	private List<NivelEquipe> todosNiveisEquipe;
	private List<Cidade> todasCidades;
	private List<Pessoa> filtroPessoas;
	
	/*Filtros*/
		private String razao;
		private String fantasia;
		private Status status;
		private TipoPessoa tipoP;
	/**/

	@PostConstruct
	public void prepararNovoCadastro() {
		pessoaEdicao = new Pessoa();
		todasCidades = cidades.todos();
		todosEstados = estados.todos();
		todasEmpresas = empresas.todos();
		todasEquipes = equipes.todos();
		todasFiliais = filiais.todos();
		todosNiveisEquipe = niveisEquipe.todos();
	}

	public void buscarCep() {
		if (pessoaEdicao.getCep().replace("-", "").replace("_","").length() >= 8) {
			try{
				buscarCep.Buscar(pessoaEdicao.getCep().replace("-", ""));
				pessoaEdicao.setBairro(buscarCep.getXmlCep().getBairro());
				pessoaEdicao.setEndereco(buscarCep.getXmlCep().getTipo_logradouro()+" " +  buscarCep.getXmlCep().getLogradouro());
				Cidade cidade = cidades.pesquisaPorNome(buscarCep.getXmlCep());
				pessoaEdicao.setEstado(cidade.getEstado());
				carregarCidades();
				pessoaEdicao.setCidade(cidade);
			}catch(Exception e){
				e.getMessage();
			}	
		}
	}

	public void salvar() {
		try {
			this.cadastroPessoa.salvar(pessoaEdicao);
			consultar();
			todasPessoas = pessoas.todos();
			todosEstados = estados.todos();
			todosEstados = estados.todos();
			todasEmpresas = empresas.todos();
			todasFiliais = filiais.todos();
			todasEquipes = equipes.todos();
			todosNiveisEquipe = niveisEquipe.todos();
			messages.info("Pessoa salva com sucesso!");
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:pessoa-table"));
			RequestContext.getCurrentInstance().execute("PF('edicaoPessoaDialog').hide()");
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao salvar Pessoa! \n Motivo:" + mensagem.getDetail());
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:pessoa-table"));

		}

	}

	public void listaCidades(AjaxBehaviorEvent event) {
		carregarCidades();
	}
	
	

	public void excluir() {
		try {
			this.cadastroPessoa.excluir(pessoaSelecionado);
			pessoaSelecionado = null;
			consultar();
			todasPessoas = pessoas.todos();
			todosEstados = estados.todos();
			todosEstados = estados.todos();
			todasEmpresas = empresas.todos();
			todasFiliais = filiais.todos();
			todasEquipes = equipes.todos();
			messages.info("Pessoa excluída com sucesso!");
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao excluir Pessoa! \n Motivo:" + mensagem);
		}

	}

	private void carregarCidades(){
		todasCidades = cidades.CidadesPorEstado(this.pessoaEdicao.getEstado());
	}
	
	private void carregarFiliais(){ 
		todasFiliais = filiais.filiaisPorEmpresa(this.pessoaEdicao.getEmpresa()); 
	}
	
	private void carregarNiveisEquipes(){
		todosNiveisEquipe = niveisEquipe.niveisPorEquipe(this.pessoaEdicao.getEquipe()); 
	}
	
	public void consultar() {
		todasPessoas = pessoas.todos();
	}
	
	public void consultarFiltros() {
		todasPessoas = pessoas.pesquisaFiltros(razao,fantasia,tipoP,status);
	}
	

	public void listaFiliais(AjaxBehaviorEvent event) {
		carregarFiliais();
	}
	
	public void listaNiveisEquipes(AjaxBehaviorEvent event) {
		carregarNiveisEquipes();
	}
	
	
	public TipoEmpresa[] getTiposEmpresas() {
		return TipoEmpresa.values();
	}
	
	public TipoPessoa[] getTiposPessoas() {
		return TipoPessoa.values();
	}

	public List<Status> getStatusList() {
		return Arrays.asList(Status.values());
	}


	public List<Estado> getTodosEstados() {
		return todosEstados;
	}

	public void setTodosEstados(List<Estado> todosEstados) {
		this.todosEstados = todosEstados;
	}

	public TipoEmpresa getTipoEmpresa() {
		return tipoEmpresa;
	}

	public void setTipoEmpresa(TipoEmpresa tipoEmpresa) {
		this.tipoEmpresa = tipoEmpresa;
	}

	public List<Cidade> getTodasCidades() {
		return todasCidades;
	}

	public void setTodasCidades(List<Cidade> todasCidades) {
		this.todasCidades = todasCidades;
	}

	public Estado getEstadoSelecionado() {
		return estadoSelecionado;
	}

	public void setEstadoSelecionado(Estado estadoSelecionado) {
		this.estadoSelecionado = estadoSelecionado;
	}

	public NiveisEquipe getNiveisEquipe() {
		return niveisEquipe;
	}

	public void setNiveisEquipe(NiveisEquipe niveisEquipe) {
		this.niveisEquipe = niveisEquipe;
	}

	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public Pessoa getPessoaEdicao() {
		return pessoaEdicao;
	}

	public void setPessoaEdicao(Pessoa pessoaEdicao) {
		this.pessoaEdicao = pessoaEdicao;
	}

	public List<Pessoa> getTodasPessoas() {
		return todasPessoas;
	}

	public void setTodasPessoas(List<Pessoa> todasPessoas) {
		this.todasPessoas = todasPessoas;
	}


	public List<Equipe> getTodasEquipes() {
		return todasEquipes;
	}

	public void setTodasEquipes(List<Equipe> todasEquipes) {
		this.todasEquipes = todasEquipes;
	}

	public List<NivelEquipe> getTodosNiveisEquipe() {
		return todosNiveisEquipe;
	}

	public void setTodosNiveisEquipe(List<NivelEquipe> todosNiveisEquipe) {
		this.todosNiveisEquipe = todosNiveisEquipe;
	}

	public List<Pessoa> getFiltroPessoas() {
		return filtroPessoas;
	}

	public void setFiltroPessoas(List<Pessoa> filtroPessoas) {
		this.filtroPessoas = filtroPessoas;
	}

	public Pessoa getPessoaSelecionado() {
		return pessoaSelecionado;
	}

	public void setPessoaSelecionado(Pessoa pessoaSelecionado) {
		this.pessoaSelecionado = pessoaSelecionado;
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

	public String getRazao() {
		return razao;
	}

	public void setRazao(String razao) {
		this.razao = razao;
	}

	public String getFantasia() {
		return fantasia;
	}

	public void setFantasia(String fantasia) {
		this.fantasia = fantasia;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public TipoPessoa getTipoP() {
		return tipoP;
	}

	public void setTipoP(TipoPessoa tipoP) {
		this.tipoP = tipoP;
	}

	

	
	
	

}