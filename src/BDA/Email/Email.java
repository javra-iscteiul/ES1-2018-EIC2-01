package BDA.Email;


import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import BDA.XMLclass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

/**
 * Date: Oct 24 2018
 * @author ES1-2018-EIC2-01
 * @version 1.0
 * Aplica��o agregadora de conte�dos acad�micos: canal do Email
 *
 */

/**
 * @author hugo
 *
 */
public class Email {
	
	/**
	 * ObservableList com os emails
	 */
	private ObservableList<Mensagem> emails = FXCollections.observableArrayList();

	
	private static Session session;
	private static String folder = "INBOX";
	private static String to="";
	
	
	
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
	
	/**
	 * Este m�todo permite que seja obtida uma lista dos emails de um utilizador (interface)
	 * @return	retorna uma lista dos emails do utilizador
	 */
	public boolean getTimeline(ListView <Mensagem> emailsList) {
		Map<String, Map<String, String>> dataToStore = new HashMap<>();
		emails.clear();
		emailsList.getItems().clear();
		try {
			/* Connect to the message Store */
			//Store store = session.getStore("pop3s");
			Store store = session.getStore("imap");
			Node emailConfig = XMLclass.getElement(XMLclass.configFile, "email");
			store.connect(emailConfig.getAttributes().getNamedItem("UserName").getNodeValue(),emailConfig.getAttributes().getNamedItem("Password").getNodeValue());
			//store.connect("pop-mail.outlook.com",emailConfig.getAttributes().getNamedItem("UserName").getNodeValue(),emailConfig.getAttributes().getNamedItem("Password").getNodeValue());
			
			if (folder=="Sent" && XMLclass.existsElement(XMLclass.storedDataFile, "emailSent")) {
				XMLclass.deleteElement(XMLclass.storedDataFile, "emailSent");
			}
			
			if (folder=="INBOX" && XMLclass.existsElement(XMLclass.storedDataFile, "emailInbox")) {
				XMLclass.deleteElement(XMLclass.storedDataFile, "emailInbox");
			}
			
			/* open Inbox folder */
			Folder emailFolder = store.getFolder(folder);
			emailFolder.open(Folder.READ_ONLY);

			Message[] messages = emailFolder.getMessages();
			if (messages.length == 0) {
				System.out.println("Your Inbox is empty!");
			}
			
			// retrieve the messages from the folder in an array and print it
			System.out.println("messages.length---" + messages.length);

			
			 for (int i = 0; i < messages.length; i++) {
		            Message message = messages[i];
		            System.out.println("---------------------------------");
		            String content = writePart(message);
		            Map<String, String> childAttributesToStore = new HashMap<>();
		            if(folder == "INBOX"){
			            //String s = "From: " + message.getFrom()[0] + "\r\n"+"Subject: "
						//		+ message.getSubject()  + "\r\n"+ "Date:" + message.getReceivedDate() + "\r\n"+ "Message: " + content;
			            emails.add(new Mensagem(message.getFrom()[0].toString(),message.getSubject(),
			            		message.getReceivedDate().toString(),content));
			           
						childAttributesToStore.put("From",message.getFrom()[0].toString() );
						childAttributesToStore.put("Subject",message.getSubject().toString() );
						childAttributesToStore.put("Date", message.getReceivedDate().toString());
						childAttributesToStore.put("Message", content);
						dataToStore.put("post" + i, childAttributesToStore);
		            }else if (folder == "Sent"){
		            	 String s = "To: " +  InternetAddress.toString(message
		                         .getRecipients(Message.RecipientType.TO))+ "\r\n"+"Subject: "
									+ message.getSubject()+ "\r\n"+ "Date:" + message.getReceivedDate()+ "\r\n" + "Message: " + content;
		            	 emails.add(i,new Mensagem(InternetAddress.toString(message
		                         .getRecipients(Message.RecipientType.TO)),message.getSubject(),
				            		message.getReceivedDate().toString(),content));
				           
				    
							childAttributesToStore.put("To",InternetAddress.toString(message
			                         .getRecipients(Message.RecipientType.TO)) );
							childAttributesToStore.put("Subject",message.getSubject().toString() );
							childAttributesToStore.put("Date", message.getReceivedDate().toString());
							childAttributesToStore.put("Message", content);
							dataToStore.put("post" + i, childAttributesToStore);
		            }
		           
		         }
			 if(folder=="INBOX") {
				 XMLclass.addElementAndChild(XMLclass.storedDataFile, "emailInbox", dataToStore);
			 }
			 if(folder=="Sent") {
				 XMLclass.addElementAndChild(XMLclass.storedDataFile, "emailSent", dataToStore);
			 }
			// Disconnect
			emailFolder.close(false);
			store.close();
			emailsList.setItems(emails);
			return true;
		} catch (NoSuchProviderException ex) {
			System.out.println("No provider.");
			ex.printStackTrace();
		}catch (UnknownHostException ex){
			System.out.println("entrar em modo offline");
			return getStoredTimeLine(emailsList);
		} catch (MessagingException ex) {
			System.out.println("Could not connect to the message store.");
			return getStoredTimeLine(emailsList);
			//ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private boolean getStoredTimeLine(ListView<Mensagem> emailsList) {
		try {
			if(folder=="INBOX"){
				if (XMLclass.existsElement(XMLclass.storedDataFile, "emailInbox")) {
					Node emailNode = XMLclass.getElement(XMLclass.storedDataFile, "emailInbox");
					System.out.println("ola");
					for (int i = 0; i < emailNode.getChildNodes().getLength(); i++) {
						NamedNodeMap childAttributes = emailNode.getChildNodes().item(i).getAttributes();
						if (childAttributes != null) {
							String from = childAttributes.getNamedItem("From").getNodeValue();
							String sub = childAttributes.getNamedItem("Subject").getNodeValue();
							String date = childAttributes.getNamedItem("Date").getNodeValue();
							String content = childAttributes.getNamedItem("Message").getNodeValue();
							System.out.println( "From: " + from + "\r\n"+"Subject: "
									+ sub  + "\r\n"+ "Date:" + date + "\r\n"+ "Message: " + content);
							  emails.add(new Mensagem(from,sub,date,content));
					                
						}
					}
				}
			}else if(folder=="Sent"){
				System.out.println("ola2");
				if (XMLclass.existsElement(XMLclass.storedDataFile, "emailSent")) {
					Node emailNode = XMLclass.getElement(XMLclass.storedDataFile, "emailSent");
					for (int i = 0; i < emailNode.getChildNodes().getLength(); i++) {
						NamedNodeMap childAttributes = emailNode.getChildNodes().item(i).getAttributes();
							if (childAttributes != null) {
								String to = childAttributes.getNamedItem("To").getNodeValue();
								String sub = childAttributes.getNamedItem("Subject").getNodeValue();
								String date = childAttributes.getNamedItem("Date").getNodeValue();
								String content = childAttributes.getNamedItem("Message").getNodeValue();
								String s = "To: " + to + "\r\n"+"Subject: "
										+ sub  + "\r\n"+ "Date:" + date + "\r\n"+ "Message: " + content;
								emails.add(new Mensagem(to,sub,date,content));
							}
					}
				            
				}
			}
			emailsList.setItems(emails);
			return true;
		} catch (Exception ex) {
			return false;
		}
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


	public boolean filter(ListView<Mensagem> emailsList, String text) {
		try {
			this.getTimeline(emailsList);
			List <Mensagem> nova = new ArrayList<Mensagem>();
			 for (int i = 0; i < emails.size(); i++) {
		            if(emails.get(i).getSubject().contains(text) ||emails.get(i).getContent().contains(text) ) {
		            	nova.add(i,new Mensagem(emails.get(i).getFrom_to(),emails.get(i).getSubject(),
								  emails.get(i).getDate().toString(),emails.get(i).getContent()));

				         }
			 }
			emails.clear();
			for (Mensagem m : nova)
				emails.add(m);
			
			emailsList.setItems(emails);
			return true;

		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
		
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


	public static String getTo() {
		return to;
	}


	public static void setTo(String to) {
		Email.to = to;
	}
	
	
	
}
