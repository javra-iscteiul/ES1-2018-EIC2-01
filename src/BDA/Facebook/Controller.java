package BDA.Facebook;

import java.io.IOException;
import java.util.Collections;

import BDA.Credential;
import BDA.FuncoesGerais;
import BDA.IServiceController;
import BDA.Mensagem;
import BDA.XMLclass;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import twitter4j.TwitterException;

/**
 * Date: Oct 22 2018
 * @author ES1-2018-EIC2-01
 * @version 1.0
 * Aplicação agregadora de aplicações: controlador do facebook
 *
 */

public class Controller implements IServiceController {
	
	/**
	 * TextField correspondente à palavra ou frase a filtrar
	 */
	@FXML
	private TextField filter;
	
	/**
	 * TextField correspondente ao utilizador logado
	 */
	@FXML
	private Label user;
	
	/**
	 * ListView da lista de posts do facebook (biblioteca Javafx) 
	 */
	@FXML
	private ListView<Mensagem> facebookList;
	
	/**
	 * Text area da publicação a postar
	 */
	@FXML
	private TextArea publicacao;
	
	/**
	 * Checkbox para ver posts do grupo
	 */
	@FXML
	private CheckBox group;
	
	Facebook facebook = new Facebook();
	
	/**
	 * Procedimento que adiciona posts à timeline quando a vista é selecionada (biblioteca Javafx)
	 * e inicia o objecto facebook com as credenciais da conta logada
	 * @param cred Credencial
	 * @throws Exception 
	 */
    public void init(Credential cred) throws Exception {
    	facebook.init(cred);
    	user.setText(cred.getUsername());
    	facebookList.setItems(facebook.getTimeLine());
    }
	
	/**
	 * Procedimento que filtra os posts da timeline (biblioteca Javafx)
	 * @param event MouseEvent
	 * @throws Exception 
	 */
	@FXML
	private void filter_clicked(ActionEvent event) throws Exception{
		facebookList.setItems(facebook.setFilter(filter.getText()));
	}
	
	/**
	 * Procedimento que filtra os posts da timeline pelo utilizador (biblioteca Javafx)
	 * @param event MouseEvent
	 * @throws Exception 
	 */
	@FXML
	private void filterUser_clicked(ActionEvent event) throws Exception{
		facebookList.setItems(facebook.setUserFilter(filter.getText()));
	}
	
	/**
	 * Procedimento que ordena os posts por data mais recente
	 * @param event MouseEvent
	 * @throws Exception 
	 */
	@FXML
	private void recent(ActionEvent event) throws Exception {
		facebookList.setItems(facebook.getTimeLine());
	}
	
	/**
	 * Procedimento que ordena os posts por data mais antiga
	 * 
	 * @param event MouseEvent
	 * @throws Exception
	 */
	@FXML
	private void older(ActionEvent event) throws Exception {
		ObservableList<Mensagem> nova = facebook.getTimeLine();
		Collections.reverse(nova);
		facebookList.setItems(nova);
	}

	/**
	 * Procedimento que devolve apenas os posts das ultimas 24 horas
	 * @param event MouseEvent
	 * @throws Exception 
	 */
	@FXML
	private void last24h(ActionEvent event) throws Exception {
		facebookList.setItems(facebook.getLast("24h"));
	}

	/**
	 * Procedimento que devolve apenas os posts da ultima semana
	 * @param event MouseEvent
	 * @throws Exception 
	 */
	@FXML
	private void lastWeek(ActionEvent event) throws Exception {
		facebookList.setItems(facebook.getLast("week"));
	}

	/**
	 * Procedimento que devolve apenas os posts do ultimo mes
	 * @param event MouseEvent
	 * @throws Exception 
	 */
	@FXML
	private void lastMonth(ActionEvent event) throws Exception {
		facebookList.setItems(facebook.getLast("month"));
	}
	
	/**
	 * utilizado no evento em que o utilizador clica no botao de publicar
	 * @param event
	 * @throws TwitterException 
	 */
	@FXML 
	private void publish(ActionEvent event) throws Exception {
		if(facebook.publish(publicacao.getText()))
		{
			publicacao.clear();
			facebookList.setItems(facebook.getTimeLine());
		}
	}
	
	/**
	 * Procedimento que filtra a lista de mensagens para remover/adicionar emails
	 * @param event ActionEvent
	 * @throws Exception e 
	 */
	@FXML
	private void group(ActionEvent event) throws Exception {
		facebook.setGroup(group.isSelected());
		facebookList.setItems(facebook.getTimeLine());
	}
	
	/**
	 * Procedimento que volta para a pagina principal (biblioteca Javafx)
	 * @param event MouseEvent
	 * @throws IOException 
	 */
	@FXML
	private void voltar_clicked(MouseEvent event) throws IOException{
		FuncoesGerais.mudarVistaFXML(event, getClass().getResource("./../mainWindow.fxml"));
	}
	
	/**
	 * Procedimento para muda a conta em utilização 
	 * @param event MouseEvent
	 * @throws Exception 
	 */
	@FXML
	private void logout(MouseEvent event) throws Exception {
		XMLclass.setLogin(XMLclass.configFile, XMLclass.facebookService, facebook.getCredential(), XMLclass.Logout);
		FuncoesGerais.mudarVistaParaLoginFXML(event, getClass().getResource("./../login.fxml"), XMLclass.facebookService);
	}
}
