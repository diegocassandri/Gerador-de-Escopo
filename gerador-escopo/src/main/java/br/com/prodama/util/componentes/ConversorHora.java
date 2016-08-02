package br.com.prodama.util.componentes;

import java.io.Serializable;
import java.text.DecimalFormat;

public  class ConversorHora implements Serializable {

	private static final long serialVersionUID = 1L;

	protected String horaTexto;
	protected Integer horaNumero;

	public String getHoraMinuto() {
		return this.horaTexto;
	}

	public String converteMinutoHora(Integer horaBanco) {
		Integer hora;
		double minutos;
		double valorConversao = (Double.parseDouble(horaBanco.toString()) / 60d);
		hora = (int) (valorConversao);
		minutos = (valorConversao - (int) (valorConversao));
		minutos = converterDoubleDoisDecimais((minutos / 100) * 60);
		String sMinutos = Double.toString(minutos).substring(Double.toString(minutos).indexOf(".") + 1,
				Double.toString(minutos).length());
		String sHora = lpad(hora.toString(), "0", 4) + ":" + rpad(sMinutos, "0", 2);
		return this.horaTexto = sHora;
	}
	
	public Integer converteHoraMinuto(String horaInteface) {
		if((horaInteface == null) || (horaInteface.equals(""))){
			return 0;
		}else{
			this.horaTexto = horaInteface;
			return horaNumero  = getTotalMunutos();
		}
		
	}
	
	public Integer getTotalMunutos() {
		String valor = this.horaTexto;
		Integer hora = Integer.parseInt(valor.substring(0, valor.indexOf(":")));
		Integer minutos = Integer.parseInt(valor.substring(valor.indexOf(":") + 1, valor.trim().length()));
		hora = (hora * 60) + minutos;
		return hora;
	}

	public static double converterDoubleDoisDecimais(double precoDouble) {
		DecimalFormat fmt = new DecimalFormat("0.00");
		String string = fmt.format(precoDouble);
		String[] part = string.split("[,]");
		String string2 = part[0] + "." + part[1];
		double preco = Double.parseDouble(string2);
		return preco;
	}

	public static String lpad(String valueToPad, String filler, int size) {
		while (valueToPad.length() < size) {
			valueToPad = filler + valueToPad;
		}
		return valueToPad;
	}

	public static String rpad(String valueToPad, String filler, int size) {
		while (valueToPad.length() < size) {
			valueToPad = valueToPad + filler;
		}
		return valueToPad;
	}
}
