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
	
	private boolean showEmail =true;
	private boolean showFacebook =true;
	private boolean showTwitter =true;

	
	
	IService email= new Email();
	IService facebook= new Facebook();
	IService twitter= new App_twitter(); 
	
	TimelineBDA() {
		if (XMLclass.existsLogin(XMLclass.configFile, XMLclass.emailService) && showEmail) {
			Credential emailCred = new Credential(XMLclass.getLogin(XMLclass.configFile, XMLclass.emailService).getAttributes());
			email.init(emailCred);
		}
		if (XMLclass.existsLogin(XMLclass.configFile, XMLclass.facebookService) && showFacebook) {
			Credential facebookCred = new Credential(XMLclass.getLogin(XMLclass.configFile, XMLclass.facebookService).getAttributes());
			facebook.init(facebookCred);
		}
		if (XMLclass.existsLogin(XMLclass.configFile, XMLclass.twitterService) && showTwitter) {
			Credential twitterCred = new Credential(XMLclass.getLogin(XMLclass.configFile, XMLclass.twitterService).getAttributes());
			twitter.init(twitterCred);
		}
	}
	
	public ObservableList<Mensagem> getTimeLine(){
		mensagens.clear();
		if (XMLclass.existsLogin(XMLclass.configFile, XMLclass.emailService) && showEmail) {
			for (Mensagem m: email.getTimeLine()){
				mensagens.add(m);
			}
		}
		if (XMLclass.existsLogin(XMLclass.configFile, XMLclass.facebookService) && showFacebook) {
			for (Mensagem m: facebook.getTimeLine()){
				mensagens.add(m);
			}
		}
		if (XMLclass.existsLogin(XMLclass.configFile, XMLclass.twitterService) && showTwitter) {
			for (Mensagem m: twitter.getTimeLine()){
				mensagens.add(m);
			}
		}
		return mensagens;
		
	}
	
	public ObservableList<Mensagem> setFilter(String s){
		ObservableList<Mensagem> nova= FXCollections.observableArrayList();
		if (XMLclass.existsLogin(XMLclass.configFile, XMLclass.emailService) && showEmail) {
			for (Mensagem m: email.setFilter(s)){
				nova.add(m);
			}
		}
		if (XMLclass.existsLogin(XMLclass.configFile, XMLclass.facebookService) && showFacebook) {
			for (Mensagem m: facebook.setFilter(s)){
				nova.add(m);
			}
		}
		if (XMLclass.existsLogin(XMLclass.configFile, XMLclass.twitterService) && showTwitter) {
			for (Mensagem m: twitter.setFilter(s)){
				nova.add(m);
			}
		}
		return nova;
	}
	
	public ObservableList<Mensagem> getOnlyTwitter(){
		mensagens.clear();
		if (XMLclass.existsLogin(XMLclass.configFile, XMLclass.twitterService) && showTwitter) {
			for (Mensagem m: twitter.getTimeLine()){
				mensagens.add(m);
			}
		}
		return mensagens;
		
	}
	
	public ObservableList<Mensagem> getOnlyEmail(){
		mensagens.clear();
		if (XMLclass.existsLogin(XMLclass.configFile, XMLclass.emailService) && showEmail) {
			for (Mensagem m: email.getTimeLine()){
				mensagens.add(m);
			}
		}
		return mensagens;
		
	}
	
	public ObservableList<Mensagem> getOnlyFacebook(){
		mensagens.clear();
		if (XMLclass.existsLogin(XMLclass.configFile, XMLclass.facebookService) && showFacebook) {
			for (Mensagem m: facebook.getTimeLine()){
				mensagens.add(m);
			}
		}
		return mensagens;		
	}

	public boolean isShowEmail() {
		return showEmail;
	}

	public void setShowEmail(boolean showEmail) {
		this.showEmail = showEmail;
	}

	public boolean isShowFacebook() {
		return showFacebook;
	}

	public void setShowFacebook(boolean showFacebook) {
		this.showFacebook = showFacebook;
	}

	public boolean isShowTwitter() {
		return showTwitter;
	}

	public void setShowTwitter(boolean showTwitter) {
		this.showTwitter = showTwitter;
	}
	
	
}

