package BDA.Email;

import java.util.HashMap;
import java.util.Map;

import BDA.FuncoesGerais;
import BDA.XMLclass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class loginController {
	/**
	 * 
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


}
