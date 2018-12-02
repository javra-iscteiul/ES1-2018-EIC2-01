package BDA;

import org.w3c.dom.NodeList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Credentials {
	
	/**
	 * ObservableList com os posts do facebook
	 */
	private ObservableList<Credential> facebookCredentials = FXCollections.observableArrayList();
	
	/**
	 * ObservableList com os posts do facebook
	 */
	private ObservableList<Credential> twitterCredentials = FXCollections.observableArrayList();
	
	/**
	 * ObservableList com os posts do facebook
	 */
	private ObservableList<Credential> emailCredentials = FXCollections.observableArrayList();
	
	public ObservableList<Credential> getCredentials(String service){
		ObservableList<Credential> credList = FXCollections.observableArrayList();
		NodeList nodeList = XMLclass.getNodeList(XMLclass.configFile, service);
		
		for(int i = 0; i < nodeList.getLength(); i++){
			credList.add(new Credential(nodeList.item(i).getAttributes()));
		}
		
		return credList;
	}
	
}
