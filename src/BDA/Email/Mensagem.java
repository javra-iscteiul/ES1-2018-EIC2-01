package BDA.Email;

import java.util.Date;

/**
 * @author Ana
 *
 */
public class Mensagem {
	/**
	 * String from(caixa de entrada) ou To(caixa de saida)
	 */
	private String from_to;
	/**
	 * data do email
	 */
	private String date;
	/**
	 * assunto do email
	 */
	private String subject;
	/**
	 * conteudo do email
	 */
	private String content;
	
	Email email = new Email();
	
	
	/**
	 * @param from_to
	 * @param subject
	 * @param date
	 * @param content
	 */
	public Mensagem(String from_to, String subject,String date,  String content) {
		this.from_to = from_to;
		this.subject=subject;
		this.date = date;
		this.content = content;
	}


	
	/**
	 * @return String
	 */
	public String getFrom_to() {
		return from_to;
	}



	/**
	 * @param from_to
	 */
	public void setFrom_to(String from_to) {
		this.from_to = from_to;
	}



	/**
	 * @return String
	 */
	public String getDate() {
		return date;
	}



	/**
	 * @param date
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return
	 */
	public String getSubject() {
		return subject;
	}



	/**
	 * @param subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}



	/**
	 * @return
	 */
	public String getContent() {
		return content;
	}



	/**
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString(){
		if(Email.getFolder()=="INBOX"){
			return "From: " + this.from_to + "\r\n" +
					"Subject" + this.subject + "\r\n" + 
					"Date: " + this.date + "\r\n" + 
					"Message: " + this.content;
		}
		if(Email.getFolder()=="Sent"){
			return "To: " + this.from_to + "\r\n" +
					"Subject:" + this.subject + "\r\n" + 
					"Date: " + this.date + "\r\n" + 
					"Message: " + this.content;
		}
		return null;
	}

}