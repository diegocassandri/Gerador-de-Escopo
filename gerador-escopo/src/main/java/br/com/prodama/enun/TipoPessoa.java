package br.com.prodama.enun;

public enum TipoPessoa {
	GESTOR ("Gestor de Negócios"),
    PRE_VENDAS ("Pré Vendas"),
    CONSULTOR ("Consultor"),
    CLIENTE ("Cliente");

    private final String descricao;       

    private TipoPessoa(String tipo) {
    	descricao = tipo;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : descricao.equals(otherName);
    }

    public String toString() {
       return this.descricao;
    }

	public String getDescricao() {
		return descricao;
	}
    
    
}
