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

import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.prodama.model.cadastro.cronograma.AtividadeHoraPadrao;
import br.com.prodama.model.proposta.projeto.TipoHoraClienteNivelEquipe;

@Entity
@Table(name = "nivelEquipe")
public class NivelEquipe implements Serializable{


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="gen_nivel_equipe") 
    @SequenceGenerator(name="gen_nivel_equipe", sequenceName = "seq_nivel_equipe", initialValue=1, allocationSize=1)
	private Long codigo;
	
	@NotEmpty
	@Column(nullable = false, length = 50)
	private String descricao;
	
	@Column(nullable=true,length=1000)
	private String observacao;

	@ManyToOne
	@JoinColumn(name = "equipe")
	private Equipe equipe;
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "nivelEquipe")
	private List<AtividadeHoraPadrao> listaAtidadeHoraPadrao;
	
	@SuppressWarnings("deprecation")
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "nivelEquipe", cascade = CascadeType.ALL)
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private List<TipoHoraClienteNivelEquipe> listaTiposHorasClienteNivelEquipe;
	
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

	
	public Equipe getEquipe() {
		return equipe;
	}

	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}

	public List<AtividadeHoraPadrao> getListaAtidadeHoraPadrao() {
		return listaAtidadeHoraPadrao;
	}

	public void setListaAtidadeHoraPadrao(List<AtividadeHoraPadrao> listaAtidadeHoraPadrao) {
		this.listaAtidadeHoraPadrao = listaAtidadeHoraPadrao;
	}

	public List<TipoHoraClienteNivelEquipe> getListaTiposHorasClienteNivelEquipe() {
		return listaTiposHorasClienteNivelEquipe;
	}

	public void setListaTiposHorasClienteNivelEquipe(List<TipoHoraClienteNivelEquipe> listaTiposHorasClienteNivelEquipe) {
		this.listaTiposHorasClienteNivelEquipe = listaTiposHorasClienteNivelEquipe;
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
		NivelEquipe other = (NivelEquipe) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	
}
