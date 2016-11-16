package br.com.prodama.model.proposta.projeto;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.prodama.enun.AnaliticoSintetico;
import br.com.prodama.enun.FormatoExecucao;
import br.com.prodama.enun.ResponsavelExecucao;
import br.com.prodama.model.cadastro.geral.Equipe;
import br.com.prodama.model.cadastro.geral.NivelEquipe;
import br.com.prodama.model.cadastro.geral.TipoHora;
import br.com.prodama.util.componentes.ConversorHora;

@Entity
@Table(name = "AtividadeProjeto")
public class AtividadeProjeto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Transient
	private ConversorHora conversorHora;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_atividadeProjeto")
	@SequenceGenerator(name = "gen_atividadeProjeto", sequenceName = "seq_atividadeProjeto", initialValue = 1, allocationSize = 1)
	@Column(name = "codigo")
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
	@JoinColumn(name = "CronogramaProjeto")
	private CronogramaProjeto cronogramaProjeto;

	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "codigoEquipe")
	private Equipe equipe;

	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "codigoNivelEquipe")
	private NivelEquipe nivelEquipe;

	
	@ManyToOne
	@JoinColumn(name = "AtividadeHoraPai")
	private AtividadeProjeto atividadeHoraPai;

	@SuppressWarnings("deprecation")
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "atividadeHoraPai", orphanRemoval = true)
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private List<AtividadeProjeto> subAtividadeHoras;
	
	@NotEmpty
	@Basic(optional = false)
	@Column(name = "nivelAtividade", nullable = false)
	private String nivelAtividade;
	
	@SuppressWarnings("deprecation")
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "atividadeProjeto", cascade = CascadeType.ALL, orphanRemoval = true)
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private List<DocAtividadeProjeto> listaDocAtividadeProjetos;

	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "tipoHora")
	private TipoHora tipoHora;
	
	@NotNull
	@ManyToOne(optional = true)
	@JoinColumn(name = "tipoHoraCliente")
	private TipoHoraCliente tipoHoraCliente;
	
	@NotNull
	@ManyToOne(optional = true)
	@JoinColumn(name = "tipoHoraClienteNivelEquipe")
	private TipoHoraClienteNivelEquipe tipoHoraClienteNivelEquipe;
	
	@NotNull 
	@DecimalMin("0")
	@Column(precision = 15, scale = 2, nullable = false)
	private BigDecimal valor;
	
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

	public ConversorHora getConversorHora() {
		return conversorHora;
	}

	public void setConversorHora(ConversorHora conversorHora) {
		this.conversorHora = conversorHora;
	}

	public CronogramaProjeto getCronogramaProjeto() {
		return cronogramaProjeto;
	}

	public void setCronogramaProjeto(CronogramaProjeto cronogramaProjeto) {
		this.cronogramaProjeto = cronogramaProjeto;
	}

	public AtividadeProjeto getAtividadeHoraPai() {
		return atividadeHoraPai;
	}

	public void setAtividadeHoraPai(AtividadeProjeto atividadeHoraPai) {
		this.atividadeHoraPai = atividadeHoraPai;
	}

	public List<AtividadeProjeto> getSubAtividadeHoras() {
		return subAtividadeHoras;
	}

	public void setSubAtividadeHoras(List<AtividadeProjeto> subAtividadeHoras) {
		this.subAtividadeHoras = subAtividadeHoras;
	}

	public List<DocAtividadeProjeto> getListaDocAtividadeProjetos() {
		return listaDocAtividadeProjetos;
	}

	public void setListaDocAtividadeProjetos(List<DocAtividadeProjeto> listaDocAtividadeProjetos) {
		this.listaDocAtividadeProjetos = listaDocAtividadeProjetos;
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


	public TipoHora getTipoHora() {
		return tipoHora;
	}

	public void setTipoHora(TipoHora tipoHora) {
		this.tipoHora = tipoHora;
	}

	public TipoHoraCliente getTipoHoraCliente() {
		return tipoHoraCliente;
	}

	public void setTipoHoraCliente(TipoHoraCliente tipoHoraCliente) {
		this.tipoHoraCliente = tipoHoraCliente;
	}

	public TipoHoraClienteNivelEquipe getTipoHoraClienteNivelEquipe() {
		return tipoHoraClienteNivelEquipe;
	}

	public void setTipoHoraClienteNivelEquipe(TipoHoraClienteNivelEquipe tipoHoraClienteNivelEquipe) {
		this.tipoHoraClienteNivelEquipe = tipoHoraClienteNivelEquipe;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
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
		AtividadeProjeto other = (AtividadeProjeto) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return descricao;
	}



	


}
