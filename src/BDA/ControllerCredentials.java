package BDA;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ControllerCredentials {
	/**
	 * ListView (biblioteca Javafx) 
	 */
	@FXML
	private ListView<Credential> facebookCredentials;
	
	/**
	 * ListView (biblioteca Javafx) 
	 */
	@FXML
	private ListView<Credential> twitterCredentials;
	
	/**
	 * ListView (biblioteca Javafx) 
	 */
	@FXML
	private ListView<Credential> emailCredentials;
	
	/**
	 * TextField correspondente à palavra ou frase a pesquisar
	 */
	@FXML
	private TextField userNameFacebook;
	
	/**
	 * TextField correspondente à palavra ou frase a pesquisar
	 */
	@FXML
	private TextField passwordFacebook;
	
	/**
	 * TextField correspondente à palavra ou frase a pesquisar
	 */
	@FXML
	private TextField accessTokenFacebook;
	
	/**
	 * TextField correspondente à palavra ou frase a pesquisar
	 */
	@FXML
	private TextField accessTokenSecretFacebook;
	
	/**
	 * TextField correspondente à palavra ou frase a pesquisar
	 */
	@FXML
	private TextField userNameTwitter;
	
	/**
	 * TextField correspondente à palavra ou frase a pesquisar
	 */
	@FXML
	private TextField passwordTwitter;
	
	/**
	 * TextField correspondente à palavra ou frase a pesquisar
	 */
	@FXML
	private TextField consumerKeyTwitter;
	
	/**
	 * TextField correspondente à palavra ou frase a pesquisar
	 */
	@FXML
	private TextField consumerSecretTwitter;
	
	/**
	 * TextField correspondente à palavra ou frase a pesquisar
	 */
	@FXML
	private TextField accessTokenTwitter;
	
	/**
	 * TextField correspondente à palavra ou frase a pesquisar
	 */
	@FXML
	private TextField accessTokenSecretTwitter;
	
	/**
	 * TextField correspondente à palavra ou frase a pesquisar
	 */
	@FXML
	private TextField userNameEmail;
	
	/**
	 * TextField correspondente à palavra ou frase a pesquisar
	 */
	@FXML
	private TextField passwordEmail;
	
	Credentials cred = new Credentials();
	
	/**
	 * Procedimento que adiciona posts à timeline quando a vista e selecionada (biblioteca Javafx)
	 */
	@FXML
    public void initialize() {
		facebookCredentials.setItems(cred.getCredentials("facebook"));
		twitterCredentials.setItems(cred.getCredentials("twitter"));
		emailCredentials.setItems(cred.getCredentials("email"));
    }
	
	/**
	 * Procedimento que volta para a pagina principal (biblioteca Javafx)
	 */
	@FXML
	private void voltar_clicked(MouseEvent event){
		FuncoesGerais.mudarVistaFXML(event, getClass().getResource("./../mainWindow.fxml"));
	}
}
