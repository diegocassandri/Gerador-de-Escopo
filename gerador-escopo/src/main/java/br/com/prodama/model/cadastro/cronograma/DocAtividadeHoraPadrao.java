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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Table(name="DocAtividadeHoraPadrao")
public class DocAtividadeHoraPadrao implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="gen_docAtividadeHoraPadrao") 
    @SequenceGenerator(name="gen_docAtividadeHoraPadrao", sequenceName = "seq_docAtividadeHoraPadrao", initialValue=1, allocationSize=1)
	@Column(name = "Codigo", nullable = false)
	private Long codigo;

	@NotEmpty
	@Basic(optional = false)
	@Column(name = "Descricao", nullable = false, length = 250)
	private String descricao;
	
	@Lob 
	@Basic(optional = true,fetch = FetchType.LAZY)
	@Column(name="Observacao")
	private String observacao;	
	
	@ManyToOne
	@JoinColumn(name = "codigoAtividadeHoraPadrao")
	private AtividadeHoraPadrao atividadeHoraPadrao;
	
	@SuppressWarnings("deprecation")
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "docAtividadeHoraPadrao",cascade=CascadeType.ALL)
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private List<AnexoAtividadeHoraPadrao> listaAnexoAtividadesHorasPadroes;
	

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

	public AtividadeHoraPadrao getAtividadeHoraPadrao() {
		return atividadeHoraPadrao;
	}

	public void setAtividadeHoraPadrao(AtividadeHoraPadrao atividadeHoraPadrao) {
		this.atividadeHoraPadrao = atividadeHoraPadrao;
	}

	public List<AnexoAtividadeHoraPadrao> getListaAnexoAtividadesHorasPadroes() {
		return listaAnexoAtividadesHorasPadroes;
	}

	public void setListaAnexoAtividadesHorasPadroes(List<AnexoAtividadeHoraPadrao> listaAnexoAtividadesHorasPadroes) {
		this.listaAnexoAtividadesHorasPadroes = listaAnexoAtividadesHorasPadroes;
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
		DocAtividadeHoraPadrao other = (DocAtividadeHoraPadrao) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return  descricao;
	}
	
	
	
	
}
