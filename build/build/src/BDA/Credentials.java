package BDA;

import org.w3c.dom.NodeList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Date: Oct 22 2018
 * 
 * @author ES1-2018-EIC2-01 
 * @version 1.0 classe das credenciais dos diversos serviços
 */
public class Credentials {
	
	/**
	 * Lista das credenciais do serviço pedido
	 * @param service String
	 * @return ObservableList
	 * @throws Exception e
	 */
	public ObservableList<Credential> getCredentials(String service) throws Exception{
		ObservableList<Credential> credList = FXCollections.observableArrayList();
		NodeList nodeList = XMLclass.getNodeList(XMLclass.configFile, service);
		
		for(int i = 0; i < nodeList.getLength(); i++){
			credList.add(new Credential(nodeList.item(i).getAttributes()));
		}
		
		return credList;
	}
	
}
