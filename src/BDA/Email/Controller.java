package BDA.Email;

import java.util.HashMap;
import java.util.Map;

import javax.swing.event.ChangeListener;

import org.w3c.dom.Node;

import BDA.FuncoesGerais;
import BDA.XMLclass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import twitter4j.TwitterException;

/**
 * Date: Oct 24 2018
 * @author ES1-2018-EIC2-01
 * @version 1.0
 * Aplica��o agregadora de conte�dos acad�micos: canal do Email
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

	@FXML
	private TextField pesquisa;

	@FXML
	private TextField user;

	@FXML
	private TextArea conteudo;

	/**
	 * Procedimento que adiciona emails � lista cada vez que esta � clicada com o rato (biblioteca Javafx)
	 * @param event 
	 */
	@FXML
	public void getEmailsList_Clicked(MouseEvent event){
		emails.clear();
		emailsList.getItems().clear();
		for(String email : Email.getTimeline()){
			emails.add(email);
		}
		emailsList.setItems(emails);
	}
	
	@FXML
	public void initialize() {
		for (String email : Email.getTimeline()) {
			emails.add(email);
		}
		emailsList.setItems(emails);
		Node emailConfig = XMLclass.getElement(XMLclass.configFile, "email");
		user.setText(emailConfig.getAttributes().getNamedItem("UserName").getNodeValue());

	}
	
	@FXML
	private void filter(ActionEvent event) {
		emails.clear();
		emailsList.getItems().clear();
		for (String email : Email.filter(pesquisa.getText())) {
			emails.add(email);
		}
		emailsList.setItems(emails);
	}

	@FXML
	private void logout(MouseEvent event) {
		FuncoesGerais.mudarVistaFXML(event, getClass().getResource("loginEmail.fxml"));
	}

	@FXML
	protected void selection(MouseEvent event) {
		conteudo.setText(emailsList.getSelectionModel().getSelectedItem().toString());
	}

	@FXML
	protected void novaMensagem(MouseEvent event) {
		FuncoesGerais.mudarVistaFXML(event, getClass().getResource("novaMensagem.fxml"));
	}
}
