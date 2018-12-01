package BDA.Facebook;

import BDA.FuncoesGerais;
import BDA.Mensagem;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

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
	private ListView<Mensagem> facebookList;
	
	
	Facebook facebook = new Facebook();
	
	/**
	 * Procedimento que adiciona posts à timeline quando a vista e selecionada (biblioteca Javafx)
	 */
	@FXML
    public void initialize() {
		facebookList.setItems(facebook.getTimeLine());
    }
	
	/**
	 * Procedimento que filtra os posts da timeline (biblioteca Javafx)
	 */
	@FXML
	private void filter_clicked(MouseEvent event){
		facebookList.setItems(facebook.setFilter(filter.getText()));
	}
	
	/**
	 * Procedimento que volta para a pagina principal (biblioteca Javafx)
	 */
	@FXML
	private void voltar_clicked(MouseEvent event){
		FuncoesGerais.mudarVistaFXML(event, getClass().getResource("./../mainWindow.fxml"));
	}
}
