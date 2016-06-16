package br.com.prodama.model.cadastro;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "Filial")
public class Filial implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="gen_filial") 
    @SequenceGenerator(name="gen_filial", sequenceName = "filial", initialValue=1, allocationSize=1)
	@Column(name = "Codigo", nullable = false)
	private Long codigo;

	@NotEmpty
	@Basic(optional = false)
	@Column(name = "RazaoSocial", nullable = false, length = 100)
	private String razaoSocial;

	@NotEmpty
	@Column(name = "Fantasia", nullable = false, length = 100)
	private String fantasia;

	@NotEmpty
	@Column(name = "CpfCnpj", length = 18)
	private String cpfCnpj;

	@NotEmpty
	@Column(name = "IncricaoEstadual", length = 18)
	private String incricaoEstadual;

	@NotEmpty
	@Column(name = "Endereco", nullable = true, length = 100)
	private String endereco;

	@NotEmpty
	@Column(name = "Numero", nullable = true, length = 8)
	private String numero;

	@Column(name = "Complemento", length = 50)
	private String complemento;

	@NotEmpty
	@Column(name = "Bairro", nullable = true, length = 200)
	private String bairro;

	@Column(name = "CEP", nullable = true, length = 20)
	private String cep;

	@NotEmpty
	@Column(name = "Telefone", length = 20)
	private String telefone;

	@Column(name = "Fax", length = 20)
	private String fax;

	@Column(name = "Celular", length = 20)
	private String celular;

	@Column(name = "Email", length = 50)
	private String email;

	@NotEmpty
	@JoinColumn(name = "Empresa", referencedColumnName = "codigo")
	@ManyToOne(optional = false)
	private Empresa empresa;

	@NotEmpty
	@JoinColumn(name = "Cidade", referencedColumnName = "codigo")
	@ManyToOne(optional = false)
	private Cidade cidade;

	@JoinColumn(name = "CodigoUsuarioInclusao", referencedColumnName = "Codigo")
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	private Usuario codigoUsuarioInclusao;

	@JoinColumn(name = "CodigoUsuarioAlteracao", referencedColumnName = "Codigo")
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	private Usuario codigoUsuarioAlteracao;

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

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public String getIncricaoEstadual() {
		return incricaoEstadual;
	}

	public void setIncricaoEstadual(String incricaoEstadual) {
		this.incricaoEstadual = incricaoEstadual;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Usuario getCodigoUsuarioInclusao() {
		return codigoUsuarioInclusao;
	}

	public void setCodigoUsuarioInclusao(Usuario codigoUsuarioInclusao) {
		this.codigoUsuarioInclusao = codigoUsuarioInclusao;
	}

	public Usuario getCodigoUsuarioAlteracao() {
		return codigoUsuarioAlteracao;
	}

	public void setCodigoUsuarioAlteracao(Usuario codigoUsuarioAlteracao) {
		this.codigoUsuarioAlteracao = codigoUsuarioAlteracao;
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
		Filial other = (Filial) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	

}
