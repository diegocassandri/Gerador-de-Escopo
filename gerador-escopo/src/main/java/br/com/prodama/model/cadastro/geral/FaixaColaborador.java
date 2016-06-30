package br.com.prodama.model.cadastro.geral;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "faixaColaborador")
public class FaixaColaborador implements Serializable{


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="gen_faixa_colaborador") 
    @SequenceGenerator(name="gen_faixa_colaborador", sequenceName = "seq_faixa_colaborador", initialValue=1, allocationSize=1)
	private Long codigo;
	
	@NotEmpty
	@Column(nullable = false, length = 50)
	private String descricao;
	
	@NotNull
	private Integer indice;

	
	private Integer faixaInicial;

	private Integer faixaFinal;
	
	private String observacao;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataIniclusao;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAlteracao;
	
	@JoinColumn(name = "CodigoUsuarioInclusao", referencedColumnName = "codigo")
	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	private Usuario codigoUsuarioInclusao;

	@JoinColumn(name = "CodigoUsuarioAlteracao", referencedColumnName = "codigo")
	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	private Usuario codigoUsuarioAlteracao;

	
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
	

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Integer getIndice() {
		return indice;
	}

	public void setIndice(Integer indice) {
		this.indice = indice;
	}
	
	public Integer getFaixaInicial() {
		return faixaInicial;
	}

	public void setFaixaInicial(Integer faixaInicial) {
		this.faixaInicial = faixaInicial;
	}

	public Integer getFaixaFinal() {
		return faixaFinal;
	}

	public void setFaixaFinal(Integer faixaFinal) {
		this.faixaFinal = faixaFinal;
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
		FaixaColaborador other = (FaixaColaborador) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	
}
