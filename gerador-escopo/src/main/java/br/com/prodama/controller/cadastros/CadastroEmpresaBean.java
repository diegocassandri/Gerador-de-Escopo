
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

import br.com.prodama.controller.UsuarioLogin;
import br.com.prodama.enun.TipoEmpresa;
import br.com.prodama.model.cadastro.Cidade;
import br.com.prodama.model.cadastro.Empresa;
import br.com.prodama.model.cadastro.Estado;
import br.com.prodama.model.cadastro.Filial;
import br.com.prodama.repository.cadastros.Cidades;
import br.com.prodama.repository.cadastros.Empresas;
import br.com.prodama.repository.cadastros.Estados;
import br.com.prodama.service.cadastro.CadastroEmpresa;
import br.com.prodama.service.cadastro.CadastroFilial;
import br.com.prodama.util.FacesMessages;

@Named
@ViewScoped
public class CadastroEmpresaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private FacesMessages messages;

	@Inject
	private CadastroEmpresa cadastroEmpresa;

	@Inject
	private CadastroFilial cadastroFilial;

	@Inject
	private Empresas empresas;

	@Inject
	private Estados estados;

	@Inject
	private Cidades cidades;

	@Inject
	private UsuarioLogin usuarioLogin;

	private TipoEmpresa tipoEmpresa;
	private Empresa empresaEdicao = new Empresa();
	private Empresa empresaSelecionado;
	private Filial filialEdicao = new Filial();
	private Estado estadoSelecionado;
	private List<Empresa> todasEmpresas;
	private List<Estado> todosEstados;
	private List<Cidade> todasCidades;
	private List<Empresa> filtroEmpresas;

	@PostConstruct
	public void prepararNovoCadastro() {
		empresaEdicao = new Empresa();
		filialEdicao = new Filial();
		todasCidades = cidades.todos();
		todosEstados = estados.todos();
	}

	public void salvar() {
		try {
			if ((empresaEdicao.getCodigo() == null || empresaEdicao.getCodigo() == 0)) {
				empresaEdicao.setCodigoUsuarioInclusao(usuarioLogin.getUsuarioLogin());
			} else {
				empresaEdicao.setCodigoUsuarioAlteracao(usuarioLogin.getUsuarioLogin());
			}
			this.cadastroEmpresa.salvar(empresaEdicao);
			consultar();
			todosEstados = estados.todos();
			if (empresaEdicao.getCodigoUsuarioAlteracao() == null) {
				/*empresaEdicao = empresas.pesquisaPorId(empresaEdicao.getCodigo());*/
				filialEdicao.setBairro(empresaEdicao.getBairro());
				filialEdicao.setCelular(empresaEdicao.getCelular());
				filialEdicao.setCep(empresaEdicao.getCep());
				filialEdicao.setCidade(empresaEdicao.getCidade());
				filialEdicao.setCnpj(empresaEdicao.getCpf());
				filialEdicao.setCodigoUsuarioAlteracao(empresaEdicao.getCodigoUsuarioAlteracao());
				filialEdicao.setCodigoUsuarioInclusao(empresaEdicao.getCodigoUsuarioInclusao());
				filialEdicao.setComplemento(empresaEdicao.getComplemento());
				filialEdicao.setCpf(empresaEdicao.getCpf());
				filialEdicao.setEmail(empresaEdicao.getEmail());
				System.out.println("-------" + empresaEdicao);
				filialEdicao.setEmpresa(empresaEdicao);
				filialEdicao.setEndereco(empresaEdicao.getEndereco());
				filialEdicao.setEstado(empresaEdicao.getEstado());
				filialEdicao.setFantasia(empresaEdicao.getFantasia());
				filialEdicao.setFax(empresaEdicao.getFax());
				filialEdicao.setIncricaoEstadual(empresaEdicao.getIncricaoEstadual());
				filialEdicao.setNumero(empresaEdicao.getNumero());
				filialEdicao.setRazaoSocial(empresaEdicao.getRazaoSocial());
				filialEdicao.setTelefone(empresaEdicao.getTelefone());
				filialEdicao.setTipoEmpresa(empresaEdicao.getTipoEmpresa());

				this.cadastroFilial.salvar(filialEdicao);
			}
			messages.info("Empresa salva com sucesso!");
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:empresa-table"));

		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao salvar Empresa! \n Motivo:" + mensagem.getDetail());
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:empresa-table"));

		}

	}

	public void listaCidades(AjaxBehaviorEvent event) {
		todasCidades = cidades.CidadesPorEstado(this.empresaEdicao.getEstado());
	}

	public void excluir() {
		try {
			this.cadastroEmpresa.excluir(empresaSelecionado);
			empresaSelecionado = null;
			consultar();
			todosEstados = estados.todos();
			messages.info("Empresa excluída com sucesso!");
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao excluir Empresa! \n Motivo:" + mensagem);
		}

	}

	public void consultar() {
		todasEmpresas = empresas.todos();
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

	public Empresas getEmpresas() {
		return empresas;
	}

	public void setEmpresas(Empresas empresas) {
		this.empresas = empresas;
	}

	public Empresa getEmpresaEdicao() {
		return empresaEdicao;
	}

	public void setEmpresaEdicao(Empresa EmpresaEdicao) {
		this.empresaEdicao = EmpresaEdicao;
	}

	public Empresa getEmpresaSelecionado() {
		return empresaSelecionado;
	}

	public void setEmpresaSelecionado(Empresa empresaSelecionado) {
		this.empresaSelecionado = empresaSelecionado;
	}

	public List<Empresa> getTodasEmpresas() {
		return todasEmpresas;
	}

	public void setTodasEmpresas(List<Empresa> todasEmpresas) {
		this.todasEmpresas = todasEmpresas;
	}

	public List<Empresa> getFiltroEmpresas() {
		return filtroEmpresas;
	}

	public void setFiltroEmpresas(List<Empresa> filtroEmpresas) {
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
