package br.com.prodama.model.cadastro.cronograma;

import java.util.List;

import javax.persistence.Basic;
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

@Entity
@Table(name="AgrupamentoAtidade")
public class AgrupamentoAtidade {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="gen_agrupamentoAtidade") 
    @SequenceGenerator(name="gen_agrupamentoAtidade", sequenceName = "seq_agrupamentoAtidade", initialValue=1, allocationSize=1)
	@Column(name = "Codigo", nullable = false)
	private Long codigo;

	@NotEmpty
	@Basic(optional = false)
	@Column(name = "Descricao", nullable = false, length = 250)
	private String descricao;

	@Column(name = "Status")
	private Status status;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "agrupamento")
	private List<AtividadeHoraPadrao> listaEstimativaHoras;
	
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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<AtividadeHoraPadrao> getListaEstimativaHoras() {
		return listaEstimativaHoras;
	}

	public void setListaEstimativaHoras(List<AtividadeHoraPadrao> listaEstimativaHoras) {
		this.listaEstimativaHoras = listaEstimativaHoras;
	}
	
	

}
