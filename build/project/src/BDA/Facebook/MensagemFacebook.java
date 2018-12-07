package BDA.Facebook;

import BDA.Mensagem;

public class MensagemFacebook extends Mensagem{
	
	/**
	 * @param userName String
	 * @param dateCreated String
	 * @param title String 
	 */
	public MensagemFacebook(String userName, String dateCreated, String title) {
		super(userName,dateCreated,title);
	} 
	
	@Override
	public String toString(){
		return "User Name: " + (this.getUser() != null ? this.getUser() : "") + "\r\n" +
				"Date: " + (this.getDate() != null ? this.getDate() : "") + "\r\n" + 
				"Title: " + (this.getContent() != null ? this.getContent() : "");
	}
	
	
	/**
	 * Devolve se a mensagem contem o filtro indicado como parametro
	 * @param filter String
	 * @return boolean
	 */
	@Override
	public boolean containsFilter(String filter) {
		return (this.getUser() != null && this.getUser().contains(filter)) ||
				(this.getContent() != null && this.getContent().contains(filter)) ||
				(this.getDate() != null && this.getDate().contains(filter));
	}
	
	/**
	 * Devolve se a mensagem contem o filtro indicado como parametro
	 * @param filter String
	 * @return boolean
	 */
	@Override
	public boolean userContainsFilter(String filter) {
		return (this.getUser() != null && this.getUser().contains(filter));
	}
}