package BDA;

import BDA.Email.Email;
import BDA.Email.MensagemEmail;
import BDA.Facebook.Facebook;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TimelineBDA {
	/**
	 * ObservableList com as mensagens
	 */
	private ObservableList<Mensagem> mensagens = FXCollections.observableArrayList();
	
	public ObservableList<Mensagem> getTimeline(){
		IService email= new Email();
		IService facebook= new Facebook();
		IService twitter= new Twitter();
		
		
		
	}
}
