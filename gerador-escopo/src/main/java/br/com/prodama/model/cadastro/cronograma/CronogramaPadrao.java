package br.com.prodama.model.cadastro.cronograma;

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

import br.com.prodama.model.cadastro.produto.Produto;

@Entity
@Table(name = "CronogramaPradrao")
public class CronogramaPadrao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_cronogramaPradrao")
	@SequenceGenerator(name = "gen_cronogramaPradrao", sequenceName = "seq_cronogramaPradrao", initialValue = 1, allocationSize = 1)
	@Column(name = "Codigo", nullable = false)
	private Long codigo;

	@NotEmpty
	@Basic(optional = false)
	@Column(name = "Descricao", nullable = false, length = 250)
	private String descricao;

	@Column(name = "TotalHoras", nullable = true)
	private Long totalHoras;

	@ManyToOne
	@JoinColumn(name = "produto")
	private Produto produto;

	@SuppressWarnings("deprecation")
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "cronogramaPadrao", cascade = CascadeType.ALL)
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private List<AtividadeHoraPadrao> listaAtividadesHorasPadroes;

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


	public List<AtividadeHoraPadrao> getListaAtividadesHorasPadroes() {
		if (listaAtividadesHorasPadroes != null) {
	
		}
		return listaAtividadesHorasPadroes;
	}

	public void setListaAtividadesHorasPadroes(List<AtividadeHoraPadrao> listaAtividadesHorasPadroes) {
		this.listaAtividadesHorasPadroes = listaAtividadesHorasPadroes;
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
		CronogramaPadrao other = (CronogramaPadrao) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}



}
