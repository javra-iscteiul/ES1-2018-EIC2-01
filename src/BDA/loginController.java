package BDA;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * Date: Oct 24 2018
 * @author ES1-2018-EIC2-01
 * @version 1.0
 * Aplica��o agregadora de conte�dos acad�micos: controlador do Login do Email
 *
 */
public class loginController {
	/**
	 * 
	 * TextField do UserName(email) (biblioteca Javafx)
	 */

	@FXML
	private TextField username;

	/**
	 * PasswordField da Password do utilizador (biblioteca Javafx)
	 */

	@FXML
	private PasswordField password;
	
	@FXML
	private Label error;
	
	private String selectedService;
	
	/**
	 * Procedimento que guarda no config.xml as credenciais do email de Login (biblioteca Javafx)
	 * @param event 
	 */
	@FXML
	private void login(ActionEvent event) {
		error.setVisible(false);
		String user = username.getText();
		String pass = password.getText();
		
		Credential cred = new Credential(user, pass);
		if(XMLclass.existsNode(XMLclass.configFile, selectedService, cred)){
			XMLclass.setLogin(XMLclass.configFile, selectedService, cred, XMLclass.Login);
			
			cred = new Credential(XMLclass.getNode(XMLclass.configFile, selectedService, cred).getAttributes());
			
			String fileName = "./" + selectedService + "/" + selectedService + ".fxml";
			FuncoesGerais.mudarVistaFromLoginFXML(event, getClass().getResource(fileName), cred);
		}
		
		error.setVisible(true);
		error.setText("n�o exitem credenciais guardadas para este utilizador!");
	}

	public void login_init(String service){
		this.selectedService = service;
	}
	
	@FXML
	private void voltar_clicked(MouseEvent event){
		FuncoesGerais.mudarVistaFXML(event, getClass().getResource("mainWindow.fxml"));
	}

}
