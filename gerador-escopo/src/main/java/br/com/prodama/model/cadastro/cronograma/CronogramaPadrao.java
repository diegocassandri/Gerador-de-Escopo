package br.com.prodama.model.cadastro.cronograma;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.prodama.model.cadastro.produto.Produto;

@Entity
@Table(name="CronogramaPradrao")
public class CronogramaPadrao {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="gen_cronogramaPradrao") 
    @SequenceGenerator(name="gen_cronogramaPradrao", sequenceName = "seq_cronogramaPradrao", initialValue=1, allocationSize=1)
	@Column(name = "Codigo", nullable = false)
	private Long codigo;

	@NotEmpty
	@Basic(optional = false)
	@Column(name = "Descricao", nullable = false, length = 250)
	private String descricao;
	
	@Column(name = "TotalHoras", nullable = true)
	private Long totalHoras;
	
	@Column(name = "Produto")
	private Produto produto;
	
	

}
