package BDA.Email;

import java.util.HashMap;
import java.util.Map;

import BDA.FuncoesGerais;
import BDA.XMLclass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * Date: Oct 24 2018
 * @author ES1-2018-EIC2-01
 * @version 1.0
 * Aplicação agregadora de conteúdos académicos: canal do Email
 *
 */

/**
 * @author hugo
 *
 */
public class Controller {
	/**
	 * ObservableList com os emails
	 */
	private ObservableList<String> emails = FXCollections.observableArrayList();

	/**
	 * ListView (biblioteca Javafx)
	 */
	@FXML
	private ListView<String> emailsList;

	/**
	 * TextField do UserName(email) (biblioteca Javafx)
	 */
	@FXML
	private TextField email;

	/**
	 * PasswordField da Password do utilizador (biblioteca Javafx)
	 */
	@FXML
	private PasswordField password;
	
	/**
	 * Procedimento que guarda no config.xml as credenciais do email de Login (biblioteca Javafx)
	 * @param event 
	 */
	@FXML
	private void login(ActionEvent event) {
		String mail = email.getText();
		String pass = password.getText();
		
		if(XMLclass.existsElement("email")){
			
			Map<String, String> Attributes = new HashMap<String, String>();
			Attributes.put("UserName", mail);
			Attributes.put("Password", pass);
			
			//valida se o email e a password sao iguais aos que estao no config
			if(!XMLclass.verifyElementAttributesUnchanged("email", Attributes)){
				XMLclass.deleteElement("email");
				XMLclass.addElement("email", "", mail, pass, "", "", "", "");
			}
			
		}else{
			XMLclass.addElement("email", "", mail, pass, "", "", "", "");
		}
		
		FuncoesGerais.mudarVistaFXML(event, getClass().getResource("email.fxml"));
	}

	/**
	 * Procedimento que adiciona emails à lista cada vez que esta é clicada com o rato (biblioteca Javafx)
	 * @param event 
	 */
	@FXML
	private void getEmailsList_Clicked(MouseEvent event){
		for(String email : Email.getTimeline()){
			emails.add(email);
		}
		emailsList.setItems(emails);
	}
}
