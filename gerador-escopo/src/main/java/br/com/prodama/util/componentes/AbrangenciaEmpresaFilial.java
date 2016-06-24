package br.com.prodama.util.componentes;

import br.com.prodama.model.cadastro.geral.Empresa;
import br.com.prodama.model.cadastro.geral.Filial;

public class AbrangenciaEmpresaFilial {
  
	private Integer id; 
	private Empresa empresa;
	private Filial filial;
	
	public AbrangenciaEmpresaFilial(Empresa empresa, Filial filial) {
		super();
		this.empresa = empresa;
		this.filial = filial;
		this.id = Integer.parseInt(empresa.getCodigo()+""+filial.getCodigo());
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		AbrangenciaEmpresaFilial other = (AbrangenciaEmpresaFilial) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
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

		
}
