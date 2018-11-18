package BDA.Facebook;

import java.util.Observable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * Date: Oct 22 2018
 * @author ES1-2018-EIC2-01
 * @version 1.0
 * Aplica��o agregadora de aplica��es: controlador do facebook
 *
 */

public class Controller {
	
	/**
	 * ObservableList com os posts do facebook
	 */
	private ObservableList<Message> posts = FXCollections.observableArrayList();
	
	/**
	 * TextField correspondente � palavra ou frase a pesquisar
	 */
	@FXML
	private TextField pesquisa;
	
	/**
	 * TextField correspondente � palavra ou frase a pesquisar
	 */
	@FXML
	private TextArea mensagem;
	
	/**
	 * ListView (biblioteca Javafx)
	 */
	@FXML
	private ListView<Message> facebookList;
	
	/**
	 * Procedimento que adiciona posts � timeline cada vez que esta � clicada com o rato (biblioteca Javafx)
	 * @param event 
	 */
	@FXML
    private void timeline_clicked(MouseEvent event){
		for(Message post : new Facebook().getTimeLine()){
			posts.add(post);
		}
		facebookList.setItems(posts);
	}
	
	@FXML
	private void sendMessage_clicked(MouseEvent event){
		new Facebook().sendMessage(mensagem.getText());
	}
}
