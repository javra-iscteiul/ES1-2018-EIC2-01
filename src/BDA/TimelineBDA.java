package BDA;

import BDA.Email.Email;
import BDA.Facebook.Facebook;
import BDA.Twitter.App_twitter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TimelineBDA {
	/**
	 * ObservableList com as mensagens
	 */
	private ObservableList<Mensagem> mensagens = FXCollections.observableArrayList();
	
	/**
	 * Boolean apresentar ou não emails
	 */
	private boolean showEmail =true;
	/**
	 * Boolean apresentar ou não posts
	 */
	private boolean showFacebook =true;
	/**
	 * Boolean apresentar ou não tweets
	 */
	private boolean showTwitter =true;
	
	/**
	 * Serviço do tipo email
	 */
	IService email= new Email();
	/**
	 * Serviço do tipo facebook
	 */
	IService facebook= new Facebook();
	/**
	 * Serviço do tipo twitter
	 */
	IService twitter= new App_twitter(); 
	
	/**
	 * Inicia os serviços caso apresentem login efetuado
	 */
	public TimelineBDA() {
		try{
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
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Devolve a timeline dos serviços caso apresentem login efetuado e o utilizador tenha escolhido visualizar esse serviço
	 * @return ObservableList
	 * @throws Exception e
	 */
	public ObservableList<Mensagem> getTimeLine() throws Exception{
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
	
	/**
	 * Devolve a timeline dos serviços filtrada caso apresentem login efetuado e o utilizador tenha escolhido visualizar esse serviço
	 * @param s String
	 * @return ObservableList
	 * @throws Exception e
	 */
	public ObservableList<Mensagem> setFilter(String s) throws Exception{
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
	
	/**
	 * Devolve apenas a lista de tweets
	 * @return ObservableList
	 * @throws Exception e
	 */
	public ObservableList<Mensagem> getOnlyTwitter() throws Exception{
		mensagens.clear();
		if (XMLclass.existsLogin(XMLclass.configFile, XMLclass.twitterService) && showTwitter) {
			for (Mensagem m: twitter.getTimeLine()){
				mensagens.add(m);
			}
		}
		return mensagens;
		
	}
	
	/**
	 * Devolve apenas a lista de emails
	 * @return ObservableList
	 * @throws Exception e
	 */
	public ObservableList<Mensagem> getOnlyEmail() throws Exception{
		mensagens.clear();
		if (XMLclass.existsLogin(XMLclass.configFile, XMLclass.emailService) && showEmail) {
			for (Mensagem m: email.getTimeLine()){
				mensagens.add(m);
			}
		}
		return mensagens;
		
	}
	
	/**
	 * Devolve apenas a lista de posts
	 * @return ObservableList
	 * @throws Exception e
	 */
	public ObservableList<Mensagem> getOnlyFacebook() throws Exception{
		mensagens.clear();
		if (XMLclass.existsLogin(XMLclass.configFile, XMLclass.facebookService) && showFacebook) {
			for (Mensagem m: facebook.getTimeLine()){
				mensagens.add(m);
			}
		}
		return mensagens;		
	}

	/**
	 * Devolve a indicação se é para apresentar emails
	 * @return boolean
	 */
	public boolean isShowEmail() {
		return showEmail;
	}

	/**
	 * altera a visibilidade do serviço email
	 * @param showEmail boolean
	 */
	public void setShowEmail(boolean showEmail) {
		this.showEmail = showEmail;
	}

	/**
	 * Devolve a indicação se é para apresentar posts
	 * @return boolean
	 */
	public boolean isShowFacebook() {
		return showFacebook;
	}

	/**
	 * altera a visibilidade do serviço facebook
	 * @param showFacebook boolean
	 */
	public void setShowFacebook(boolean showFacebook) {
		this.showFacebook = showFacebook;
	}

	/**
	 * Devolve a indicação se é para apresentar tweets
	 * @return boolean
	 */
	public boolean isShowTwitter() {
		return showTwitter;
	}

	/**
	 * altera a visibilidade do serviço twitter
	 * @param showTwitter boolean
	 */
	public void setShowTwitter(boolean showTwitter) {
		this.showTwitter = showTwitter;
	}
	
	
}

