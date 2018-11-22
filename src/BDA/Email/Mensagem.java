package BDA.Email;

/**
 * Date: Oct 24 2018
 * @author ES1-2018-EIC2-01
 * @version 1.0
 * Aplicação agregadora de conteúdos académicos: controlador do Email
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

	
	
	/**
	 * @param from_to String
	 * @param subject String
	 * @param date String
	 * @param content String
	 */
	public Mensagem(String from_to, String subject,String date,  String content) {
		this.from_to = from_to;
		this.subject=subject;
		this.date = date;
		this.content = content;
	}


	
	/**
	 * Devolve o destinatario/origem da mensagem
	 * @return String
	 */
	public String getFrom_to() {
		return from_to;
	}
	/**
	 * Altera o destinatario/origem da mensagem
	 * @param from_to String
	 */

	public void setFrom_to(String from_to) {
		this.from_to = from_to;
	}



	/**
	 * Devolve a data da mensagem
	 * @return String
	 */
	public String getDate() {
		return date;
	}



	/**
	 * Altera a data da mensagem
	 * @param date String
	 */
	public void setDate(String date) {
		this.date = date;
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
	public String getContent() {
		return content;
	}


	/**
	 * Altera  o conteudo da mensagem
	 * @param content String
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
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