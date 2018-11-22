package BDA.Facebook;

import java.util.Date;

public class Message {
	private String userName;
	private String dateCreated;
	private String title;
	
	/**
	 * @param userName
	 * @param datePosted
	 * @param title
	 */
	public Message(String userName, String dateCreated, String title) {
		this.userName = userName;
		this.dateCreated = dateCreated;
		this.title = title;
	}

	public String getUserName() {
		return this.userName;
	}

	public String getDateCreated() {
		return this.dateCreated;
	}

	public String getTitle() {
		return this.title;
	}
	
	@Override
	public String toString(){
		return "User Name: " + (this.userName != null ? this.userName : "") + "\r\n" +
				"Date: " + this.dateCreated + "\r\n" + 
				"Title: " + (this.title != null ? this.title : "");
	}
}