package BDA.Twitter;

import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import twitter4j.DirectMessage;
import twitter4j.DirectMessageList;

public class Dms_Controller {
	
	/**
	 * Twitter, intancia da aplicacao a ser utilizada
	 */
	App_twitter tt = new App_twitter();
	
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
	@FXML
    public void initialize() {
		
		DirectMessageList messages =tt.getMessageList();
		/*for (DirectMessage message : messages) {
			Date dateCreated = message.getCreatedAt();
        	String dateCreatedString = dateCreated.getDate() + "/" + dateCreated.getMonth() + "/"
					+ (dateCreated.getYear() + 1900);
        	
            if(message.getSender().getName().equals("EsJarh") ){
            	sent.getItems().add("To" + message.getRecipient().getName()+ "\n" + 
            						"date " + dateCreatedString);
            }else {
            	received.getItems().add("From" + message.getSender().getName() + "\n" + 
						"date " + dateCreatedString);
            }
		 }*/
	}
}
