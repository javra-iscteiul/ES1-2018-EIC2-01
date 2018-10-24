package BDA.Email;

import javafx.event.Event;
import java.io.IOException;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import BDA.FuncoesGerais;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import twitter4j.TwitterException;


public class Email {
	
	
	private String mail;
	private String pass;
	
	private ObservableList<String> emails = FXCollections.observableArrayList();
	
	@FXML
	private ListView<String> emailsList;
	
	@FXML
	private TextField email;
	
	@FXML
	private PasswordField password;
	
	
	@FXML
    private void login(ActionEvent event) 
    {
		mail=email.getText();
		pass=password.getText();
		//Email email = new Email();
		//email.searchEmail(mail, pass);
		init(event, mail, pass);
		//FuncoesGerais.mudarVistaFXML(event, getClass().getResource("./Email/email.fxml"));
    }
	

	
	public void init(Event event, String m, String p){
		FuncoesGerais.mudarVistaFXML(event, getClass().getResource("email.fxml"));
		Email email = new Email();
		email.searchEmail(m, p);
	}
	
	public void searchEmail(String username, String password) {
		Properties properties = new Properties();

		// server setting
		properties.put("mail.imap.host", "imap-mail.outlook.com");
		properties.put("mail.imap.port", 993);

		// SSL setting
		properties.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.setProperty("mail.imap.socketFactory.fallback", "false");
		properties.setProperty("mail.imap.socketFactory.port", String.valueOf(993));

		Session session = Session.getDefaultInstance(properties);

		try {
			/* Connect to the message Store */
			Store store = session.getStore("imap");
			store.connect(username, password);
			

			/* open Inbox folder */
			Folder emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.READ_ONLY);

			Message[] foundMessages = emailFolder.getMessages();
			if (foundMessages.length == 0) {
				System.out.println("Your Inbox is empty!");
			}
			 // retrieve the messages from the folder in an array and print it
		      Message[] messages = emailFolder.getMessages();
		      System.out.println("messages.length---" + messages.length);

		      for (int i = 0, n = messages.length; i < n; i++) {
		         Message message = messages[i];
		         System.out.println("---------------------------------");
		         System.out.println("Email Number " + (i + 1));
		         System.out.println("Subject: " + message.getSubject());
		         System.out.println("From: " + message.getFrom()[0]);
		         System.out.println("Text: " + message.getContent().toString());
		         String s = "---------------------------------" + "Email Number " + (i + 1) +
		        		 "Subject: " + message.getSubject() + "From: " + message.getFrom()[0] +
		        		 "From: " + message.getFrom()[0] + "Text: " + message.getContent().toString();
		 		
					emails.add(i, s);

		      }
		      //emailsList.setItems(emails);

			// Disconnect
			emailFolder.close(false);
			store.close();

		} catch (NoSuchProviderException ex) {
			System.out.println("No provider.");
			ex.printStackTrace();
		} catch (MessagingException ex) {
			System.out.println("Could not connect to the message store.");
			ex.printStackTrace();
	      } catch (Exception e) {
	          e.printStackTrace();
	       }

	}

	private String getTextFromMessage(Message message) throws MessagingException, IOException {
		String text = "";
		if (message.isMimeType("text/plain")) {
			text = message.getContent().toString();
		} else if (message.isMimeType("multipart/*")) {
			Multipart mimeMultipart = (Multipart) message.getContent();
			text = getTextFromMimeMultipart(mimeMultipart);
		}
		return text;
	}
	
	
	private String getTextFromMimeMultipart(Multipart mimeMultipart) throws MessagingException, IOException {
		String text = "";
		int count = mimeMultipart.getCount();
		for (int i = 0; i < count; i++) {
			BodyPart bodyPart = mimeMultipart.getBodyPart(i);
			if (bodyPart.isMimeType("text/plain")) {
				text = text + "\n" + bodyPart.getContent();
				break; // without break same text appears twice in my tests

			} else if (bodyPart.getContent() instanceof Multipart) {
				text = text + getTextFromMimeMultipart((Multipart) bodyPart.getContent());
			}
		}
		return text;
	}

	
	public static void main(String[] args) {
		//String username = "es1g1@outlook.com";
		//String password = "grupo1grupo";
		//Email email = new Email();
		//email.searchEmail(username, password);

	}
	  
	}

