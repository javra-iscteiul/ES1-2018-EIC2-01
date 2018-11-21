package BDA.Email;

import java.util.Date;

public class Message {
	private String from_to;
	private String date;
	private String subject;
	private String content;
	
	/**
	 * @param userName
	 * @param datePosted
	 * @param title
	 */
	public Message(String from_to, String subject,String date,  String content) {
		this.from_to = from_to;
		this.subject=subject;
		this.date = date;
		this.content = content;
	}


	
	public String getFrom_to() {
		return from_to;
	}



	public void setFrom_to(String from_to) {
		this.from_to = from_to;
	}



	public String getDate() {
		return date;
	}



	public void setDate(String date) {
		this.date = date;
	}



	public String getSubject() {
		return subject;
	}



	public void setSubject(String subject) {
		this.subject = subject;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}

}