package br.com.prodama.model.cadastro.produto;

import java.io.Serializable;
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

import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.NotEmpty;
import br.com.prodama.enun.Status;

@Entity
@Table(name = "modulo")
public class Modulo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="gen_modulo") 
    @SequenceGenerator(name="gen_modulo", sequenceName = "seq_modulo", initialValue=1, allocationSize=1)
	private Long codigo;
	
	@NotEmpty
	@Column(nullable = false, length = 50)
	private String descricao;
	
	@Column(nullable = true, length = 3000)
	private String Observacao;

	@Column(nullable = true)
	private Status status;
	
	@ManyToOne
	@JoinColumn(name = "produto")
	private Produto produto;
	
	@SuppressWarnings("deprecation")
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "modulo",cascade=CascadeType.ALL )
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private List<GestaoModulo> listaGestoesModulo;
	
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

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public List<GestaoModulo> getListaGestoesModulo() {
		return listaGestoesModulo;
	}

	public void setListaGestoesModulo(List<GestaoModulo> listaGestoesModulo) {
		this.listaGestoesModulo = listaGestoesModulo;
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
		Modulo other = (Modulo) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
}
