package BDA;

import BDA.Email.Email;
import BDA.Email.MensagemEmail;
import BDA.Facebook.Facebook;
import BDA.Twitter.App_twitter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TimelineBDA {
	/**
	 * ObservableList com as mensagens
	 */
	private ObservableList<Mensagem> mensagens = FXCollections.observableArrayList();
	
	IService email= new Email();
	IService facebook= new Facebook();
	IService twitter= new App_twitter(); 
	
	public ObservableList<Mensagem> getTimeline(){
		
		for (Mensagem m: email.getTimeLine()){
			mensagens.add(m);
		}
		for (Mensagem m: facebook.getTimeLine()){
			mensagens.add(m);
		}
		for (Mensagem m: twitter.getTimeLine()){
			mensagens.add(m);
		}
		return mensagens;
		
	}
	
	public ObservableList<Mensagem> setFilter(String s){
		ObservableList<Mensagem> nova= FXCollections.observableArrayList();
		
		for (Mensagem m: email.setFilter(s)){
			nova.add(m);
		}
		for (Mensagem m: facebook.setFilter(s)){
			nova.add(m);
		}
//		for (Mensagem m: twitter.setFilter(s)){
//			nova.add(m);
//		}
		return nova;
	}
}
