package BDA.Facebook;


import BDA.Mensagem;

public class MensagemFacebook extends Mensagem{
	
	/**
	 * @param userName
	 * @param datePosted
	 * @param title
	 */
	public MensagemFacebook(String userName, String dateCreated, String title) {
		super(userName,dateCreated,title);
	}
	
	@Override
	public String toString(){
		return "User Name: " + (this.getUser() != null ? this.getUser() : "") + "\r\n" +
				"Date: " + this.getDate() + "\r\n" + 
				"Title: " + (this.getContent() != null ? this.getContent() : "");
	}

	@Override
	public boolean containsFilter(String filter) {
		return (this.getContent() != null && this.getContent().contains(filter)) ||
				(this.getDate() != null && this.getDate().contains(filter));
	}

	@Override
	public boolean userContainsFilter(String filter) {
		// TODO Auto-generated method stub
		return false;
	}
}