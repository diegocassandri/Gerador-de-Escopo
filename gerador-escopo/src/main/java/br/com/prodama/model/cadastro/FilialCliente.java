package br.com.prodama.model.cadastro;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import br.com.prodama.enun.RegimeTributacao;
import br.com.prodama.enun.TipoEmpresa;


@Entity
@Table(name = "FilialCliente")
public class FilialCliente implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="gen_filial_cliente") 
    @SequenceGenerator(name="gen_filial_cliente", sequenceName = "seq_filial_cliente", initialValue=1, allocationSize=1)
	@Column(name = "Codigo", nullable = false)
	private Long codigo;
	
	@NotEmpty
	@Basic(optional = false)
	@Column(name = "RazaoSocial", nullable = false, length = 250)
	private String razaoSocial;

	@NotEmpty
	@Column(name = "Fantasia", nullable = false, length = 250)
	private String fantasia;
	
	@CNPJ
	@Column(name = "Cnpj", length = 18)
	private String cnpj;
	
	@CPF
	@Column(name = "Cpf", length = 14)
	private String cpf;	

	@Column(name = "TipoEmpresa")
	private TipoEmpresa tipoEmpresa;
	
	@NotNull
	@Column(name = "RegimeTributacao")
	private RegimeTributacao regimeTributacao;
	
	@JoinColumn(name = "EmpresaCliente", referencedColumnName = "codigo")
	@ManyToOne(optional = false)
	
	private EmpresaCliente empresaCliente;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getFantasia() {
		return fantasia;
	}

	public void setFantasia(String fantasia) {
		this.fantasia = fantasia;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public TipoEmpresa getTipoEmpresa() {
		return tipoEmpresa;
	}

	public void setTipoEmpresa(TipoEmpresa tipoEmpresa) {
		this.tipoEmpresa = tipoEmpresa;
	}

	public EmpresaCliente getEmpresaCliente() {
		return empresaCliente;
	}

	public void setEmpresaCliente(EmpresaCliente empresaCliente) {
		this.empresaCliente = empresaCliente;
	}
	
	

	
	public RegimeTributacao getRegimeTributacao() {
		return regimeTributacao;
	}

	public void setRegimeTributacao(RegimeTributacao regimeTributacao) {
		this.regimeTributacao = regimeTributacao;
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
		FilialCliente other = (FilialCliente) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	
}
