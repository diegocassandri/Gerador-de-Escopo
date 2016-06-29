package br.com.prodama.model.cadastro.produto;

import java.io.Serializable;
import java.util.List;

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
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import br.com.prodama.enun.Status;

@Entity
@Table(name = "gestaoModulo")
public class GestaoModulo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="gen_gestaoModulo") 
    @SequenceGenerator(name="gen_gestaoModulo", sequenceName = "seq_gestaoModulo", initialValue=1, allocationSize=1)
	private Long codigo;
	
	@NotEmpty
	@NotNull
	@Column(nullable = false, length = 50)
	private String descricao;
	
	@Column(nullable = true, length = 3000)
	private String Observacao;

	@Column(nullable = true)
	private Status status;
	
	@ManyToOne
	@JoinColumn(name = "modulo",nullable = false)
	private Modulo modulo;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "gestaoModulo")
	private List<ProcessoGestao> listaProcessosGestoes;

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

	public Modulo getModulo() {
		return modulo;
	}

	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}

	public List<ProcessoGestao> getListaProcessosGestoes() {
		return listaProcessosGestoes;
	}

	public void setListaProcessosGestoes(List<ProcessoGestao> listaProcessosGestoes) {
		this.listaProcessosGestoes = listaProcessosGestoes;
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
		GestaoModulo other = (GestaoModulo) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	
}
