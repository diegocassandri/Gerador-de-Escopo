package br.com.prodama.enun;

public enum FormatoExecucao {
	
	PRESENCIAL ("Presencial"),
    REMOTO ("Remoto");

	private String descricao;

	FormatoExecucao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
