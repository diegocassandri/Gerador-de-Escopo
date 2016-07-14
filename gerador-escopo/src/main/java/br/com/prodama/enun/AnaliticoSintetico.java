package br.com.prodama.enun;

public enum AnaliticoSintetico {
	
	ANALITICO ("Analítico"),
    SINTETICO ("Sintético");

	private String descricao;

	AnaliticoSintetico(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}


}
