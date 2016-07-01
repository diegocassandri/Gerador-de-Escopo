package br.com.prodama.enun;

public enum ResponsavelExecucao {
	
	EMPRESA ("Empresa"),
    CLIENTE ("Cliente");

	private String descricao;

	ResponsavelExecucao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
