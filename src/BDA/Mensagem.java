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
	 * @param from_to String
	 * @param date String
	 * @param content String
	 */
	public Mensagem( String user,String date,  String content) {
		this.user=user;
		this.date = date;
		this.content = content;
	}



	public String getUser() {
		return user;
	}



	public void setUser(String user) {
		this.user = user;
	}



	public String getDate() {
		return date;
	}



	public void setDate(String date) {
		this.date = date;
	}



	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public abstract boolean containsFilter(String filter);
	
	public abstract boolean userContainsFilter(String filter);
}
