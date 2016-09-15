package br.com.prodama.model.cadastro.geral;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import br.com.prodama.enun.TipoEmpresa;
import br.com.prodama.model.proposta.projeto.CronogramaProjeto;

@Entity
@Table(name = "Filial")
public class Filial implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="gen_filial") 
    @SequenceGenerator(name="gen_filial", sequenceName = "seq_filial", initialValue=1, allocationSize=1)
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

	@NotEmpty
	@Column(name = "IncricaoEstadual", length =25)
	private String incricaoEstadual;

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

	@Column(name = "Telefone", length = 20)
	private String telefone;

	@Column(name = "Fax", length = 20)
	private String fax;

	@Column(name = "Celular", length = 20)
	private String celular;

	@Column(name = "Email", length = 50)
	private String email;
	
	@NotNull
	@Column(name = "TipoEmpresa")
	private TipoEmpresa tipoEmpresa;

	@JoinColumn(name = "Empresa", referencedColumnName = "codigo")
	@ManyToOne(optional = false)
	private Empresa empresa;
	
	@JoinColumn(name = "Cidade", referencedColumnName = "codigo")
	@ManyToOne(optional = true,fetch=FetchType.LAZY)
	private Cidade cidade;
	
	@JoinColumn(name = "Estado", referencedColumnName = "codigo")
	@ManyToOne(optional = true,fetch=FetchType.LAZY)
	private Estado estado;

	@JoinColumn(name = "CodigoUsuarioInclusao", referencedColumnName = "codigo")
	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	private Usuario codigoUsuarioInclusao;

	@JoinColumn(name = "CodigoUsuarioAlteracao", referencedColumnName = "codigo")
	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	private Usuario codigoUsuarioAlteracao;

	@ManyToMany(mappedBy="abrangenciaFiliais",cascade=CascadeType.REMOVE,fetch=FetchType.EAGER)
	private List<Usuario> abrangenciaUsuarios = new LinkedList<Usuario>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "filial")
	private List<CronogramaProjeto> listaCronogramas;
	
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

		
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public TipoEmpresa getTipoEmpresa() {
		return tipoEmpresa;
	}

	public void setTipoEmpresa(TipoEmpresa tipoEmpresa) {
		this.tipoEmpresa = tipoEmpresa;
	}
	
	

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<Usuario> getAbrangenciaUsuarios() {
		return abrangenciaUsuarios;
	}

	public void setAbrangenciaUsuarios(List<Usuario> abrangenciaUsuarios) {
		this.abrangenciaUsuarios = abrangenciaUsuarios;
	}

	public List<CronogramaProjeto> getListaCronogramas() {
		return listaCronogramas;
	}

	public void setListaCronogramas(List<CronogramaProjeto> listaCronogramas) {
		this.listaCronogramas = listaCronogramas;
	}

	@Override
	public String toString() {
		return codigo + " "+ razaoSocial;
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