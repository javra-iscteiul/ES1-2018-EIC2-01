package BDA.Twitter;

import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import twitter4j.DirectMessage;
import twitter4j.DirectMessageList;

public class Dms_Controller {
	
	App_twitter tt = new App_twitter();
	
	@FXML
	private ListView<String> received;
	
	@FXML
	private ListView<String> sent;
	
	@FXML
	private TextArea mensagem;
	
	
	@FXML
    public void initialize() {
		System.out.println("ola");
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
