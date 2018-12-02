package BDA.Email;


import java.util.Collections;

import org.w3c.dom.Node;

import BDA.Credential;
import BDA.FuncoesGerais;
import BDA.IServiceController;
import BDA.Mensagem;
import BDA.XMLclass;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * Date: Oct 24 2018
 * @author ES1-2018-EIC2-01
 * @version 1.0
 * Aplicação agregadora de conteúdos académicos: controlador do Email
 *
 */

public class Controller implements IServiceController {	
	/**
	 * ListView (biblioteca Javafx)
	 */

	@FXML
	private ListView<Mensagem> emailsList;

	/**
	 * TextField correspondente à palavra ou frase a pesquisar
	 */
	@FXML
	private TextField pesquisa;
	
	/**
	 * Button para responder a uma mensagem
	 */
	@FXML
	private Button responder;

	/**
	 * TextField que indica a conta do utilizador que se encontra a usar a aplicação
	 */
	@FXML
	private TextField user;

	/**
	 * TextArea que apresenta todas as informaçoes da mensagem
	 */
	@FXML
	private TextArea conteudo;
	
	
	/**
	 * Email, instancia da aplicação em uso
	 */
	Email email = new Email();

	public void init(Credential cred) {
		email.init(cred);
		emailsList.setItems(email.getTimeLine());
		user.setText(cred.getUsername());
    }
	
	/**
	 * Procedimento que adiciona emails à lista cada vez que esta é clicada com o rato (biblioteca Javafx)
	 * @param event MouseEvent
	 */
	@FXML
	public void getEmailsList_Clicked(MouseEvent event){
		emailsList.setItems(email.getTimeLine());
	}
	
	/**
	 * Procedimento que filtra a lista de emails dada uma palavra ou frase
	 * @param event
	 */
	@FXML
	private void filter(ActionEvent event) {
		emailsList.setItems(email.setFilter(pesquisa.getText()));
	}
	
	/**
	 * Procedimento que filtra a lista de emails dado um user
	 * @param event
	 */ 
	@FXML
	private void filterUser(ActionEvent event) {
		emailsList.setItems(email.filterUser(pesquisa.getText()));
	}
	
	/**
	 * Procedimento que altera a pasta selecionada para a caixa de itens enviados com os respetivos emails
	 * @param event
	 */
	@FXML
	private void sent(ActionEvent event) {
		conteudo.setText("");
		Email.setFolder("Sent");
		responder.setVisible(false);
		emailsList.setItems(email.getTimeLine());
	}
	
	/**
	 * Procedimento que altera a pasta selecionada para a caixa de itens recebidos com os respetivos emails
	 * @param event
	 */
	@FXML
	private void inbox(ActionEvent event) {
		conteudo.setText("");
		Email.setFolder("INBOX");
		emailsList.setItems(email.getTimeLine());
	}
	/**
	 * Procedimento que ordena emails por data mais recente
	 * @param event
	 */
	@FXML
	private void recent(ActionEvent event) {
		emailsList.setItems(email.getTimeLine());
	}
	/**
	 * Procedimento que ordena emails por data mais antiga
	 * @param event
	 */
	@FXML
	private void older(ActionEvent event) {
		ObservableList<Mensagem> nova =email.getTimeLine();
		Collections.reverse(nova);
		emailsList.setItems(nova);
	}
	/**
	 * Procedimento que devolve apenas os emails das ultimas 24 horas
	 * @param event
	 */
	@FXML
	private void last24h(ActionEvent event) {
		emailsList.setItems(email.getLast24h());
	}

	/**
	 * Procedimento que retorna à janela principal da aplicação
	 * @param event
	 */
	@FXML
	private void sair(ActionEvent event) {
		FuncoesGerais.mudarVistaFXML(event, getClass().getResource("./../mainWindow.fxml"));
	}
	
	/**
	 * Procedimento para mudar a conta em utilização 
	 * @param event
	 */
	@FXML
	private void logout(MouseEvent event) {
		FuncoesGerais.mudarVistaFXML(event, getClass().getResource("loginEmail.fxml"));
	}
	
	/**
	 * Procedimento que mostra a interface de envio de mensagens para a mensagem selecionada
	 * @param event
	 */
	@FXML
	private void responder(MouseEvent event) {
		Mensagem m = emailsList.getSelectionModel().getSelectedItem();
		Email.setTo(m.getUser());
		FuncoesGerais.mudarVistaFXML(event, getClass().getResource("novaMensagem.fxml"));
		
		
	}

	/**
	 * Procedimento que expande a mensagem selecionada para uma TextArea
	 * @param event
	 */
	@FXML
	protected void selection(MouseEvent event) {
		conteudo.setText(emailsList.getSelectionModel().getSelectedItem().toString());
		responder.setVisible(true);
	}

	/**
	 * Procedimento que mostra a interface de envio de mensagens
	 * @param event
	 */
	@FXML
	protected void novaMensagem(MouseEvent event) {
		FuncoesGerais.mudarVistaFromLoginFXML(event, getClass().getResource("novaMensagem.fxml"), email.getCredential());
	}
}
