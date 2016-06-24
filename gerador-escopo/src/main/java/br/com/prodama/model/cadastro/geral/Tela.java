package br.com.prodama.model.cadastro.geral;

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
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "tela")
public class Tela implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="gen_tela") 
    @SequenceGenerator(name="gen_tela", sequenceName = "seq_tela", initialValue=1, allocationSize=1)
	private Long codigo;

	@NotEmpty
	@Column(nullable = false, length = 150)
	private String descricao;

	@NotEmpty
	@Column(nullable = false, length = 150)
	private String url;

	

	@ManyToOne
	@JoinColumn(name = "tela_pai", nullable = true)
	private Tela telaPai;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "tela_pai", updatable = false)
	private List<Tela> telasfilhas;

	@Transient
	private Boolean status;
	
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
	public Tela getTelaPai() {
		return telaPai;
	}

	public void setTelaPai(Tela telaPai) {
		this.telaPai = telaPai;
	}

	public List<Tela> getTelasfilhas() {
		return telasfilhas;
	}

	public void setTelasfilhas(List<Tela> telasfilhas) {
		this.telasfilhas = telasfilhas;
	}


	public void setStatus(Boolean status) {
		this.status = status;
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
		Tela other = (Tela) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
}
