package BDA.Twitter;

import java.util.Date;

import BDA.Credential;
import BDA.IServiceController;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import twitter4j.DirectMessage;
import twitter4j.DirectMessageList;

public class Dms_Controller implements IServiceController {
	
	private Credential emailCredential;
	
	/**
	 * ListView que contem as mensagens recebidas
	 */
	@FXML
	private ListView<String> received;
	
	/**
	 * ListView que contem as mensagens enviadas
	 */
	@FXML
	private ListView<String> sent;
	
	/**
	 * Area de texto onde ira surgir a mensagem que o utlizador selecionar para ler
	 */
	@FXML
	private TextArea mensagem;
	
	
	/**
	 * responsavel por inicializar o controlador, devido a updates recentes no API do twitter o codigo utilizado esta 
	 * desatualizado. Codigo comentado para referencia futura. 
	 */
	@Override
	public void init(Credential cred) {
		emailCredential = cred;
		DirectMessageList messages = App_twitter.getMessageList();
	}
}
