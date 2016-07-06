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

import br.com.prodama.enun.RegimeTributacao;
import br.com.prodama.enun.TipoEmpresa;
import br.com.prodama.model.cadastro.geral.Cidade;
import br.com.prodama.model.cadastro.geral.EmpresaCliente;
import br.com.prodama.model.cadastro.geral.Estado;
import br.com.prodama.model.cadastro.geral.FilialCliente;
import br.com.prodama.repository.cadastro.geral.Cidades;
import br.com.prodama.repository.cadastro.geral.EmpresasCliente;
import br.com.prodama.repository.cadastro.geral.Estados;
import br.com.prodama.repository.cadastro.geral.FiliaisCliente;
import br.com.prodama.service.cadastro.geral.CadastroFilialCliente;
import br.com.prodama.util.FacesMessages;

@Named
@ViewScoped
public class CadastroFilialClienteBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private FacesMessages messages;
	
	@Inject
	private CadastroFilialCliente cadastroFilialCliente;
	
	@Inject
	private FiliaisCliente filiaisCliente;
	
	@Inject
	private Estados estados;

	@Inject
	private Cidades cidades;
	
	@Inject
	private EmpresasCliente empresasCliente;
		
	private TipoEmpresa tipoEmpresa;
	private RegimeTributacao tipoRegime;
	private FilialCliente filialEdicao = new FilialCliente();
	private FilialCliente filialSelecionado;
	private List<Estado> todosEstados;
	private List<Cidade> todasCidades;
	private List<FilialCliente> todasFiliais;
	private List<EmpresaCliente> todasEmpresas;

	
	@PostConstruct
	public void prepararNovoCadastro() {
		filialEdicao = new FilialCliente();
		todasCidades = cidades.todos();
		todosEstados = estados.todos();
		todasEmpresas = empresasCliente.todos();
	}
	
	public void salvar() {
		try {
			this.cadastroFilialCliente.salvar(filialEdicao);
			consultar();
			todasEmpresas = empresasCliente.todos();
			todosEstados = estados.todos();
			messages.info("Filial salva com sucesso!");
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:filial-table"));
			RequestContext.getCurrentInstance().execute("PF('edicaoFilialDialog').hide()");
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao salvar Filial! \n Motivo:" + mensagem.getDetail());
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:filial-table"));

		}

	}
	


	public void excluir() {
		try {
			this.cadastroFilialCliente.excluir(filialSelecionado);
			filialSelecionado = null;
			consultar();
			todosEstados = estados.todos();
			todasEmpresas = empresasCliente.todos();
			messages.info("Filial excluída com sucesso!");
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao excluir Filial! \n Motivo:" + mensagem);
		}

	}

	public void consultar() {
		todasFiliais = filiaisCliente.todos();
	}

	public TipoEmpresa[] getTiposEmpresas() {
		return TipoEmpresa.values();
	}
	
	public RegimeTributacao[] getTiposRegime() {
		return RegimeTributacao.values();
	}

	public FilialCliente getFilialEdicao() {
		return filialEdicao;
	}

	public void setFilialEdicao(FilialCliente filialEdicao) {
		this.filialEdicao = filialEdicao;
	}

	public FilialCliente getFilialSelecionado() {
		return filialSelecionado;
	}

	public void setFilialSelecionado(FilialCliente filialSelecionado) {
		this.filialSelecionado = filialSelecionado;
	}

	public List<Estado> getTodosEstados() {
		return todosEstados;
	}

	public void setTodosEstados(List<Estado> todosEstados) {
		this.todosEstados = todosEstados;
	}

	public List<Cidade> getTodasCidades() {
		return todasCidades;
	}

	public void setTodasCidades(List<Cidade> todasCidades) {
		this.todasCidades = todasCidades;
	}

	public List<FilialCliente> getTodasFiliais() {
		return todasFiliais;
	}

	public void setTodasFiliais(List<FilialCliente> todasFiliais) {
		this.todasFiliais = todasFiliais;
	}

	public TipoEmpresa getTipoEmpresa() {
		return tipoEmpresa;
	}

	public void setTipoEmpresa(TipoEmpresa tipoEmpresa) {
		this.tipoEmpresa = tipoEmpresa;
	}

	public List<EmpresaCliente> getTodasEmpresas() {
		return todasEmpresas;
	}

	public void setTodasEmpresas(List<EmpresaCliente> todasEmpresas) {
		this.todasEmpresas = todasEmpresas;
	}

	public RegimeTributacao getTipoRegime() {
		return tipoRegime;
	}

	public void setTipoRegime(RegimeTributacao tipoRegime) {
		this.tipoRegime = tipoRegime;
	}
	
	
}
