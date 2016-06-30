package br.com.prodama.model.cadastro.produto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotEmpty;
import br.com.prodama.enun.Status;

@Entity
@Table(name = "processoGestao")
public class ProcessoGestao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="gen_processoGestao") 
    @SequenceGenerator(name="gen_processoGestao", sequenceName = "seq_processoGestao", initialValue=1, allocationSize=1)
	private Long codigo;
	
	@NotEmpty
	@Column(nullable = false, length = 50)
	private String descricao;
	
	@Column(nullable = true, length = 3000)
	private String Observacao;

	@Column(nullable = true)
	private Status status;
	
	@NotEmpty
	@Column(nullable = false, length = 50)
	private String codigoProprietaria;
	
	@ManyToOne
	@JoinColumn(name = "gestaoModulo")
	private GestaoModulo gestaoModulo;
	
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
		return Observacao;
	}

	public void setObservacao(String observacao) {
		Observacao = observacao;
	}


	
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public GestaoModulo getGestaoModulo() {
		return gestaoModulo;
	}

	public void setGestaoModulo(GestaoModulo gestaoModulo) {
		this.gestaoModulo = gestaoModulo;
	}
	
	

	public String getCodigoProprietaria() {
		return codigoProprietaria;
	}

	public void setCodigoProprietaria(String codigoProprietaria) {
		this.codigoProprietaria = codigoProprietaria;
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
		ProcessoGestao other = (ProcessoGestao) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
}
