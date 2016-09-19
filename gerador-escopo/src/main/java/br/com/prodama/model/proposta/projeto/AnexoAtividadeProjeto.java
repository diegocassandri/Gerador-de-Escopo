package br.com.prodama.model.proposta.projeto;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Table(name="AnexoAtividadeProjeto")
public class AnexoAtividadeProjeto implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="gen_anexoAtividadeProjeto") 
    @SequenceGenerator(name="gen_anexoAtividadeProjeto", sequenceName = "seq_anexoAtividadeProjeto", initialValue=1, allocationSize=1)
	@Column(name = "Codigo", nullable = false)
	private Long codigo;

	@NotEmpty
	@Basic(optional = false)
	@Column(name = "Descricao", nullable = false, length = 250)
	private String descricao;
	
	@NotNull
	@Lob
	@Column(nullable = false)
	private byte[] arquivo;
	
	@ManyToOne
	@JoinColumn(name = "DocAtividadeprojeto")
	private DocAtividadeProjeto docAtividadeProjeto;
	
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

   	public byte[] getArquivo() {
		return arquivo;
	}

	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
	}

	
	public DocAtividadeProjeto getDocAtividadeProjeto() {
		return docAtividadeProjeto;
	}

	public void setDocAtividadeProjeto(DocAtividadeProjeto docAtividadeProjeto) {
		this.docAtividadeProjeto = docAtividadeProjeto;
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
		AnexoAtividadeProjeto other = (AnexoAtividadeProjeto) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	
	
	
	
}
