package BDA.Email;

import BDA.Mensagem;

/**
 * Date: Oct 24 2018
 * @author ES1-2018-EIC2-01
 * @version 1.0
 * Aplicação agregadora de conteúdos académicos: controlador do Email
 *
 */
public class MensagemEmail extends Mensagem{

	/**
	 * assunto do email
	 */

	private String subject;

	
	
	/**
	 * @param from_to String
	 * @param subject String
	 * @param date String
	 * @param content String
	 */
	public MensagemEmail(String from_to, String subject,String date,  String content) {
		super(from_to,date,content);
		this.subject=subject;
		
	}
	
	public MensagemEmail(MensagemEmail msg) {
		super(msg.getUser(),msg.getDate(),msg.getContent());
		this.subject=msg.subject;
		
	}


	/**
	 *  Devolve o assunto da mensagem
	 * @return String
	 */
	public String getSubject() {
		return subject;
	}



	/**
	 * Altera o assunto da mensagem
	 * @param subject String
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Devolve o conteudo da mensagem
	 * @return String
	 */


	/**
	 * Altera  o conteudo da mensagem
	 * @param content String
	 */
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		if(Email.getFolder()=="INBOX"){
			return "From: " + this.getUser() + "\r\n" +
					"Subject" + this.subject + "\r\n" + 
					"Date: " + this.getDate() + "\r\n" + 
					"Message: " + this.getContent();
		}
		if(Email.getFolder()=="Sent"){
			return "To: " + this.getUser() + "\r\n" +
					"Subject:" + this.subject + "\r\n" + 
					"Date: " + this.getDate() + "\r\n" + 
					"Message: " + this.getContent();
		}
		return null;
	}

	@Override
	public boolean containsFilter(String filter) {
		return this.getSubject().contains(filter) || this.getContent().contains(filter);
	}

	@Override
	public boolean userContainsFilter(String filter) {
		return this.getUser().contains(filter);
	}

}