package br.com.prodama.model.cadastro.produto;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotEmpty;
import br.com.prodama.enun.Status;
import br.com.prodama.model.cadastro.cronograma.CronogramaPadrao;

@Entity
@Table(name = "Produto")
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="gen_produto") 
    @SequenceGenerator(name="gen_produto", sequenceName = "seq_produto", initialValue=1, allocationSize=1)
	private Long codigo;
	
	@NotEmpty
	@Column(nullable = false, length = 50)
	private String descricao;
	
	@Column(nullable = true, length = 3000)
	private String Observacao;

	@Column(nullable = false)
	private Status status;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "produto")
	private List<Modulo> listaModulos;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "produto")
	private List<CronogramaPadrao> listaCronoramasPadroes;
	
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

	public List<Modulo> getListaModulo() {
		return listaModulos;
	}

	public void setListaModulo(List<Modulo> listaModulo) {
		this.listaModulos = listaModulo;
	}

	public List<Modulo> getListaModulos() {
		return listaModulos;
	}

	public void setListaModulos(List<Modulo> listaModulos) {
		this.listaModulos = listaModulos;
	}

	public List<CronogramaPadrao> getListaCronoramasPadroes() {
		return listaCronoramasPadroes;
	}

	public void setListaCronoramasPadroes(List<CronogramaPadrao> listaCronoramasPadroes) {
		this.listaCronoramasPadroes = listaCronoramasPadroes;
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
		Produto other = (Produto) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
}
