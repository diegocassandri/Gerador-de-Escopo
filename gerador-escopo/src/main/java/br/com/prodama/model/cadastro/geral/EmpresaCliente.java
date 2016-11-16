package br.com.prodama.model.cadastro.geral;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import br.com.prodama.enun.TipoEmpresa;

@Entity
@Table(name = "EmpresaCliente")
public class EmpresaCliente implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="gen_empresa_cliente") 
    @SequenceGenerator(name="gen_empresa_cliente", sequenceName = "seq_empresa_cliente", initialValue=1, allocationSize=1)
	@Column(name = "codigo", nullable = false)
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
	
	@Column(name = "Endereco", nullable = true, length = 400)
	private String endereco;

	@Column(name = "Numero", nullable = true, length = 20)
	private String numero;

	@Column(name = "Complemento", length = 50)
	private String complemento;

	@Column(name = "Bairro", nullable = true, length = 200)
	private String bairro;

	@Column(name = "CEP", nullable = true, length = 20)
	private String cep;

	@Column(name = "Observacao",length = 3000)
	private String observacao;

	@Column(name = "TipoEmpresa")
	private TipoEmpresa tipoEmpresa;
 
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "empresaCliente")
	private List<FilialCliente> listaFiliais;
	
	@JoinColumn(name = "Cidade", referencedColumnName = "codigo")
	@ManyToOne(optional = true,fetch=FetchType.LAZY)
	private Cidade cidade;
	
	@JoinColumn(name = "Estado", referencedColumnName = "codigo")
	@ManyToOne(optional = true,fetch=FetchType.LAZY)
	private Estado estado;
	
	@JoinColumn(name = "CodigoCliente", referencedColumnName = "codigo")
	@ManyToOne(optional = true,fetch=FetchType.LAZY)
	private Pessoa pessoa;

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

	public TipoEmpresa getTipoEmpresa() {
		return tipoEmpresa;
	}

	public void setTipoEmpresa(TipoEmpresa tipoEmpresa) {
		this.tipoEmpresa = tipoEmpresa;
	}

	public List<FilialCliente> getListaFiliais() {
		return listaFiliais;
	}

	public void setListaFiliais(List<FilialCliente> listaFiliais) {
		this.listaFiliais = listaFiliais;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
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
		EmpresaCliente other = (EmpresaCliente) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	
}
