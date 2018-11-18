package BDA.Facebook;

import java.util.Date;

public class Message {
	private String userName;
	private Date dateCreated;
	private String title;
	
	/**
	 * @param userName
	 * @param datePosted
	 * @param title
	 */
	public Message(String userName, Date datePosted, String title) {
		this.userName = userName;
		this.dateCreated = datePosted;
		this.title = title;
	}

	public String getUserName() {
		return this.userName;
	}

	public Date getDatePosted() {
		return this.dateCreated;
	}

	public String getTitle() {
		return this.title;
	}
	
	@Override
	public String toString(){
		return "User Name: " + this.userName + "; Date: " + this.dateCreated + "; Title: " + this.title + ".";
	}
}