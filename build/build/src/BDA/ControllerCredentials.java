package BDA;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * Date: Oct 22 2018
 * 
 * @author ES1-2018-EIC2-01
 * @version 1.0 classe do controller da interface das credenciais
 */
public class ControllerCredentials {
	/**
	 * ListView das credenciais do facebook (biblioteca Javafx)
	 */
	@FXML
	private ListView<Credential> facebookCredentials;

	/**
	 * ListView das credenciais do twitter (biblioteca Javafx)
	 */
	@FXML
	private ListView<Credential> twitterCredentials;

	/**
	 * ListView das credenciais do email (biblioteca Javafx)
	 */
	@FXML
	private ListView<Credential> emailCredentials;
	
	/**
	 * Button editar credencial facebook
	 */
	@FXML
	private Button editFacebookbtn;
	
	/**
	 * Button apagar credencial facebook
	 */
	@FXML
	private Button deleteFacebookbtn;
	
	/**
	 * Button editar credencial twitter
	 */
	@FXML
	private Button editTwitterbtn;
	
	/**
	 * Button apagar credencial twitter
	 */
	@FXML
	private Button deleteTwitterbtn;
	
	/**
	 * Button editar credencial email
	 */
	@FXML
	private Button editEmailbtn;
	
	/**
	 * Button apagar credencial email
	 */
	@FXML
	private Button deleteEmailbtn;
	
	/**
	 * TextField correspondente ao username do facebook a inserir ou editar
	 */
	@FXML
	private TextField userNameFacebook;

	/**
	 * TextField correspondente a password do facebook a inserir ou editar
	 */
	@FXML
	private TextField passwordFacebook;

	/**
	 * TextField correspondente ao accessToken do facebook a inserir ou editar
	 */
	@FXML
	private TextField accessTokenFacebook;

	/**
	 * TextField correspondente ao accessTokenSecret do facebook a inserir ou editar
	 */
	@FXML
	private TextField accessTokenSecretFacebook;
	
	/**
	 * TextField correspondente ao grupo do facebook
	 */
	@FXML
	private TextField groupFacebook;

	/**
	 * TextField correspondente ao protocolo do twitter a inserir ou editar
	 */
	@FXML
	private TextField protocolTwitter;

	/**
	 * TextField correspondente ao username do twitter a inserir ou editar
	 */
	@FXML
	private TextField userNameTwitter;

	/**
	 * TextField correspondente a password do twitter a inserir ou editar
	 */
	@FXML
	private TextField passwordTwitter;

	/**
	 * TextField correspondente a consumerKey do twitter a inserir ou editar
	 */
	@FXML
	private TextField consumerKeyTwitter;

	/**
	 * TextField correspondente a consumerKey do twitter a inserir ou editar
	 */
	@FXML
	private TextField consumerSecretTwitter;

	/**
	 * TextField correspondente ao accessToken do twitter a inserir ou editar
	 */
	@FXML
	private TextField accessTokenTwitter;

	/**
	 * TextField correspondente ao accessTokenSecret do twitter a inserir ou editar
	 */
	@FXML
	private TextField accessTokenSecretTwitter;

	/**
	 * TextField correspondente ao username do email a inserir ou editar
	 */
	@FXML
	private TextField userNameEmail;

	/**
	 * TextField correspondente a password do email a inserir ou editar
	 */
	@FXML
	private TextField passwordEmail;
	
	/**
	 * Button para voltar á pagina inicial
	 */
	@FXML
	private Button voltar;
	
	/**
	 * Credenciais dos serviços
	 */
	Credentials cred = new Credentials();

	/**
	 * Procedimento que adiciona as credenciais as ListViews (biblioteca Javafx)
	 * @throws Exception e
	 */
	@FXML
	private void initialize() throws Exception {
		facebook_load();
		twitter_load();
		email_load();
	}

	/**
	 * Carrega as credenciais do facebook
	 * @throws Exception e
	 */
	private void facebook_load() throws Exception {
		facebookCredentials.setItems(cred.getCredentials(XMLclass.facebookService));
		facebookCredentials.getSelectionModel().clearSelection();
		editFacebookbtn.setDisable(true);
		deleteFacebookbtn.setDisable(true);
	}

	/**
	 * Carrega as credenciais do twitter
	 * @throws Exception e
	 */
	private void twitter_load() throws Exception {
		twitterCredentials.setItems(cred.getCredentials(XMLclass.twitterService));
		twitterCredentials.getSelectionModel().clearSelection();
		editTwitterbtn.setDisable(true);
		deleteTwitterbtn.setDisable(true);
	}

	/**
	 * Carrega as credenciais do email
	 * @throws Exception e
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
	private void voltar(ActionEvent event) throws IOException {
		FuncoesGerais.mudarVistaFXML(event, Main.class.getResource("mainWindow.fxml"));
	}

	/**
	 * Procedimento que ao selecionar o facebook desbloqueia os botões editar e apagar e preenche as combobox's
	 * @param event MouseEvent
	 */
	@FXML
	protected void facebookSelection(MouseEvent event) {
		Credential selectedCredencial = facebookCredentials.getSelectionModel().getSelectedItem();
		userNameFacebook.setText(selectedCredencial.getUsername());
		passwordFacebook.setText(selectedCredencial.getPassword());
		groupFacebook.setText(selectedCredencial.getGroup());
		accessTokenFacebook.setText(selectedCredencial.getAccessToken());
		accessTokenSecretFacebook.setText(selectedCredencial.getAccessTokenSecret());
		editFacebookbtn.setDisable(false);
		deleteFacebookbtn.setDisable(false);
	}

	/**
	 * Procedimento que adiciona uma credencial do facebook (biblioteca Javafx)
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
	 * Procedimento que edita uma credencial do facebook (biblioteca Javafx)
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
	 * Procedimento que remove uma credencial do facebook (biblioteca Javafx)
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
	 * Procedimento que ao selecionar o twitter desbloqueia os botões editar e apagar e preenche as combobox's
	 * @param event MouseEvent
	 */
	@FXML
	protected void twitterSelection(MouseEvent event) {
		Credential selectedCredencial = twitterCredentials.getSelectionModel().getSelectedItem();
		protocolTwitter.setText(selectedCredencial.getProtocolo());
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
	 * Procedimento que adiciona uma credencial do twitter
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
	 * Procedimento que edita uma credencial do twitter (biblioteca Javafx)
	 * @param event ActionEvent
	 * @throws Exception e
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
	 * Procedimento que remove uma credencial do twitter (biblioteca Javafx)
	 * @param event ActionEvent
	 * @throws Exception e
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
	 * Procedimento que ao selecionar o email desbloqueia os botões editar e apagar e preenche as combobox's
	 * @param event ActionEvent
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
	 * Procedimento que adiciona uma credencial do email
	 * @param event ActionEvent
	 * @throws Exception e
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
	 * Procedimento que edita uma credencial do email (biblioteca Javafx)
	 * @param event ActionEvent
	 * @throws Exception e
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
	 * Procedimento que remove uma credencial do email (biblioteca Javafx)
	 * @param event ActionEvent
	 * @throws Exception e
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
	 * Metodo que recebe uma credencial pelas textbox's do serviço selecionado
	 * @param service String
	 * @return Credential
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
