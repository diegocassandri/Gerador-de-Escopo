package br.com.prodama.util.componentes;

public class teste {

	public static void main(String[] args) {
		CampoHora c1 = new CampoHora();
		CampoHora c2 = new CampoHora();
		CampoHora c3 = new CampoHora();
		c1.setHora(500);
		c2.setHora(600);
		c3.setHora("128:00");
		System.out.println("inicio");
		System.out.println("Texto---");
		System.out.println(c1.getHoraMinuto());
		System.out.println(c2.getHoraMinuto());
		System.out.println(c3.getHoraMinuto());
		System.out.println("minuto ----");
		System.out.println(c1.getTotalMunutos().toString());
		System.out.println(c2.getTotalMunutos().toString());
		System.out.println(c3.getTotalMunutos().toString());
		System.out.println("fim");
	}

}
