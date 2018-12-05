package BDA;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
	 * Button editar facebook
	 */
	@FXML
	private Button editFacebookbtn;
	/**
	 * Button apagar facebook
	 */
	@FXML
	private Button deleteFacebookbtn;
	/**
	 * Button editar twitter
	 */
	@FXML
	private Button editTwitterbtn;
	/**
	 * Button apagar twitter
	 */
	@FXML
	private Button deleteTwitterbtn;
	/**
	 * Button editar email
	 */
	@FXML
	private Button editEmailbtn;
	/**
	 * Button apagar email
	 */
	@FXML
	private Button deleteEmailbtn;
	
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
	 * TextField correspondente ao grupo do facebook
	 */
	@FXML
	private TextField groupFacebook;

	/**
	 * TextField correspondente à palavra ou frase a pesquisar
	 */
	@FXML
	private TextField protocolTwitter;

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
	
	/**
	 * Credenciais dos serviços
	 */
	Credentials cred = new Credentials();

	/**
	 * Procedimento que adiciona posts à timeline quando a vista e selecionada
	 * (biblioteca Javafx)
	 * @throws Exception e
	 */
	@FXML
	private void initialize() throws Exception {
		facebook_load();
		twitter_load();
		email_load();
	}

	/**
	 * @throws Exception e
	 * 
	 */
	private void facebook_load() throws Exception {
		facebookCredentials.setItems(cred.getCredentials(XMLclass.facebookService));
		facebookCredentials.getSelectionModel().clearSelection();
		editFacebookbtn.setDisable(true);
		deleteFacebookbtn.setDisable(true);
	}

	/**
	 * @throws Exception e
	 * 
	 */
	private void twitter_load() throws Exception {
		twitterCredentials.setItems(cred.getCredentials(XMLclass.twitterService));
		twitterCredentials.getSelectionModel().clearSelection();
		editTwitterbtn.setDisable(true);
		deleteTwitterbtn.setDisable(true);
	}

	/**
	 * @throws Exception e
	 * 
	 */
	private void email_load() throws Exception {
		emailCredentials.setItems(cred.getCredentials(XMLclass.emailService));
		emailCredentials.getSelectionModel().clearSelection();
		editEmailbtn.setDisable(true);
		deleteEmailbtn.setDisable(true);
	}

	/**
	 * Procedimento que volta para a pagina principal (biblioteca Javafx)
	 * @param event MouseEvent
	 * @throws IOException
	 */
	@FXML
	private void voltar_clicked(MouseEvent event) throws IOException {
		FuncoesGerais.mudarVistaFXML(event, getClass().getResource("mainWindow.fxml"));
	}

	/**
	 * Procedimento que expande a mensagem selecionada para uma TextArea
	 * @param event MouseEvent
	 */
	@FXML
	protected void facebookSelection(MouseEvent event) {
		Credential selectedCredencial = facebookCredentials.getSelectionModel().getSelectedItem();
		userNameFacebook.setText(selectedCredencial.getUsername());
		passwordFacebook.setText(selectedCredencial.getPassword());
		accessTokenFacebook.setText(selectedCredencial.getAccessToken());
		accessTokenSecretFacebook.setText(selectedCredencial.getAccessTokenSecret());
		editFacebookbtn.setDisable(false);
		deleteFacebookbtn.setDisable(false);
	}

	/**
	 * Procedimento que volta para a pagina principal (biblioteca Javafx)
	 * @throws Exception e
	 */
	/**
	 * @param event ActionEvent
	 * @throws Exception e
	 */
	@FXML
	private void addFacebookCredencial_clicked(ActionEvent event) throws Exception {
		Credential addCredencial = getCredencial(XMLclass.facebookService);

		if (!XMLclass.existsNode(XMLclass.configFile, XMLclass.facebookService, addCredencial)) {
			XMLclass.addNode(XMLclass.configFile, XMLclass.facebookService, addCredencial);
			facebook_load();
		}
	}

	/**
	 * Procedimento que volta para a pagina principal (biblioteca Javafx)
	 * @param event ActionEvent
	 * @throws Exception e
	 */
	@FXML
	private void editFacebookCredencial_clicked(ActionEvent event) throws Exception {
		Credential newCredencial = getCredencial(XMLclass.facebookService);
		Credential oldCredencial = facebookCredentials.getSelectionModel().getSelectedItem();

		if (oldCredencial != null) {
			if (XMLclass.existsNode(XMLclass.configFile, XMLclass.facebookService, oldCredencial)) {
				XMLclass.deleteNode(XMLclass.configFile, XMLclass.facebookService, oldCredencial);
				XMLclass.addNode(XMLclass.configFile, XMLclass.facebookService, newCredencial);
				facebook_load();
			}
		}
	}

	/**
	 * Procedimento que volta para a pagina principal (biblioteca Javafx)
	 * @param event ActionEvent
	 * @throws Exception e
	 */
	@FXML
	private void removeFacebookCredencial_clicked(ActionEvent event) throws Exception {
		Credential deleteCredencial = facebookCredentials.getSelectionModel().getSelectedItem();

		if (deleteCredencial != null) {
			if (XMLclass.existsNode(XMLclass.configFile, XMLclass.facebookService, deleteCredencial)) {
				XMLclass.deleteNode(XMLclass.configFile, XMLclass.facebookService, deleteCredencial);
				facebook_load();
			}
		}
	}

	/**
	 * Procedimento que expande a mensagem selecionada para uma TextArea
	 * 
	 * @param event
	 */
	/**
	 * @param event
	 */
	@FXML
	protected void twitterSelection(MouseEvent event) {
		Credential selectedCredencial = twitterCredentials.getSelectionModel().getSelectedItem();
		userNameTwitter.setText(selectedCredencial.getUsername());
		passwordTwitter.setText(selectedCredencial.getPassword());
		consumerKeyTwitter.setText(selectedCredencial.getConsumerKey());
		consumerSecretTwitter.setText(selectedCredencial.getConsumerSecret());
		accessTokenTwitter.setText(selectedCredencial.getAccessToken());
		accessTokenSecretTwitter.setText(selectedCredencial.getAccessTokenSecret());
		editTwitterbtn.setDisable(false);
		deleteTwitterbtn.setDisable(false);
	}

	/**
	 * Procedimento que volta para a pagina principal (biblioteca Javafx)
	 * @throws Exception 
	 */
	/**
	 * @param event
	 * @throws Exception
	 */
	@FXML
	private void addTwitterCredencial_clicked(ActionEvent event) throws Exception {
		Credential addCredencial = getCredencial(XMLclass.twitterService);

		if (!XMLclass.existsNode(XMLclass.configFile, XMLclass.twitterService, addCredencial)) {
			XMLclass.addNode(XMLclass.configFile, XMLclass.twitterService, addCredencial);
			twitter_load();
		}
	}

	/**
	 * Procedimento que volta para a pagina principal (biblioteca Javafx)
	 * @throws Exception 
	 */
	/**
	 * @param event
	 * @throws Exception
	 */
	@FXML
	private void editTwitterCredencial_clicked(ActionEvent event) throws Exception {
		Credential newCredencial = getCredencial(XMLclass.twitterService);
		Credential oldCredencial = twitterCredentials.getSelectionModel().getSelectedItem();

		if (oldCredencial != null) {
			if (XMLclass.existsNode(XMLclass.configFile, XMLclass.twitterService, oldCredencial)) {
				XMLclass.deleteNode(XMLclass.configFile, XMLclass.twitterService, oldCredencial);
				XMLclass.addNode(XMLclass.configFile, XMLclass.twitterService, newCredencial);
				twitter_load();
			}
		}
	}

	/**
	 * Procedimento que volta para a pagina principal (biblioteca Javafx)
	 * @throws Exception 
	 */
	/**
	 * @param event
	 * @throws Exception
	 */
	@FXML
	private void removeTwitterCredencial_clicked(ActionEvent event) throws Exception {
		Credential deleteCredencial = twitterCredentials.getSelectionModel().getSelectedItem();

		if (deleteCredencial != null) {
			if (XMLclass.existsNode(XMLclass.configFile, XMLclass.twitterService, deleteCredencial)) {
				XMLclass.deleteNode(XMLclass.configFile, XMLclass.twitterService, deleteCredencial);
				twitter_load();
			}
		}
	}

	/**
	 * Procedimento que expande a mensagem selecionada para uma TextArea
	 * 
	 * @param event
	 */
	/**
	 * @param event
	 */
	@FXML
	protected void emailSelection(MouseEvent event) {
		Credential selectedCredencial = emailCredentials.getSelectionModel().getSelectedItem();
		userNameEmail.setText(selectedCredencial.getUsername());
		passwordEmail.setText(selectedCredencial.getPassword());
		editEmailbtn.setDisable(false);
		deleteEmailbtn.setDisable(false);
	}

	/**
	 * Procedimento que volta para a pagina principal (biblioteca Javafx)
	 * @param event
	 * @throws Exception
	 */
	@FXML
	private void addEmailCredencial_clicked(ActionEvent event) throws Exception {
		Credential addCredencial = getCredencial(XMLclass.emailService);

		if (!XMLclass.existsNode(XMLclass.configFile, XMLclass.emailService, addCredencial)) {
			XMLclass.addNode(XMLclass.configFile, XMLclass.emailService, addCredencial);
			email_load();
		}

	}

	/**
	 * Procedimento que volta para a pagina principal (biblioteca Javafx)
	 * @throws Exception 
	 */
	/**
	 * @param event
	 * @throws Exception
	 */
	@FXML
	private void editEmailCredencial_clicked(ActionEvent event) throws Exception {
		Credential newCredencial = getCredencial(XMLclass.emailService);
		Credential oldCredencial = emailCredentials.getSelectionModel().getSelectedItem();

		if (oldCredencial != null) {
			if (XMLclass.existsNode(XMLclass.configFile, XMLclass.emailService, oldCredencial)) {
				XMLclass.deleteNode(XMLclass.configFile, XMLclass.emailService, oldCredencial);
				XMLclass.addNode(XMLclass.configFile, XMLclass.emailService, newCredencial);
				email_load();
			}
		}
	}

	/**
	 * Procedimento que volta para a pagina principal (biblioteca Javafx)
	 * @throws Exception 
	 */
	/**
	 * @param event
	 * @throws Exception
	 */
	@FXML
	private void removeEmailCredencial_clicked(ActionEvent event) throws Exception {
		Credential deleteCredencial = emailCredentials.getSelectionModel().getSelectedItem();

		if (deleteCredencial != null) {
			if (XMLclass.existsNode(XMLclass.configFile, XMLclass.emailService, deleteCredencial)) {
				XMLclass.deleteNode(XMLclass.configFile, XMLclass.emailService, deleteCredencial);
				email_load();
			}
		}
	}

	/**
	 * @param service
	 * @return
	 */
	private Credential getCredencial(String service) {
		if (service.equals(XMLclass.facebookService))
			return new Credential(null, this.userNameFacebook.getText(), this.passwordFacebook.getText(), null, null,
					this.accessTokenFacebook.getText(), this.accessTokenSecretFacebook.getText(), this.groupFacebook.getText());
		else if (service.equals(XMLclass.twitterService))
			return new Credential(this.protocolTwitter.getText(), this.userNameTwitter.getText(),
					this.passwordTwitter.getText(), this.consumerKeyTwitter.getText(),
					this.consumerSecretTwitter.getText(), this.accessTokenTwitter.getText(),
					this.accessTokenSecretTwitter.getText());
		else if (service.equals(XMLclass.emailService))
			return new Credential(null, this.userNameEmail.getText(), this.passwordEmail.getText(), null, null, null,
					null);
		else
			return null;
	}
}
