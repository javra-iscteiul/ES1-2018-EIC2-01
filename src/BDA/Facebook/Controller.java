package BDA.Facebook;

import java.util.Observable;

import BDA.FuncoesGerais;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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

public class Controller {
	
	/**
	 * ObservableList com os posts do facebook
	 */
	private ObservableList<Message> posts = FXCollections.observableArrayList();
	
	/**
	 * TextField correspondente à palavra ou frase a pesquisar
	 */
	@FXML
	private TextField pesquisa;
	
	/**
	 * TextField correspondente à palavra ou frase a pesquisar
	 */
	@FXML
	private TextField filter;
	
	/**
	 * ListView (biblioteca Javafx)
	 */
	@FXML
	private ListView<Message> facebookList;
	
	/**
	 * Procedimento que adiciona posts à timeline (biblioteca Javafx)
	 */
	@FXML
    public void initialize() {
		for(Message post : new Facebook().getTimeLine()){
			posts.add(post);
		}
		facebookList.setItems(posts);
    }
	
	@FXML
	private void filter_clicked(MouseEvent event){
		new Facebook().setFilter(filter.getText());
	}
	
	@FXML
	private void voltar_clicked(MouseEvent event){
		FuncoesGerais.mudarVistaFXML(event, getClass().getResource("./../mainWindow.fxml"));
	}
}
