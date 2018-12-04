package BDA;

import org.w3c.dom.NodeList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Credentials {
	
	public ObservableList<Credential> getCredentials(String service) throws Exception{
		ObservableList<Credential> credList = FXCollections.observableArrayList();
		NodeList nodeList = XMLclass.getNodeList(XMLclass.configFile, service);
		
		for(int i = 0; i < nodeList.getLength(); i++){
			credList.add(new Credential(nodeList.item(i).getAttributes()));
		}
		
		return credList;
	}
	
}
