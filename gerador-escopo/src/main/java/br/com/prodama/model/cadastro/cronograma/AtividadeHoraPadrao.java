package br.com.prodama.model.cadastro.cronograma;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.prodama.enun.AnaliticoSintetico;
import br.com.prodama.enun.FormatoExecucao;
import br.com.prodama.enun.ResponsavelExecucao;
import br.com.prodama.model.cadastro.geral.Equipe;
import br.com.prodama.model.cadastro.geral.NivelEquipe;
import br.com.prodama.util.componentes.ConversorHora;

@Entity
@Table(name = "AtividadeHoraPadrao")
public class AtividadeHoraPadrao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Transient
	private ConversorHora conversorHora;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_atividadeHoraPadrao")
	@SequenceGenerator(name = "gen_atividadeHoraPadrao", sequenceName = "seq_atividadeHoraPadrao", initialValue = 1, allocationSize = 1)
	@Column(name = "Codigo", nullable = false)
	private Long codigo;

	@NotEmpty
	@Basic(optional = false)
	@Column(name = "Descricao", length = 250)
	private String descricao;


	@Column(name = "Observacao", nullable = true, length = 4000)
	private String observacao;

	@Column(name = "horaAtividade", nullable = true)
	private Integer horaAtividade;

	@Transient
	private String horaString;

	@Enumerated(EnumType.STRING)
	private AnaliticoSintetico analiticoSitetico;

	@Enumerated(EnumType.STRING)
	@Column(name = "FormatoExecucao", nullable = true)
	private FormatoExecucao formatoExecucao;

	@Enumerated(EnumType.STRING)
	@Column(name = "ResponsavelExecucao", nullable = true)
	private ResponsavelExecucao responsavelExecucao;

	@ManyToOne
	@JoinColumn(name = "CronogramaPadrao")
	private CronogramaPadrao cronogramaPadrao;

	@ManyToOne(optional = true)
	@JoinColumn(name = "codigoEquipe")
	private Equipe equipe;

	@ManyToOne(optional = true)
	@JoinColumn(name = "codigoNivelEquipe")
	private NivelEquipe nivelEquipe;

	@ManyToOne
	@JoinColumn(name = "CodigoAgrupamento")
	private AgrupamentoAtividade agrupamento;

	@ManyToOne
	@JoinColumn(name = "AtividadeHoraPai")
	private AtividadeHoraPadrao atividadeHoraPai;

	@SuppressWarnings("deprecation")
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "atividadeHoraPai", orphanRemoval = true)
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private List<AtividadeHoraPadrao> subAtividadeHoras;
	
	@NotEmpty
	@Basic(optional = false)
	@Column(name = "nivelAtividade", nullable = false)
	private String nivelAtividade;
	
	

	@SuppressWarnings("deprecation")
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "atividadeHoraPadrao", cascade = CascadeType.ALL, orphanRemoval = true)
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private List<DocAtividadeHoraPadrao> listaDocAtividadesHorasPadroes;

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

	public Integer getHoraAtividade() {
		return horaAtividade;
	}

	public void setHoraAtividade(Integer horaAtividade) {
		this.horaAtividade = horaAtividade;
	}

	public String getHoraAtividadeForm() {
		conversorHora = new ConversorHora();
		if (horaAtividade != null) {
			return conversorHora.converteMinutoHora(horaAtividade);
		} else {
			return "000:00";
		}

	}

	public void setHoraAtividadeForm(String horaAtividade) {
		this.horaAtividade = conversorHora.converteHoraMinuto(horaAtividade);
	}

	public AnaliticoSintetico getAnaliticoSitetico() {
		return analiticoSitetico;
	}

	public void setAnaliticoSitetico(AnaliticoSintetico analiticoSitetico) {
		this.analiticoSitetico = analiticoSitetico;
	}

	public FormatoExecucao getFormatoExecucao() {
		return formatoExecucao;
	}

	public void setFormatoExecucao(FormatoExecucao formatoExecucao) {
		this.formatoExecucao = formatoExecucao;
	}

	public ResponsavelExecucao getResponsavelExecucao() {
		return responsavelExecucao;
	}

	public void setResponsavelExecucao(ResponsavelExecucao responsavelExecucao) {
		this.responsavelExecucao = responsavelExecucao;
	}

	public CronogramaPadrao getCronogramaPadrao() {
		return cronogramaPadrao;
	}

	public void setCronogramaPadrao(CronogramaPadrao cronogramaPadrao) {
		this.cronogramaPadrao = cronogramaPadrao;
	}

	public Equipe getEquipe() {
		return equipe;
	}

	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}

	public NivelEquipe getNivelEquipe() {
		return nivelEquipe;
	}

	public void setNivelEquipe(NivelEquipe nivelEquipe) {
		this.nivelEquipe = nivelEquipe;
	}

	public AgrupamentoAtividade getAgrupamento() {
		return agrupamento;
	}

	public void setAgrupamento(AgrupamentoAtividade agrupamento) {
		this.agrupamento = agrupamento;
	}

	public AtividadeHoraPadrao getAtividadeHoraPai() {
		return atividadeHoraPai;
	}

	public void setAtividadeHoraPai(AtividadeHoraPadrao atividadeHoraPai) {
		this.atividadeHoraPai = atividadeHoraPai;
	}

	public List<AtividadeHoraPadrao> getSubAtividadeHoras() {
		return subAtividadeHoras;
	}

	public void setSubAtividadeHoras(List<AtividadeHoraPadrao> subAtividadeHoras) {
		this.subAtividadeHoras = subAtividadeHoras;
	}

	public List<DocAtividadeHoraPadrao> getListaDocAtividadesHorasPadroes() {
		return listaDocAtividadesHorasPadroes;
	}

	public void setListaDocAtividadesHorasPadroes(List<DocAtividadeHoraPadrao> listaDocAtividadesHorasPadroes) {
		this.listaDocAtividadesHorasPadroes = listaDocAtividadesHorasPadroes;
	}

	public String getHoraString() {
		return horaString;
	}

	public void setHoraString(String horaString) {
		this.horaString = horaString;
	}
	
	public String getNivelAtividade() {
		return nivelAtividade;
	}

	public void setNivelAtividade(String nivelAtividade) {
		this.nivelAtividade = nivelAtividade;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((atividadeHoraPai == null) ? 0 : atividadeHoraPai.hashCode());
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
		AtividadeHoraPadrao other = (AtividadeHoraPadrao) obj;
		if (atividadeHoraPai == null) {
			if (other.atividadeHoraPai != null)
				return false;
		} else if (!atividadeHoraPai.equals(other.atividadeHoraPai))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return descricao;
	}



	


}
