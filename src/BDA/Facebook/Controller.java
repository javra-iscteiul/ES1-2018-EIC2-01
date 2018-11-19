package BDA.Facebook;

import java.util.List;
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
	
	IFacebook facebook = new Facebook();
	
	/**
	 * Procedimento que adiciona posts à timeline (biblioteca Javafx)
	 */
	@FXML
    public void initialize() {
		facebook.getTimeLine(facebookList);
    }
	
	@FXML
	private void filter_clicked(MouseEvent event){
		facebook.setFilter(facebookList, filter.getText());
	}
	
	@FXML
	private void voltar_clicked(MouseEvent event){
		FuncoesGerais.mudarVistaFXML(event, getClass().getResource("./../mainWindow.fxml"));
	}
}
