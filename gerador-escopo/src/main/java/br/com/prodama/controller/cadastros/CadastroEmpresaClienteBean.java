package br.com.prodama.controller.cadastros;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
 


import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.prodama.enun.TipoEmpresa;
import br.com.prodama.model.cadastro.Cidade;
import br.com.prodama.model.cadastro.EmpresaCliente;
import br.com.prodama.model.cadastro.Estado;
import br.com.prodama.repository.cadastros.Cidades;
import br.com.prodama.repository.cadastros.EmpresasCliente;
import br.com.prodama.repository.cadastros.Estados;
import br.com.prodama.service.cadastro.CadastroEmpresaCliente;
import br.com.prodama.util.FacesMessages;
import br.com.prodama.util.componentes.BuscaCEP;

@Named
@ViewScoped
public class CadastroEmpresaClienteBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private FacesMessages messages;

	@Inject
	private CadastroEmpresaCliente cadastroEmpresaCliente;

	@Inject
	private EmpresasCliente empresasCliente;

	@Inject
	private Estados estados;

	@Inject
	private Cidades cidades;


	private TipoEmpresa tipoEmpresa;
	private EmpresaCliente empresaEdicao = new EmpresaCliente();
	private EmpresaCliente empresaSelecionado;
	private Estado estadoSelecionado;
	private BuscaCEP buscarCep = new BuscaCEP();
	private List<EmpresaCliente> todasEmpresas;
	private List<Estado> todosEstados;
	private List<Cidade> todasCidades;
	private List<EmpresaCliente> filtroEmpresas;

	@PostConstruct
	public void prepararNovoCadastro() {
		empresaEdicao = new EmpresaCliente();
		todasCidades = cidades.todos();
		todosEstados = estados.todos();
	}

	public void buscarCep() {
		if (empresaEdicao.getCep().replace("-", "").length() >= 8) {
			buscarCep.Buscar(empresaEdicao.getCep().replace("-", ""));
			empresaEdicao.setBairro(buscarCep.getXmlCep().getBairro());
			empresaEdicao.setEndereco(buscarCep.getXmlCep().getTipo_logradouro()+" " +  buscarCep.getXmlCep().getLogradouro());
			Cidade cidade = cidades.pesquisaPorNome(buscarCep.getXmlCep());
			empresaEdicao.setEstado(cidade.getEstado());
			carregarCidades();
			empresaEdicao.setCidade(cidade);
		}
	}

	public void salvar() {
		try {
			this.cadastroEmpresaCliente.salvar(empresaEdicao);
			consultar();
			todosEstados = estados.todos();
			messages.info("Empresa salva com sucesso!");
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:empresa-table"));

		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao salvar Empresa! \n Motivo:" + mensagem.getDetail());
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:empresa-table"));

		}

	}

	public void listaCidades(AjaxBehaviorEvent event) {
		carregarCidades();
	}
	
	public void excluir() {
		try {
			this.cadastroEmpresaCliente.excluir(empresaSelecionado);
			empresaSelecionado = null;
			consultar();
			todosEstados = estados.todos();
			messages.info("Empresa excluída com sucesso!");
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao excluir Empresa! \n Motivo:" + mensagem);
		}

	}

	private void carregarCidades(){
		todasCidades = cidades.CidadesPorEstado(this.empresaEdicao.getEstado());
	}
	public void consultar() {
		todasEmpresas = empresasCliente.todos();
	}

	public TipoEmpresa[] getTiposEmpresas() {
		return TipoEmpresa.values();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean filterByPrice(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim();
		if (filterText == null || filterText.equals("")) {
			return true;
		}

		if (value == null) {
			return false;
		}

		return ((Comparable) value).compareTo(Integer.valueOf(filterText)) > 0;
	}

	public EmpresasCliente getEmpresas() {
		return empresasCliente;
	}

	public void setEmpresas(EmpresasCliente empresasCliente) {
		this.empresasCliente = empresasCliente;
	}

	public EmpresaCliente getEmpresaEdicao() {
		return empresaEdicao;
	}

	public void setEmpresaEdicao(EmpresaCliente EmpresaEdicao) {
		this.empresaEdicao = EmpresaEdicao;
	}

	public EmpresaCliente getEmpresaSelecionado() {
		return empresaSelecionado;
	}

	public void setEmpresaSelecionado(EmpresaCliente empresaSelecionado) {
		this.empresaSelecionado = empresaSelecionado;
	}

	public List<EmpresaCliente> getTodasEmpresas() {
		return todasEmpresas;
	}

	public void setTodasEmpresas(List<EmpresaCliente> todasEmpresas) {
		this.todasEmpresas = todasEmpresas;
	}

	public List<EmpresaCliente> getFiltroEmpresas() {
		return filtroEmpresas;
	}

	public void setFiltroEmpresas(List<EmpresaCliente> filtroEmpresas) {
		this.filtroEmpresas = filtroEmpresas;
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

}