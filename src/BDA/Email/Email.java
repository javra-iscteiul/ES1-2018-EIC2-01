package BDA.Email;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.w3c.dom.Node;

import BDA.XMLclass;

/**
 * Date: Oct 24 2018
 * @author ES1-2018-EIC2-01
 * @version 1.0
 * Aplicação agregadora de conteúdos académicos: canal do Email
 *
 */

/**
 * @author hugo
 *
 */
public class Email {
	
	private static Session session;
	private static String folder = "INBOX";
	/**
	 * Este método permite que seja obtida uma lista dos emails de um utilizador (interface)
	 * @return	retorna uma lista dos emails do utilizador
	 */
	
	
	public static void init() {
		
		Properties properties = new Properties();

		// server setting
		properties.put("mail.imap.host", "imap-mail.outlook.com");
		properties.put("mail.imap.port", 993);

		// SSL setting
		properties.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.setProperty("mail.imap.socketFactory.fallback", "false");
		properties.setProperty("mail.imap.socketFactory.port", String.valueOf(993));
		Node emailConfig = XMLclass.getElement(XMLclass.configFile, "email");
		

		session = Session.getDefaultInstance(properties,new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                emailConfig.getAttributes().getNamedItem("UserName").getNodeValue(),emailConfig.getAttributes().getNamedItem("Password").getNodeValue());
            	}
             });


		/*
		 Properties properties = new Properties();
		 properties.put("mail.store.protocol", "pop3");
	      properties.put("mail.pop3.host", "pop-mail.outlook.com");
	      properties.put("mail.pop3.port", 995);
	      properties.put("mail.pop3.starttls.enable", "true");
	      session = Session.getDefaultInstance(properties);
	      */
	    

	}
	
	
	public static List<String> getTimeline() {
		try {
			/* Connect to the message Store */
			//Store store = session.getStore("pop3s");
			Store store = session.getStore("imap");
			Node emailConfig = XMLclass.getElement(XMLclass.configFile, "email");
			store.connect(emailConfig.getAttributes().getNamedItem("UserName").getNodeValue(),emailConfig.getAttributes().getNamedItem("Password").getNodeValue());
			//store.connect("pop-mail.outlook.com",emailConfig.getAttributes().getNamedItem("UserName").getNodeValue(),emailConfig.getAttributes().getNamedItem("Password").getNodeValue());
			
			/* open Inbox folder */
			Folder emailFolder = store.getFolder(folder);
			emailFolder.open(Folder.READ_ONLY);

			Message[] messages = emailFolder.getMessages();
			if (messages.length == 0) {
				System.out.println("Your Inbox is empty!");
			}
			
			// retrieve the messages from the folder in an array and print it
			System.out.println("messages.length---" + messages.length);

			List<String> emails = new ArrayList<String>();
			 for (int i = 0; i < messages.length; i++) {
		            Message message = messages[i];
		            System.out.println("---------------------------------");
		            String content = writePart(message);
		            if(folder == "INBOX"){
			            String s = "From: " + message.getFrom()[0] + "\r\n"+"Subject: "
								+ message.getSubject()  + "\r\n"+ "Date:" + message.getReceivedDate() + "\r\n"+ "Message: " + content;
			            emails.add(s);
		            }else if (folder == "Sent"){
		            	 String s = "To: " +  InternetAddress.toString(message
		                         .getRecipients(Message.RecipientType.TO))+ "\r\n"+"Subject: "
									+ message.getSubject()+ "\r\n"+ "Date:" + message.getReceivedDate()+ "\r\n" + "Message: " + content;
				          emails.add(s);
		            }
		           
		         }

			// Disconnect
			emailFolder.close(false);
			store.close();
			return emails;
		} catch (NoSuchProviderException ex) {
			System.out.println("No provider.");
			ex.printStackTrace();
		} catch (MessagingException ex) {
			System.out.println("Could not connect to the message store.");
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public static String writePart(Part p) throws Exception {
		String content="";
	      if (p instanceof Message)
	         //Call methos writeEnvelope
	         writeEnvelope((Message) p);

	      System.out.println("----------------------------");
	      System.out.println("CONTENT-TYPE: " + p.getContentType());
//
//	      //check if the content is plain text
	      if (p.isMimeType("text/plain")) {
	         System.out.println("This is plain text");
	         System.out.println("---------------------------");
	         content=(String) p.getContent();
	         System.out.println(content);
	         return content;
	        
	      } 
	      //check if the content has attachment
	      else if (p.isMimeType("multipart/*")) {
	         System.out.println("This is a Multipart");
	         System.out.println("---------------------------");
	         Multipart mp = (Multipart) p.getContent();
	         int count = mp.getCount();
	         for (int i = 0; i < count; i++){
	        	 if (mp.getBodyPart(i).isMimeType("text/plain")) {
	    	         System.out.println("This is plain text");
	    	         System.out.println("---------------------------");
	    	         content=(String) mp.getBodyPart(i).getContent();
	    	         System.out.println(content);
	    	        
	    	      }
	        	 return content;
	         }
	            //writePart(mp.getBodyPart(i));
	      } 
//	      //check if the content is a nested message
//	      else if (p.isMimeType("message/rfc822")) {
//	         System.out.println("This is a Nested Message");
//	         System.out.println("---------------------------");
//	         writePart((Part) p.getContent());
//	      } 
//	      //check if the content is an inline image
//	      else if (p.isMimeType("image/jpeg")) {
//	         System.out.println("--------> image/jpeg");
//	         Object o = p.getContent();
//
//	         InputStream x = (InputStream) o;
//	         // Construct the required byte array
//	       
//	         System.out.println("x.length = " + x.available());
//	         int i;
//	         byte[] bArray =new byte[x.available()];
//	         while ((i = (int) ((InputStream) x).available()) > 0) {
//	            int result = (int) (((InputStream) x).read(bArray));
//	            if (result == -1)
//	            i = 0;
//		          bArray= new byte[x.available()];
//	            break;
//	         }
//	         FileOutputStream f2 = new FileOutputStream("/tmp/image.jpg");
//	         f2.write(bArray);
//	      } 
//	      else if (p.getContentType().contains("image/")) {
//	         System.out.println("content type" + p.getContentType());
//	         File f = new File("image" + new Date().getTime() + ".jpg");
//	         DataOutputStream output = new DataOutputStream(
//	            new BufferedOutputStream(new FileOutputStream(f)));
//	            com.sun.mail.util.BASE64DecoderStream test = 
//	                 (com.sun.mail.util.BASE64DecoderStream) p
//	                  .getContent();
//	         byte[] buffer = new byte[1024]; 
//	         int bytesRead;
//	         while ((bytesRead = test.read(buffer)) != -1) {
//	            output.write(buffer, 0, bytesRead);
//	         }
//	      } 
//	      else {
//	         Object o = p.getContent();
//	         if (o instanceof String) {
//	            System.out.println("This is a string");
//	            System.out.println("---------------------------");
//	            System.out.println((String) o);
//	         } 
//	         else if (o instanceof InputStream) {
//	            System.out.println("This is just an input stream");
//	            System.out.println("---------------------------");
//	            InputStream is = (InputStream) o;
//	            is = (InputStream) o;
//	            int c;
//	            while ((c = is.read()) != -1)
//	               System.out.write(c);
//	         } 
//	         else {
//	            System.out.println("This is an unknown type");
//	            System.out.println("---------------------------");
//	            System.out.println(o.toString());
//	         }
//	      }
	      return null;
	   }
	   
	public static void writeEnvelope(Message m) throws Exception {
	      System.out.println("This is the message envelope");
	      System.out.println("---------------------------");
	      Address[] a;

	      // FROM
	      if ((a = m.getFrom()) != null) {
	         for (int j = 0; j < a.length; j++)
	         System.out.println("FROM: " + a[j].toString());
	      }

	      // TO
	      if ((a = m.getRecipients(Message.RecipientType.TO)) != null) {
	         for (int j = 0; j < a.length; j++)
	         System.out.println("TO: " + a[j].toString());
	      }

	      // SUBJECT
	      if (m.getSubject() != null)
	         System.out.println("SUBJECT: " + m.getSubject());

	   }


	public static List<String> filter(String text) {
		try {
			/* Connect to the message Store */
			//Store store = session.getStore("pop3s");
			Store store = session.getStore("imap");
			Node emailConfig = XMLclass.getElement(XMLclass.configFile,"email");
			store.connect(emailConfig.getAttributes().getNamedItem("UserName").getNodeValue(),emailConfig.getAttributes().getNamedItem("Password").getNodeValue());
			//store.connect("pop.gmail.com",emailConfig.getAttributes().getNamedItem("UserName").getNodeValue(),emailConfig.getAttributes().getNamedItem("Password").getNodeValue());
			/* open Inbox folder */
			Folder emailFolder = store.getFolder(folder);
			emailFolder.open(Folder.READ_ONLY);

			Message[] messages = emailFolder.getMessages();
			if (messages.length == 0) {
				System.out.println("Your Inbox is empty!");
			}
			
			// retrieve the messages from the folder in an array and print it
			System.out.println("messages.length---" + messages.length);

			List<String> emails = new ArrayList<String>();
			
			 for (int i = 0; i < messages.length; i++) {
		            Message message = messages[i];
		            System.out.println("---------------------------------");
		            String content = writePart(message);
		            if(message.getSubject().contains(text) ||content.contains(text) ) {
		            	 if(folder == "INBOX"){
					            String s = "From: " + message.getFrom()[0] + "\r\n" +"Subject: "
										+ message.getSubject() + "\r\n"+ "Date:" + message.getReceivedDate()+  "\r\n" +"Message: " + content;
					            emails.add(s);
				          }else if (folder == "Sent"){
				            	 String s = "To: " +  InternetAddress.toString(message
				                         .getRecipients(Message.RecipientType.TO))+  "\r\n" +"Subject: "
											+ message.getSubject() + "\r\n"+ "Date:" + message.getReceivedDate()+ "\r\n" + "Message: " + content;
						          emails.add(s);
				          }
		            }
		         }

			// Disconnect
			emailFolder.close(false);
			store.close();
			return emails;
		} catch (NoSuchProviderException ex) {
			System.out.println("No provider.");
			ex.printStackTrace();
		} catch (MessagingException ex) {
			System.out.println("Could not connect to the message store.");
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	
	
	
	public static void sendEmails(String to, String sub, String text) {
		System.out.println(to + sub + text);
		Node emailConfig = XMLclass.getElement(XMLclass.configFile,"email");
		

	      // Sender's email ID needs to be mentioned
	      String from = emailConfig.getAttributes().getNamedItem("UserName").getNodeValue();
	      String password = emailConfig.getAttributes().getNamedItem("Password").getNodeValue();

	      Properties props = new Properties();
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.host", "outlook.office365.com");
	        props.put("mail.smtp.port", "587");

	        Session session = Session.getInstance(props,
	          new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(from, password);
	            }
	          });

	        try {

	            Message message = new MimeMessage(session);
	            message.setFrom(new InternetAddress(from));
	            message.setRecipients(Message.RecipientType.TO,
	                InternetAddress.parse(to));
	            message.setSubject(sub);
	            message.setText(text);

	            Transport.send(message);

	            System.out.println("Done");
	         System.out.println("Sent message successfully....");
	      } catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
	}


	public static String getFolder() {
		return folder;
	}


	public static void setFolder(String folder) {
		Email.folder = folder;
	}
	
	
}
