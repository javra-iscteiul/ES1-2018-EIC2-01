package BDA.Email;

import java.util.HashMap;
import java.util.Map;

import BDA.FuncoesGerais;
import BDA.XMLclass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Date: Oct 24 2018
 * @author ES1-2018-EIC2-01
 * @version 1.0
 * Aplicação agregadora de conteúdos académicos: controlador do Login do Email
 *
 */
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
		
		Map<String, String> attributes = new HashMap<String, String>();
		attributes.put("UserName", mail);
		attributes.put("Password", pass);
		
		if(XMLclass.existsNode(XMLclass.configFile, "email")){			
			
			//valida se o email e a password sao iguais aos que estao no config
			if(!XMLclass.verifyNodeAttributesUnchanged(XMLclass.configFile, "email", attributes)){
				XMLclass.deleteNode(XMLclass.configFile, "email");
				XMLclass.addElement(XMLclass.configFile, "email", attributes);
			}
			
		}else{
			XMLclass.addElement(XMLclass.configFile, "email", attributes);
		}
		
		FuncoesGerais.mudarVistaFXML(event, getClass().getResource("email.fxml"));
	}


}
