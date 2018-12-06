package BDA.Twitter;

import java.io.IOException;

import BDA.Credential;
import BDA.FuncoesGerais;
import BDA.IServiceController;
import BDA.Mensagem;
import BDA.XMLclass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import twitter4j.TwitterException;

public class Twitter_Controller implements IServiceController {
	
	/**
	 * Twitter, instancia da aplicacao em uso
	 */
	App_twitter tt = new App_twitter();
	
	/**
	 * ListView com a lista de tweets
	 */
	@FXML
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
	 * Label com o nome de utilizador
	 */
	@FXML
	private Label user;
	
	public void init(Credential cred) throws Exception {
		tt.init(cred);
		user.setText("@" +cred.getUsername());
		tweetsList.setItems(tt.getTimeLine());
    }
	
	/**
	 * ocorre quando o utilizador clica no botao para atualizar a timeline
	 * @param event
	 * @throws Exception 
	 */
	@FXML
    private void refresh_timeline_Clicked(MouseEvent event) throws Exception
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
		tweetsList.setItems(tt.filter_users(pesquisa.getText()));
    }
	@FXML
	private void lastDay(ActionEvent event) throws TwitterException{
		String type = "lastDay";
		tweetsList.setItems(tt.timeFilter(type));
	}
	@FXML
	private void lastMonth(ActionEvent event) throws TwitterException{
		String type = "lastDay";
		tweetsList.setItems(tt.timeFilter(type));
	}
	
	/**
	 * utilizado no evento em que o utilizado clica no botao de postar um novo tweet
	 * @param event
	 * @throws TwitterException 
	 */
	@FXML 
	private void post(ActionEvent event) throws TwitterException {
		System.out.println(publicacao.getText());
		tt.post(publicacao.getText());
	}
	
	
	
	/**
	 * Procedimento que volta para a pagina principal (biblioteca Javafx)
	 * @throws IOException 
	 */
	@FXML
	private void voltar_clicked(MouseEvent event) throws IOException{
		FuncoesGerais.mudarVistaFXML(event, getClass().getResource("./../mainWindow.fxml"));
	}
	
	@FXML
	protected void selection(MouseEvent event) throws IOException {
		Stage s = new Stage();
		s.setTitle("Tweet");
		FXMLLoader f =  new FXMLLoader();
		f.setLocation(getClass().getResource("TweetPost.fxml"));
		Tweet_Controller tc =  new Tweet_Controller();
		tc.setMensagem((MensagemTwitter) tweetsList.getSelectionModel().getSelectedItem());
		f.setController(tc);
		
		Scene scene = new Scene(f.load());
        s.setScene(scene);
        s.show();
	}
	
	/**
	 * Procedimento para mudar a conta em utilização 
	 * @param event
	 * @throws Exception 
	 */
	@FXML
	private void logout(MouseEvent event) throws Exception {
		XMLclass.setLogin(XMLclass.configFile, XMLclass.twitterService, tt.getCredential(), XMLclass.Logout);
		FuncoesGerais.mudarVistaParaLoginFXML(event, getClass().getResource("./../login.fxml"), XMLclass.twitterService);
	}
}
