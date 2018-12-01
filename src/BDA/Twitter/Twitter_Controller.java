package BDA.Twitter;

import BDA.FuncoesGerais;
import BDA.Mensagem;
import BDA.XMLclass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import twitter4j.TwitterException;

public class Twitter_Controller {
	
	/**
	 * Twitter, instancia da aplicacao em uso
	 */
	App_twitter tt = new App_twitter();
	
	/**
	 * ListView com a lista de tweets
	 */
	@FXML
	//private ListView<String> tweetsList;
	private ListView<Mensagem> tweetsList;
	
	/**
	 * Caixa de texto onde utilizador ira escrever os tweets que quer procurar
	 */
	@FXML
	private TextField pesquisa;
	
	/**
	 * Caixa de texto onde o utilizador escreve novas publicacoes
	 */
	@FXML
	private TextArea publicacao;
	
	/**
	 * Funcao responsavel por inicializar o controlador
	 */
	@FXML
    public void initialize() {
		tweetsList.setItems(tt.getTimeLine());
    }
	
	/**
	 * ocorre quando o utilizador clica no botao para atualizar a timeline
	 * @param event
	 * @throws TwitterException
	 */
	@FXML
    private void refresh_timeline_Clicked(MouseEvent event) throws TwitterException
    {
		tweetsList.setItems(tt.getTimeLine());
    }
	
	
	/**
	 * utilizada no evento em que o utilizador pesquisa por tweets com um certo texto
	 * @param event
	 * @throws TwitterException
	 */
	@FXML
    private void filter(ActionEvent event) throws TwitterException 
    {
		System.out.println(pesquisa.getText());
		tweetsList.setItems(tt.setFilter(pesquisa.getText()));
    }
	
	@FXML
    private void filter_users(ActionEvent event) throws TwitterException 
    {
		System.out.println(pesquisa.getText());
		tt.filter_users(pesquisa.getText());
    }
	
	/**
	 * utilizado no evento em que o utilizado clica no botao de postar um novo tweet
	 * @param event
	 */
	@FXML 
	private void post(ActionEvent event) {
		System.out.println(publicacao.getText());
		tt.post(publicacao.getText());
	}
	
	/**
	 * Altera para a interface de mensagens diretas no evento em que o utilizador clica em "mensagens"
	 * @param event
	 */
	@FXML
    private void dms_clicked(MouseEvent event)
    {
		FuncoesGerais.mudarVistaFXML(event, getClass().getResource("./dms.fxml"));
		
    }
	
	
	/**
	 * Procedimento que volta para a pagina principal (biblioteca Javafx)
	 */
	@FXML
	private void voltar_clicked(MouseEvent event){
		FuncoesGerais.mudarVistaFXML(event, getClass().getResource("./../mainWindow.fxml"));
	}
}
