package br.com.prodama.controller.cadastros;

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

import br.com.prodama.controller.UsuarioLogin;
import br.com.prodama.enun.TipoEmpresa;
import br.com.prodama.model.cadastro.Cidade;
import br.com.prodama.model.cadastro.Empresa;
import br.com.prodama.model.cadastro.Estado;
import br.com.prodama.model.cadastro.Filial;
import br.com.prodama.repository.cadastros.Cidades;
import br.com.prodama.repository.cadastros.Empresas;
import br.com.prodama.repository.cadastros.Estados;
import br.com.prodama.repository.cadastros.Filiais;
import br.com.prodama.service.cadastro.CadastroFilial;
import br.com.prodama.util.FacesMessages;
import br.com.prodama.util.componentes.BuscaCEP;

@Named
@ViewScoped
public class CadastroFilialBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private FacesMessages messages;
	
	@Inject
	private CadastroFilial cadastroFilial;
	
	@Inject
	private Filiais filiais;
	
	@Inject
	private Estados estados;

	@Inject
	private Cidades cidades;
	
	@Inject
	private Empresas empresas;
	
	@Inject
	private UsuarioLogin usuarioLogin;
	
	private TipoEmpresa tipoEmpresa;
	private Filial filialEdicao = new Filial();
	private Filial filialSelecionado;
	private List<Estado> todosEstados;
	private List<Cidade> todasCidades;
	private List<Filial> todasFiliais;
	private List<Empresa> todasEmpresas;
	private BuscaCEP buscarCep = new BuscaCEP();
	
	@PostConstruct
	public void prepararNovoCadastro() {
		filialEdicao = new Filial();
		todasCidades = cidades.todos();
		todosEstados = estados.todos();
		todasEmpresas = empresas.todos();
	}
	
	public void salvar() {
		try {
			if ((filialEdicao.getCodigo() == null || filialEdicao.getCodigo() == 0)) {
				filialEdicao.setCodigoUsuarioInclusao(usuarioLogin.getUsuarioLogin());
			} else {
				filialEdicao.setCodigoUsuarioAlteracao(usuarioLogin.getUsuarioLogin());
			}
			System.out.println("------" + filialEdicao.getEmpresa().getRazaoSocial());
			this.cadastroFilial.salvar(filialEdicao);
			consultar();
			todasEmpresas = empresas.todos();
			todosEstados = estados.todos();
			messages.info("Filial salva com sucesso!");
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:filial-table"));

		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao salvar Filial! \n Motivo:" + mensagem.getDetail());
			RequestContext.getCurrentInstance().update(Arrays.asList("frmCadastro:msgs", "frmCadastro:empresa-table"));

		}

	}
	
	public void buscarCep() {
		if (filialEdicao.getCep().replace("-", "").length() >= 8) {
			buscarCep.Buscar(filialEdicao.getCep().replace("-", ""));
			filialEdicao.setBairro(buscarCep.getXmlCep().getBairro());
			filialEdicao.setEndereco(buscarCep.getXmlCep().getTipo_logradouro()+" " +  buscarCep.getXmlCep().getLogradouro());
			Cidade cidade = cidades.pesquisaPorNome(buscarCep.getXmlCep());
			filialEdicao.setEstado(cidade.getEstado());
			carregarCidades();
			filialEdicao.setCidade(cidade);
		}
	}
	
	private void carregarCidades(){
		todasCidades = cidades.CidadesPorEstado(this.filialEdicao.getEstado());
	}
	
	public void listaCidades(AjaxBehaviorEvent event) {
		todasCidades = cidades.CidadesPorEstado(this.filialEdicao.getEstado());
	}

	public void excluir() {
		try {
			this.cadastroFilial.excluir(filialSelecionado);
			filialSelecionado = null;
			consultar();
			todosEstados = estados.todos();
			todasEmpresas = empresas.todos();
			messages.info("Filial excluída com sucesso!");
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			messages.error("Erro ao excluir Filial! \n Motivo:" + mensagem);
		}

	}

	public void consultar() {
		todasFiliais = filiais.todos();
	}

	public TipoEmpresa[] getTiposEmpresas() {
		return TipoEmpresa.values();
	}

	public Filial getFilialEdicao() {
		return filialEdicao;
	}

	public void setFilialEdicao(Filial filialEdicao) {
		this.filialEdicao = filialEdicao;
	}

	public Filial getFilialSelecionado() {
		return filialSelecionado;
	}

	public void setFilialSelecionado(Filial filialSelecionado) {
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

	public List<Filial> getTodasFiliais() {
		return todasFiliais;
	}

	public void setTodasFiliais(List<Filial> todasFiliais) {
		this.todasFiliais = todasFiliais;
	}

	public TipoEmpresa getTipoEmpresa() {
		return tipoEmpresa;
	}

	public void setTipoEmpresa(TipoEmpresa tipoEmpresa) {
		this.tipoEmpresa = tipoEmpresa;
	}

	public List<Empresa> getTodasEmpresas() {
		return todasEmpresas;
	}

	public void setTodasEmpresas(List<Empresa> todasEmpresas) {
		this.todasEmpresas = todasEmpresas;
	}

	
	

}
