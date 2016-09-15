package br.com.prodama.model.cadastro.geral;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.prodama.model.cadastro.cronograma.AtividadeHoraPadrao;
import br.com.prodama.model.proposta.projeto.TipoHoraCliente;

@Entity
@Table(name = "tipoHora")
public class TipoHora implements Serializable{


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="gen_tipo_hora") 
    @SequenceGenerator(name="gen_tipo_hora", sequenceName = "seq_tipo_hora", initialValue=1, allocationSize=1)
	private Long codigo;
	
	@NotEmpty
	@Column(nullable = false, length = 50)
	private String descricao;
	
	@NotNull 
	@DecimalMin("0")
	@Column(precision = 15, scale = 2, nullable = false)
	private BigDecimal valor;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataIniclusao;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAlteracao;
	
	@JoinColumn(name = "CodigoUsuarioInclusao", referencedColumnName = "codigo")
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	private Usuario codigoUsuarioInclusao;

	@JoinColumn(name = "CodigoUsuarioAlteracao", referencedColumnName = "codigo")
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	private Usuario codigoUsuarioAlteracao;
	
	@SuppressWarnings("deprecation")
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "tipoHora", cascade = CascadeType.ALL)
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private List<TipoHoraCliente> listaTiposHorasCliente;
	
	@SuppressWarnings("deprecation")
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "tipoHora", cascade = CascadeType.ALL)
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private List<AtividadeHoraPadrao> listaAtividadesPadroes;
	
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
	
	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Date getDataIniclusao() {
		return dataIniclusao;
	}

	public void setDataIniclusao(Date dataIniclusao) {
		this.dataIniclusao = dataIniclusao;
	}

	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public Usuario getCodigoUsuarioInclusao() {
		return codigoUsuarioInclusao;
	}

	public void setCodigoUsuarioInclusao(Usuario codigoUsuarioInclusao) {
		this.codigoUsuarioInclusao = codigoUsuarioInclusao;
	}

	public Usuario getCodigoUsuarioAlteracao() {
		return codigoUsuarioAlteracao;
	}

	public void setCodigoUsuarioAlteracao(Usuario codigoUsuarioAlteracao) {
		this.codigoUsuarioAlteracao = codigoUsuarioAlteracao;
	}

	public List<TipoHoraCliente> getListaTiposHorasCliente() {
		return listaTiposHorasCliente;
	}

	public void setListaTiposHorasCliente(List<TipoHoraCliente> listaTiposHorasCliente) {
		this.listaTiposHorasCliente = listaTiposHorasCliente;
	}

	public List<AtividadeHoraPadrao> getListaAtividadesPadroes() {
		return listaAtividadesPadroes;
	}

	public void setListaAtividadesPadroes(List<AtividadeHoraPadrao> listaAtividadesPadroes) {
		this.listaAtividadesPadroes = listaAtividadesPadroes;
	}

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
		TipoHora other = (TipoHora) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	
}
