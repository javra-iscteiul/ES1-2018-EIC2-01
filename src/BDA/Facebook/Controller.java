package BDA.Facebook;

import BDA.Credential;
import BDA.FuncoesGerais;
import BDA.IServiceController;
import BDA.Mensagem;
import BDA.XMLclass;
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

public class Controller implements IServiceController {
	
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
    public void init(Credential cred) {
    	facebook.init(cred);
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
	
	/**
	 * Procedimento para mudar a conta em utilização 
	 * @param event
	 */
	@FXML
	private void logout(MouseEvent event) {
		XMLclass.setLogin(XMLclass.configFile, XMLclass.facebookService, facebook.getCredential(), XMLclass.Logout);
		FuncoesGerais.mudarVistaParaLoginFXML(event, getClass().getResource("./../login.fxml"), XMLclass.facebookService);
	}
}
