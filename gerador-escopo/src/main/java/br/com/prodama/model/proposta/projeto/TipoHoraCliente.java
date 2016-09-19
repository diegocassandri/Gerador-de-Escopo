package br.com.prodama.model.proposta.projeto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import br.com.prodama.model.cadastro.geral.Pessoa;
import br.com.prodama.model.cadastro.geral.TipoHora;

@Entity
@Table(name="TipoHoraCliente")
public class TipoHoraCliente implements Serializable{

	private static final long serialVersionUID = 288492591868646613L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="gen_TipHorCliente") 
    @SequenceGenerator(name="gen_TipHorCliente", sequenceName = "seq_TipHorCliente", initialValue=1, allocationSize=1)
	private Long codigo;
	
	@ManyToOne
	@JoinColumn(name = "Cliente")
	private Pessoa cliente;
	
	@ManyToOne
	@JoinColumn(name = "TipoHora")
	private TipoHora tipoHora;
	
	@NotNull 
	@DecimalMin("0")
	@Column(precision = 15, scale = 2, nullable = false)
	private BigDecimal valor;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Pessoa getCliente() {
		return cliente;
	}

	public void setCliente(Pessoa cliente) {
		this.cliente = cliente;
	}

	public TipoHora getTipoHora() {
		return tipoHora;
	}

	public void setTipoHora(TipoHora tipoHora) {
		this.tipoHora = tipoHora;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
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
		TipoHoraCliente other = (TipoHoraCliente) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	
	
}
