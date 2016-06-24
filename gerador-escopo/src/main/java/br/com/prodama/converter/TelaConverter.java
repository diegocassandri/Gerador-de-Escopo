package br.com.prodama.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.prodama.model.cadastro.geral.Tela;



@FacesConverter(forClass = Tela.class,value = "telaPickConverter")
public class TelaConverter implements Converter {

	@Inject
	private EntityManager manager;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Tela retorno = null;

		if (value != null) {
			retorno = this.manager.find(Tela.class, Long.valueOf(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((Tela) value).getCodigo();
			String retorno = (codigo == null ? null : codigo.toString());

			return retorno;
		}

		return "";
	}

}
