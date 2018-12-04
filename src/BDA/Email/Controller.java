package BDA.Email;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.w3c.dom.Node;

import BDA.Credential;
import BDA.FuncoesGerais;
import BDA.IServiceController;
import BDA.Mensagem;
import BDA.XMLclass;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * Date: Oct 24 2018
 * 
 * @author ES1-2018-EIC2-01
 * @version 1.0 Aplicação agregadora de conteúdos académicos: controlador do
 *          Email
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
	 * TextField que indica a conta do utilizador que se encontra a usar a
	 * aplicação
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

	/* (non-Javadoc)
	 * @see BDA.IServiceController#init(BDA.Credential)
	 */
	public void init(Credential cred) {

		Task<Void> exampleTask = new Task<Void>() {

			@Override
			protected Void call() throws Exception {

				email.init(cred);
				System.out.println("init");
				emailsList.setItems(email.getTimeLine());
				user.setText(cred.getUsername());

				return null;
			}
		};

		new Thread(exampleTask).start();

	}

	/**
	 * Procedimento que adiciona emails à lista cada vez que esta é clicada com
	 * o rato (biblioteca Javafx)
	 * 
	 * @param event MouseEvent
	 *            
	 */
	@FXML
	public void getEmailsList_Clicked(MouseEvent event) {

		Task<Void> exampleTask = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				emailsList.setItems(email.getTimeLine());

				return null;
			}
		};

		new Thread(exampleTask).start();

	}

	/**
	 * Procedimento que filtra a lista de emails dada uma palavra ou frase
	 * 
	 * @param event ActionEvent
	 */
	@FXML
	private void filter(ActionEvent event) {
		Task<Void> exampleTask = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				emailsList.setItems(email.setFilter(pesquisa.getText()));
				return null;
			}
		};

		new Thread(exampleTask).start();

	}

	/**
	 * Procedimento que filtra a lista de emails dado um user
	 * 
	 * @param event ActionEvent
	 * @throws Exception e
	 */
	@FXML
	private void filterUser(ActionEvent event) throws Exception {
		emailsList.setItems(email.filterUser(pesquisa.getText()));
	}

	/**
	 * Procedimento que altera a pasta selecionada para a caixa de itens
	 * enviados com os respetivos emails
	 * 
	 * @param event ActionEvent
	 * @throws Exception e
	 */
	@FXML
	private void sent(ActionEvent event) throws Exception {
		conteudo.setText("");
		Email.setFolder("Sent");
		responder.setVisible(false);
		ObservableList<Mensagem> l = email.getTimeLine();
		emailsList.setItems((ObservableList<Mensagem>) l);
	}

	/**
	 * Procedimento que altera a pasta selecionada para a caixa de itens
	 * recebidos com os respetivos emails
	 * 
	 * @param event ActionEvent
	 * @throws Exception e
	 */
	@FXML
	private void inbox(ActionEvent event) throws Exception {
		conteudo.setText("");
		Email.setFolder("INBOX");
		emailsList.setItems(email.getTimeLine());
	}

	/**
	 * Procedimento que ordena emails por data mais recente
	 * 
	 * @param event ActionEvent
	 * @throws Exception  e
	 */
	@FXML
	private void recent(ActionEvent event) throws Exception {
		emailsList.setItems(email.getTimeLine());
	}

	/**
	 * Procedimento que ordena emails por data mais antiga
	 * 
	 * @param event ActionEvent
	 * @throws Exception e
	 */
	@FXML
	private void older(ActionEvent event) throws Exception {
		ObservableList<Mensagem> nova = email.getTimeLine();
		Collections.reverse(nova);
		emailsList.setItems(nova);
	}

	/**
	 * Procedimento que devolve apenas os emails das ultimas 24 horas
	 * 
	 * @param event ActionEvent
	 * @throws Exception e
	 */
	@FXML
	private void last24h(ActionEvent event) throws Exception {
		emailsList.setItems(email.getLast("24h"));
	}

	/**
	 * Procedimento que devolve apenas os emails da ultima semana
	 * 
	 * @param event ActionEvent
	 * @throws Exception e
	 */
	@FXML
	private void lastWeek(ActionEvent event) throws Exception {
		emailsList.setItems(email.getLast("week"));
	}

	/**
	 * Procedimento que devolve apenas os emails do ultimo mes
	 * 
	 * @param event ActionEvent
	 * @throws Exception e
	 */
	@FXML
	private void lastMonth(ActionEvent event) throws Exception {
		emailsList.setItems(email.getLast("month"));
	}

	/**
	 * Procedimento que retorna à janela principal da aplicação
	 * 
	 * @param event ActionEvent
	 * @throws IOException 
	 */
	@FXML
	private void sair(ActionEvent event) throws IOException {
		FuncoesGerais.mudarVistaFXML(event, getClass().getResource("./../mainWindow.fxml"));
	}

	/**
	 * Procedimento para mudar a conta em utilização
	 * 
	 * @param event ActionEvent
	 * @throws Exception e
	 */
	@FXML
	private void logout(MouseEvent event) throws Exception {
		XMLclass.setLogin(XMLclass.configFile, XMLclass.emailService, email.getCredential(), XMLclass.Logout);
		FuncoesGerais.mudarVistaParaLoginFXML(event, getClass().getResource("./../login.fxml"), XMLclass.emailService);
	}

	/**
	 * Procedimento que mostra a interface de envio de mensagens para a mensagem
	 * selecionada
	 * 
	 * @param event ActionEvent
	 * @throws IOException 
	 */
	@FXML
	private void responder(MouseEvent event) throws Exception {
		Mensagem m = emailsList.getSelectionModel().getSelectedItem();
		Email.setTo(m.getUser());
		FuncoesGerais.mudarVistaFromLoginFXML(event, getClass().getResource("novaMensagem.fxml"),
				email.getCredential());
	}

	/**
	 * Procedimento que expande a mensagem selecionada para uma TextArea
	 * 
	 * @param event ActionEvent
	 */
	@FXML
	protected void selection(MouseEvent event) {
		conteudo.setText(emailsList.getSelectionModel().getSelectedItem().toString());
		responder.setVisible(true);
	}

	/**
	 * Procedimento que mostra a interface de envio de mensagens
	 * 
	 * @param event ActionEvent
	 * @throws IOException 
	 */
	@FXML
	protected void novaMensagem(MouseEvent event) throws Exception {
		FuncoesGerais.mudarVistaFromLoginFXML(event, getClass().getResource("novaMensagem.fxml"),
				email.getCredential());
	}
}
