package br.com.prodama.enun;

public enum RegimeTributacao {
	
	REAL ("Lucro Real"),
	PRESUMIDO ("Lucro Presumido"),
	SIMPLES ("Simples Nascional");
	
	private String descricao;

	RegimeTributacao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}
