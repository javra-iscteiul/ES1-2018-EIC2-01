package BDA;

public abstract class Mensagem {

	
	/**
	 * String from(caixa de entrada) ou To(caixa de saida)
	 */

	private String user;
	/**
	 * data do email 
	 */

	private String date;

	/**
	 * conteudo do email
	 */
	private String content;

	
	
	/**
	 * @param user String
	 * @param date String
	 * @param content String
	 */
	public Mensagem( String user,String date,  String content) {
		this.user=user;
		this.date = date;
		this.content = content;
	}



	/**
	 * Devolve o user da mensagem
	 * @return String
	 */
	public String getUser() {
		return user;
	}



	/**
	 * Altera o user da mensagem
	 * @param user String
	 */
	public void setUser(String user) {
		this.user = user;
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
	 * Devolve o conteudo da mensagem
	 * @return String
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Altera o conteudo da mensagem
	 * @param content String
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * Devolve se a mensagem contem o filtro indicado como parametro
	 * @param filter String
	 * @return boolean
	 */
	public abstract boolean containsFilter(String filter);
	
	/**
	 * Devolve se a mensagem pertence ao user indicado como parametro
	 * @param filter String
	 * @return boolean
	 */
	public abstract boolean userContainsFilter(String filter);
}
