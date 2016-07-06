package br.com.prodama.util.componentes;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class BuscaCEP {

	protected Webservicecep xmlCep = new Webservicecep();
	
	public Webservicecep Buscar(String cep) {
		if (cep.replace("-", "").replace("_","").length() >= 8) {
			 try {
		            JAXBContext jc = JAXBContext.newInstance(Webservicecep.class);

		            Unmarshaller u = jc.createUnmarshaller();
		            URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep.replace("-", "") + "&formato=xml");
		            xmlCep = (Webservicecep) u.unmarshal(url);
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
		return null;
       
    }

	public Webservicecep getXmlCep() {
		return xmlCep;
	}
	
	
}
