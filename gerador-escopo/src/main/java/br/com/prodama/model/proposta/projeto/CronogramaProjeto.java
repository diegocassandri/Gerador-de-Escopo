package br.com.prodama.model.proposta.projeto;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.prodama.model.cadastro.geral.Empresa;
import br.com.prodama.model.cadastro.geral.EmpresaCliente;
import br.com.prodama.model.cadastro.geral.FaixaColaborador;
import br.com.prodama.model.cadastro.geral.Filial;
import br.com.prodama.model.cadastro.geral.FilialCliente;
import br.com.prodama.model.cadastro.geral.Pessoa;
import br.com.prodama.model.cadastro.produto.Produto;

@Entity
@Table(name = "CronogramaProjeto")
public class CronogramaProjeto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_cronogramaProjeto")
	@SequenceGenerator(name = "gen_cronogramaProjeto", sequenceName = "seq_cronogramaProjeto", initialValue = 1, allocationSize = 1)
	@Column(name = "Codigo", nullable = false)
	private Long codigo;

	@NotEmpty
	@Basic(optional = false)
	@Column(name = "Descricao", nullable = false, length = 250)
	private String descricao;

	@Column(name = "TotalHoras", nullable = true)
	private Long totalHoras;

	@ManyToOne
	@JoinColumn(name = "Empresa")
	private Empresa empresa;
	
	@ManyToOne
	@JoinColumn(name = "Filial")
	private Filial filial;
	
	@ManyToOne
	@JoinColumn(name = "Cliente")
	private Pessoa cliente;
	
		
	@ManyToOne
	@JoinColumn(name = "Produto")
	private Produto produto;
	
	@ManyToOne
	@JoinColumn(name = "FaixaColaborador")
	private FaixaColaborador faixaColaborador;

	/*@SuppressWarnings("deprecation")
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "codigo", cascade = CascadeType.ALL)
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private List<EmpresaCliente> listaEmpresasCliente;
	
	@SuppressWarnings("deprecation")
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "codigo", cascade = CascadeType.ALL)
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private List<FilialCliente> listaFiliaisCliente;*/
	
	@SuppressWarnings("deprecation")
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "cronogramaProjeto", cascade = CascadeType.ALL)
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private List<AtividadeProjeto> listaAtividadesProjeto;

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

	public Long getTotalHoras() {
		return totalHoras;
	}

	public void setTotalHoras(Long totalHoras) {
		this.totalHoras = totalHoras;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<AtividadeProjeto> getListaAtividadesProjeto() {
		return listaAtividadesProjeto;
	}

	public void setListaAtividadesProjeto(List<AtividadeProjeto> listaAtividadesProjeto) {
		this.listaAtividadesProjeto = listaAtividadesProjeto;
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

	public FaixaColaborador getFaixaColaborador() {
		return faixaColaborador;
	}

	public void setFaixaColaborador(FaixaColaborador faixaColaborador) {
		this.faixaColaborador = faixaColaborador;
	}

	/*public List<EmpresaCliente> getListaEmpresasCliente() {
		return listaEmpresasCliente;
	}

	public void setListaEmpresasCliente(List<EmpresaCliente> listaEmpresasCliente) {
		this.listaEmpresasCliente = listaEmpresasCliente;
	}

	public List<FilialCliente> getListaFiliaisCliente() {
		return listaFiliaisCliente;
	}

	public void setListaFiliaisCliente(List<FilialCliente> listaFiliaisCliente) {
		this.listaFiliaisCliente = listaFiliaisCliente;
	}*/

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CronogramaProjeto other = (CronogramaProjeto) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}




}
