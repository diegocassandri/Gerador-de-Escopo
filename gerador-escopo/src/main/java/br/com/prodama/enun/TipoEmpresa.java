package br.com.prodama.enun;

public enum TipoEmpresa {
	
	FISICA ("Física"),
    JURIDICA ("Jurídica");

	private String descricao;

	TipoEmpresa(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}