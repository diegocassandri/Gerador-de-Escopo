package br.com.prodama.model.cadastro.geral;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.prodama.enun.Status;
import br.com.prodama.enun.TipoPessoa;

@Entity
@Table(name = "Pessoa")
public class Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="gen_pessoa") 
    @SequenceGenerator(name="gen_pessoa", sequenceName = "seq_pessoa", initialValue=1, allocationSize=1)
	@Basic(optional = false)
	@Column(name = "Codigo", nullable = false)
	private Long codigo;

	@NotEmpty
	@Column(name = "TipoPessoa", nullable = false, length = 10)
	private TipoPessoa tipoPessoa;

	@NotEmpty
	@Column(name = "NomeRazaoSocial", nullable = false, length = 250)
	private String nomeRazaoSocial;

	@NotEmpty
	@Column(name = "ApelidoFantasia", length = 250)
	private String apelidoFantasia;

	@NotEmpty
	@Column(name = "CpfCnpj", length = 18)
	private String cpfCnpj;

	@NotEmpty
	@Column(name = "RgIe", length = 18)
	private String rgIe;

	@Column(name = "Endereco", nullable = true, length = 100)
	private String endereco;

	@Column(name = "Numero", nullable = true, length = 8)
	private String numero;

	@Column(name = "Complemento", length = 50)
	private String complemento;

	@Column(name = "Bairro", nullable = true, length = 200)
	private String bairro;

	@Column(name = "CEP", nullable = true, length = 20)
	private String cep;

	@Column(name = "Telefone", length = 20)
	private String telefone;

	@Column(name = "Celular", length = 20)
	private String celular;

	@Column(name = "Telefone_2", length = 20)
	private String telefone_2;

	@Column(name = "Telefone_3", length = 20)
	private String telefone_3;

	@Column(name = "Referencias", length = 5000)
	private String referencias;
	
	@NotEmpty
	@Column(name = "Status", length = 7)
	private Status status;

	/* Utilizado pra rotina de custo por consultor X Unidade */
	@NotNull 
	@DecimalMin("0")
	@Column(precision = 15, scale = 2, nullable = false)
	private BigDecimal custoPorHora;
	
	/*Utilizado em casos de clientes com neçociação especifica, cliente atuais*/
	@NotNull 
	@DecimalMin("0")
	@Column(precision = 15, scale = 2, nullable = false)
	private BigDecimal valorHoraCliente;
	
	@JoinColumn(name = "Empresa", referencedColumnName = "codigo")
	@ManyToOne(optional = true,fetch=FetchType.LAZY)
	private Empresa empresa;
	
	@JoinColumn(name = "Filial", referencedColumnName = "codigo")
	@ManyToOne(optional = true,fetch=FetchType.LAZY)
	private Filial filial;
	
	@JoinColumn(name = "Cidade", referencedColumnName = "codigo")
	@ManyToOne(optional = true,fetch=FetchType.LAZY)
	private Cidade cidade;
	
	@JoinColumn(name = "Estado", referencedColumnName = "codigo")
	@ManyToOne(optional = true,fetch=FetchType.LAZY)
	private Estado estado;
	
	@JoinColumn(name = "Equipe", referencedColumnName = "codigo")
	@ManyToOne(optional = true,fetch=FetchType.LAZY)
	private Equipe equipe;
	
	@JoinColumn(name = "NivelEquipe", referencedColumnName = "codigo")
	@ManyToOne(optional = true,fetch=FetchType.LAZY)
	private NivelEquipe nivelEquipe;
	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public String getNomeRazaoSocial() {
		return nomeRazaoSocial;
	}

	public void setNomeRazaoSocial(String nomeRazaoSocial) {
		this.nomeRazaoSocial = nomeRazaoSocial;
	}

	public String getApelidoFantasia() {
		return apelidoFantasia;
	}

	public void setApelidoFantasia(String apelidoFantasia) {
		this.apelidoFantasia = apelidoFantasia;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public String getRgIe() {
		return rgIe;
	}

	public void setRgIe(String rgIe) {
		this.rgIe = rgIe;
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

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getTelefone_2() {
		return telefone_2;
	}

	public void setTelefone_2(String telefone_2) {
		this.telefone_2 = telefone_2;
	}

	public String getTelefone_3() {
		return telefone_3;
	}

	public void setTelefone_3(String telefone_3) {
		this.telefone_3 = telefone_3;
	}

	public String getReferencias() {
		return referencias;
	}

	public void setReferencias(String referencias) {
		this.referencias = referencias;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
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

	public BigDecimal getCustoPorHora() {
		return custoPorHora;
	}

	public void setCustoPorHora(BigDecimal custoPorHora) {
		this.custoPorHora = custoPorHora;
	}

	public BigDecimal getValorHoraCliente() {
		return valorHoraCliente;
	}

	public void setValorHoraCliente(BigDecimal valorHoraCliente) {
		this.valorHoraCliente = valorHoraCliente;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
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
		Pessoa other = (Pessoa) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	
}
