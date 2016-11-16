package br.com.prodama.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.persistence.EntityManager;


import br.com.prodama.model.cadastro.geral.Filial;

@FacesConverter(forClass = Filial.class)
public class FilialConverter implements Converter {

	@Inject
	private EntityManager manager;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Filial retorno = null;

		if (value != null) {
			retorno = this.manager.find(Filial.class, Long.valueOf(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((Filial) value).getCodigo();
			String retorno = (codigo == null ? null : codigo.toString());

			return retorno;
		}

		return "";
	}

}
