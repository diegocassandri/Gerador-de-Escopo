package br.com.prodama.model.cadastro;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "tela")
public class Tela implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@NotEmpty
	@Column(nullable = false, length = 150)
	private String descricao;

	@NotEmpty
	@Column(nullable = false, length = 150)
	private String url;

	@ManyToMany(mappedBy = "telas", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Grupo> grupos = new LinkedList<Grupo>();

	@ManyToOne
	@JoinColumn(name = "tela_pai", nullable = true)
	private Tela telaPai;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "tela_pai", updatable = false)
	private List<Tela> telasfilhas;

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

	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
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
