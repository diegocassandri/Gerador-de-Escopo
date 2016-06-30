package br.com.prodama.model.cadastro.cronograma;

import java.util.List;

import javax.persistence.Basic;
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

import br.com.prodama.enun.AnaliticoSintetico;
import br.com.prodama.enun.FormatoExecucao;
import br.com.prodama.enun.ResponsavelExecucao;
import br.com.prodama.model.cadastro.geral.Equipe;
import br.com.prodama.model.cadastro.geral.NivelEquipe;

@Entity
@Table(name="AtividadeHoraPadrao")
public class AtividadeHoraPadrao {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="gen_atividadeHoraPadrao") 
    @SequenceGenerator(name="gen_atividadeHoraPadrao", sequenceName = "seq_atividadeHoraPadrao", initialValue=1, allocationSize=1)
	@Column(name = "Codigo", nullable = false)
	private Long codigo;

	@NotEmpty
	@Basic(optional = false)
	@Column(name = "Descricao", nullable = false, length = 250)
	private String descricao;
	
	@NotEmpty
	@Basic(optional = false)
	@Column(name = "Observacao", nullable = true,length=4000)
	private String observacao;
	
	@NotNull
	@Column(name = "horaAtividade", nullable = true)
	private Long horaAtividade;

	@NotEmpty
	@Column(name = "AnaliticoSintetico", nullable = false)
	private AnaliticoSintetico analiticoSitetico;
	
	@Column(name = "FormatoExecucao", nullable = true)
	private FormatoExecucao formatoExecucao;
	
	@Column(name = "ResponsavelExecucao", nullable = true)
	private ResponsavelExecucao responsavelExecucao;
	
	@ManyToOne(optional=true)
	@JoinColumn(name = "codigoEquipe")
	private Equipe equipe;
	
	@ManyToOne(optional=true)
	@JoinColumn(name = "codigoNivelEquipe")
	private NivelEquipe nivelEquipe;
	
	@ManyToOne
	@JoinColumn(name = "CodigoAgrupamento")
	private AgrupamentoAtidade agrupamento;
	
	@ManyToOne
	@JoinColumn(name = "AtividadeHoraPai")
	private AtividadeHoraPadrao atividadeHoraPai;

	@OneToMany(fetch = FetchType.LAZY,mappedBy = "atividadeHoraPai")
	private List<AtividadeHoraPadrao> subAtividadeHoras;
	
	
	
}
