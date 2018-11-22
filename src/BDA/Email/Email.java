package BDA.Email;


import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Address;
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

/**
 * Date: Oct 24 2018
 * @author ES1-2018-EIC2-01
 * @version 1.0
 * Aplicação agregadora de conteúdos académicos: canal do Email
 *
 */

public class Email {
	
	/**
	 * ObservableList com os emails
	 */
	private ObservableList<Mensagem> emails = FXCollections.observableArrayList();

	
	/**
	 * Sessao do email com as propriedades já definidas
	 */
	private static Session session;
	/**
	 * String indicadora da pasta seleciona iniciada com a pasta default
	 */
	private static String folder = "INBOX";
	/**
	 * String indicadora do destinatorio de uma possivel mensagem para intercomunicação entre interfaces
	 */
	private static String to="";
	
	
	
	/**
	 * Procedimento responsavel por iniciar a aplicação
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

	}
	
	/**
	 * Este método permite que seja obtida uma lista dos emails de um utilizador (interface)
	 * @return	retorna uma lista dos emails do utilizador
	 */
	public ObservableList<Mensagem> getTimeline() {
		Map<String, Map<String, String>> dataToStore = new HashMap<>();
		emails.clear();
		try {
			/* Connect to the message Store */
			Store store = session.getStore("imap");
			Node emailConfig = XMLclass.getElement(XMLclass.configFile, "email");
			store.connect(emailConfig.getAttributes().getNamedItem("UserName").getNodeValue(),emailConfig.getAttributes().getNamedItem("Password").getNodeValue());
			
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
			            emails.add(new Mensagem(message.getFrom()[0].toString(),message.getSubject(),
			            		message.getReceivedDate().toString(),content));
			           
						childAttributesToStore.put("From",message.getFrom()[0].toString() );
						childAttributesToStore.put("Subject",message.getSubject().toString() );
						childAttributesToStore.put("Date", message.getReceivedDate().toString());
						childAttributesToStore.put("Message", content);
						dataToStore.put("post" + i, childAttributesToStore);
		            }else if (folder == "Sent"){
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
			return emails;
		} catch (NoSuchProviderException ex) {
			System.out.println("No provider.");
			ex.printStackTrace();
		}catch (UnknownHostException ex){
			System.out.println("entrar em modo offline");
			return getStoredTimeLine();
		} catch (MessagingException ex) {
			System.out.println("Could not connect to the message store.");
			return getStoredTimeLine();
			//ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Este método permite que seja obtida uma lista dos emails de um utilizador no caso de este se encontrar sem acesso à Internet (interface)
	 * @return	retorna uma lista dos emails do utilizador
	 */
	private ObservableList<Mensagem> getStoredTimeLine() {
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
								emails.add(new Mensagem(to,sub,date,content));
							}
					}
				            
				}
			}
			
			return emails;
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * Procedimento responsavel por ler a informação do email e passá-la para string
	 * @param p Part
	 * @return String conteudo do email
	 * @throws Exception
	 */
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
	            
	      } 

	      return null;
	   }
	   
	/**
	 * Procedimento que imprime na consola os dados principais da mensagem
	 * @param m Message
	 * @throws Exception
	 */
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


	/**
	 * Procedimento que filtra os emails de um utilizador consoante uma palavra ou frase
	 * @param text String
	 * @return lista dos emails do utilizador filtrada
	 */
	public ObservableList<Mensagem> filter(String text) {
		try {
			
			ObservableList<Mensagem> nova = FXCollections.observableArrayList();
			 for (int i = 0; i < emails.size(); i++) {
		            if(emails.get(i).getSubject().contains(text) ||emails.get(i).getContent().contains(text) ) {
		            	nova.add(new Mensagem(emails.get(i).getFrom_to(),emails.get(i).getSubject(),
								  emails.get(i).getDate().toString(),emails.get(i).getContent()));
				     }
			 }
			 
			return nova;

		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	
	/**
	 * Procedimento responsavel pelo envio de uma mensagem com os dados recebidos como parametros
	 * @param to String
	 * @param sub String
	 * @param text String
	 */
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


	/**
	 * Devolve o folder atual
	 * @return String folder
	 */
	public static String getFolder() {
		return folder;
	}


	/**
	 * Muda o folder para o folder dado como parametro
	 * @param folder String
	 */
	public static void setFolder(String folder) {
		Email.folder = folder;
	}


	/**
	 * Retorna o destinatario de uma possivel mensagem
	 * @return String to
	 */
	public static String getTo() {
		return to;
	}


	/**
	 * Altera o destinatario de uma possivel mensagem
	 * @param to String
	 */
	public static void setTo(String to) {
		Email.to = to;
	}
	
	
	
}
