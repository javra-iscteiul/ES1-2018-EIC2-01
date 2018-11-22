package BDA.Email;


import org.w3c.dom.Node;

import BDA.FuncoesGerais;
import BDA.XMLclass;
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

public class Controller {

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

	/**
	 * Procedimento que adiciona emails à lista cada vez que esta é clicada com o rato (biblioteca Javafx)
	 * @param event MouseEvent
	 */
	@FXML
	public void getEmailsList_Clicked(MouseEvent event){
		emailsList.setItems(email.getTimeline());
	}
	
	/**
	 * Procedimento responsável por iniciar a aplicação com a timeline e user definido
	 */
	@FXML
	public void initialize() {
		emailsList.setItems(email.getTimeline());
		Node emailConfig = XMLclass.getElement(XMLclass.configFile, "email");
		user.setText(emailConfig.getAttributes().getNamedItem("UserName").getNodeValue());

	}
	
	/**
	 * Procedimento que filtra a lista de emails dada uma palavra ou frase
	 * @param event
	 */
	@FXML
	private void filter(ActionEvent event) {
		emailsList.setItems(email.filter(pesquisa.getText()));
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
		emailsList.setItems(email.getTimeline());
	}
	
	/**
	 * Procedimento que altera a pasta selecionada para a caixa de itens recebidos com os respetivos emails
	 * @param event
	 */
	@FXML
	private void inbox(ActionEvent event) {
		conteudo.setText("");
		Email.setFolder("INBOX");
		emailsList.setItems(email.getTimeline());
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
		Mensagem m=emailsList.getSelectionModel().getSelectedItem();
		Email.setTo(m.getFrom_to());
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
		FuncoesGerais.mudarVistaFXML(event, getClass().getResource("novaMensagem.fxml"));
	}
}
