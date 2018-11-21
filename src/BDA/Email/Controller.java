package BDA.Email;

import java.util.HashMap;
import java.util.Map;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.swing.event.ChangeListener;

import org.w3c.dom.Node;

import BDA.FuncoesGerais;
import BDA.XMLclass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
 * Aplicação agregadora de conteúdos académicos: canal do Email
 *
 */

/**
 * @author hugo
 *
 */
public class Controller {

	/**
	 * ListView (biblioteca Javafx)
	 */
	@FXML
	private ListView<String> emailsList;

	@FXML
	private TextField pesquisa;
	
	@FXML
	private Button responder;

	@FXML
	private TextField user;

	@FXML
	private TextArea conteudo;
	
	
	Email email = new Email();

	/**
	 * Procedimento que adiciona emails à lista cada vez que esta é clicada com o rato (biblioteca Javafx)
	 * @param event 
	 */
	@FXML
	public void getEmailsList_Clicked(MouseEvent event){
		email.getTimeline(emailsList);
	}
	
	@FXML
	public void initialize() {
		email.getTimeline(emailsList);
		Node emailConfig = XMLclass.getElement(XMLclass.configFile, "email");
		user.setText(emailConfig.getAttributes().getNamedItem("UserName").getNodeValue());

	}
	
	@FXML
	private void filter(ActionEvent event) {
		email.filter(emailsList,pesquisa.getText());
	
	}
	
	@FXML
	private void sent(ActionEvent event) {
		Email.setFolder("Sent");
		responder.setVisible(false);
		email.getTimeline(emailsList);
	}
	
	@FXML
	private void inbox(ActionEvent event) {
		Email.setFolder("INBOX");
		email.getTimeline(emailsList);
	}

	@FXML
	private void sair(ActionEvent event) {
		FuncoesGerais.mudarVistaFXML(event, getClass().getResource("./../mainWindow.fxml"));
	}
	
	@FXML
	private void logout(MouseEvent event) {
		FuncoesGerais.mudarVistaFXML(event, getClass().getResource("loginEmail.fxml"));
	}
	
	@FXML
	private void responder(MouseEvent event) {
		String s=emailsList.getSelectionModel().getSelectedItem();
		String[] parts = s.split(":");
		String to =parts[1].trim();
		System.out.println();
		Email.setTo(to.substring(0, to.length() - 9));
		FuncoesGerais.mudarVistaFXML(event, getClass().getResource("novaMensagem.fxml"));
		
		
	}

	@FXML
	protected void selection(MouseEvent event) {
		conteudo.setText(emailsList.getSelectionModel().getSelectedItem().toString());
		responder.setVisible(true);
	}

	@FXML
	protected void novaMensagem(MouseEvent event) {
		FuncoesGerais.mudarVistaFXML(event, getClass().getResource("novaMensagem.fxml"));
	}
}
