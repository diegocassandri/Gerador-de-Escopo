package br.com.prodama.util.componentes;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class BuscaCEP {

	protected XmlCEP xmlCep;
	
	public XmlCEP Buscar(String cep) {
        try {
            JAXBContext jc = JAXBContext.newInstance(XmlCEP.class);

            Unmarshaller u = jc.createUnmarshaller();
            URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep.replace("-", "") + "&formato=xml");
            xmlCep = (XmlCEP) u.unmarshal(url);
            if (xmlCep.getCidade().trim().isEmpty()) {
                return null;//Não encontrado
            }
            return xmlCep;
        } catch (JAXBException ex) {
            ex.printStackTrace();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

	public XmlCEP getXmlCep() {
		return xmlCep;
	}
	
	
}
