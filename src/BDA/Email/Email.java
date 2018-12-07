package BDA.Email;

import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
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

import org.w3c.dom.DOMException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import BDA.Credential;
import BDA.IService;
import BDA.Mensagem;
import BDA.XMLclass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Date: Oct 24 2018
 * 
 * @author ES1-2018-EIC2-01
 * @version 1.0 Aplicação agregadora de conteúdos académicos: canal do Email
 *
 */

public class Email implements IService {
	/**
	 * credenciais do email
	 */
	private Credential emailCredential;

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
	 * String indicadora do destinatorio de uma possivel mensagem para
	 * intercomunicação entre interfaces
	 */
	private static String to = "";

	/**
	 * Procedimento responsavel por iniciar a aplicação
	 */
	public void init(Credential cred) {
		emailCredential = cred;
		folder = "INBOX";
		Properties properties = new Properties();

		// server setting
		properties.put("mail.imap.host", "imap-mail.outlook.com");
		properties.put("mail.imap.port", 993);

		// SSL setting
		properties.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.setProperty("mail.imap.socketFactory.fallback", "false");
		properties.setProperty("mail.imap.socketFactory.port", String.valueOf(993));

		session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(emailCredential.username, emailCredential.password);
			}
		});
		System.out.println(emailCredential.username + emailCredential.password);
	}

	/**
	 * Este método permite que seja obtida uma lista dos emails de um utilizador
	 * (interface)
	 * 
	 * @return ObservableList
	 * @throws Exception e
	 */
	public ObservableList<Mensagem> getTimeLine() throws Exception {
		Map<String, Map<String, String>> dataToStore = new HashMap<>();
		emails.clear(); 
		try {
			/* Connect to the message Store */
			Store store = session.getStore("imap");
			store.connect(emailCredential.username, emailCredential.password);

			if (folder == "Sent" && XMLclass.existsNode(XMLclass.storedDataFile, "emailSent", emailCredential)) {
				XMLclass.deleteNode(XMLclass.storedDataFile, "emailSent", emailCredential);
			}

			if (folder == "INBOX" && XMLclass.existsNode(XMLclass.storedDataFile, "emailInbox", emailCredential)) {
				XMLclass.deleteNode(XMLclass.storedDataFile, "emailInbox", emailCredential);
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
				// System.out.println("---------------------------------");
				String content = writePart(message);
				Map<String, String> childAttributesToStore = new HashMap<>();
				if (folder == "INBOX") {
					emails.add(new MensagemEmail(message.getFrom()[0].toString(), message.getSubject(),
							message.getReceivedDate().toString(), content));

					childAttributesToStore.put("From", message.getFrom()[0].toString());
					childAttributesToStore.put("Subject", message.getSubject().toString());
					childAttributesToStore.put("Date", message.getReceivedDate().toString());
					childAttributesToStore.put("Message", content);
					dataToStore.put("post" + i, childAttributesToStore);
				} else if (folder == "Sent") {
					emails.add(i,
							new MensagemEmail(InternetAddress.toString(message.getRecipients(Message.RecipientType.TO)),
									message.getSubject(), message.getReceivedDate().toString(), content));

					childAttributesToStore.put("To",
							InternetAddress.toString(message.getRecipients(Message.RecipientType.TO)));
					childAttributesToStore.put("Subject", message.getSubject().toString());
					childAttributesToStore.put("Date", message.getReceivedDate().toString());
					childAttributesToStore.put("Message", content);
					dataToStore.put("post" + i, childAttributesToStore);
				}

			}
			if (folder == "INBOX") {
				XMLclass.addNodeAndChild(XMLclass.storedDataFile, "emailInbox", emailCredential, dataToStore);
			} else if (folder == "Sent") {
				XMLclass.addNodeAndChild(XMLclass.storedDataFile, "emailSent", emailCredential, dataToStore);
			}
			// Disconnect
			emailFolder.close(false);
			store.close();

			Collections.reverse(emails);
			return emails;
		} catch (NoSuchProviderException ex) {
			System.out.println("No provider.");
			ex.printStackTrace();
		} catch (UnknownHostException ex) {
			System.out.println("entrar em modo offline");
			return getStoredTimeLine();
		} catch (MessagingException ex) {
			System.out.println("Could not connect to the message store.");
			return getStoredTimeLine();
			// ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Este método permite que seja obtida uma lista dos emails de um utilizador
	 * no caso de este se encontrar sem acesso à Internet (interface)
	 * 
	 * @return ObservableList
	 * @throws Exception e
	 * @throws DOMException e
	 */

	public ObservableList<Mensagem> getStoredTimeLine() throws DOMException, Exception {
			if (folder == "INBOX") {
				if (XMLclass.existsNode(XMLclass.storedDataFile, "emailInbox", emailCredential)) {
					Node emailNode = XMLclass.getNode(XMLclass.storedDataFile, "emailInbox", emailCredential);
					for (int i = 0; i < emailNode.getChildNodes().getLength(); i++) {
						NamedNodeMap childAttributes = emailNode.getChildNodes().item(i).getAttributes();
						if (childAttributes != null) {
							String from = childAttributes.getNamedItem("From").getNodeValue();
							String sub = childAttributes.getNamedItem("Subject").getNodeValue();
							String date = childAttributes.getNamedItem("Date").getNodeValue();
							String content = childAttributes.getNamedItem("Message").getNodeValue();
							emails.add(new MensagemEmail(from, sub, date, content));

						}
					}
				}

			} else if (folder == "Sent") {
				if (XMLclass.existsNode(XMLclass.storedDataFile, "emailSent", emailCredential)) {
					Node emailNode = XMLclass.getNode(XMLclass.storedDataFile, "emailSent", emailCredential);

					for (int i = 0; i < emailNode.getChildNodes().getLength(); i++) {
						NamedNodeMap childAttributes = emailNode.getChildNodes().item(i).getAttributes();
						if (childAttributes != null) {
							String to = childAttributes.getNamedItem("To").getNodeValue();
							String sub = childAttributes.getNamedItem("Subject").getNodeValue();
							String date = childAttributes.getNamedItem("Date").getNodeValue();
							String content = childAttributes.getNamedItem("Message").getNodeValue();
							emails.add(new MensagemEmail(to, sub, date, content));
						}
					}

				}
			}
			return emails;
	}

	/**
	 * Procedimento responsavel por ler a informação do email e passá-la para
	 * string
	 * 
	 * @param p  Part
	 *           
	 * @return String conteudo do email
	 * @throws Exception e
	 */
	public static String writePart(Part p) throws Exception {
		String content = "";
		if (p.isMimeType("text/plain")) {
			content = (String) p.getContent();
			return content;

		}
		// check if the content has attachment
		else if (p.isMimeType("multipart/*")) {
			Multipart mp = (Multipart) p.getContent();
			int count = mp.getCount();
			for (int i = 0; i < count; i++) {
				if (mp.getBodyPart(i).isMimeType("text/plain")) {
					content = (String) mp.getBodyPart(i).getContent();

				}
				return content;
			}

		}

		return null;
	}

	/**
	 * Procedimento que filtra os emails de um utilizador consoante uma palavra
	 * ou frase
	 * 
	 * @param text String
	 *           
	 * @return ObservableList
	 * @throws Exception e
	 */
	public ObservableList<Mensagem> setFilter(String text) throws Exception {
		ObservableList<Mensagem> nova = FXCollections.observableArrayList();
		for (int i = 0; i < emails.size(); i++) {
			if (emails.get(i).containsFilter(text)) {
				nova.add(new MensagemEmail((MensagemEmail) emails.get(i)));
			}
		}

		Map<String, String> filterAttr = new HashMap<>();
		filterAttr.put("value", text);
		if (!XMLclass.existsChildNode(XMLclass.configFile, XMLclass.emailService, emailCredential, "filter",
				filterAttr))
			XMLclass.addChild(XMLclass.configFile, XMLclass.emailService, emailCredential, "filter", filterAttr);

		return nova;
	}

	/**
	 * Procedimento que filtra os emails de um utilizador consoante os emails
	 * que pertencem ao utilizador pedido
	 * 
	 * @param text  String
	 * @return ObservableList
	 * @throws Exception e
	 */
	public ObservableList<Mensagem> filterUser(String text) throws Exception {
		ObservableList<Mensagem> nova = FXCollections.observableArrayList();
		for (int i = 0; i < emails.size(); i++) {
			if (emails.get(i).userContainsFilter(text)) {
				nova.add(new MensagemEmail((MensagemEmail) emails.get(i)));
			}
		}

		Map<String, String> filterAttr = new HashMap<>();
		filterAttr.put("value", text);
		if (!XMLclass.existsChildNode(XMLclass.configFile, XMLclass.emailService, emailCredential, "filterUser",
				filterAttr))
			XMLclass.addChild(XMLclass.configFile, XMLclass.emailService, emailCredential, "filterUser", filterAttr);
		return nova;
	}

	/**
	 * Procedimento que filtra os emails de um utilizador das ultimas 24 horas
	 * 
	 * @return ObservableList
	 * @throws Exception e
	 * @param s String
	 */
	public ObservableList<Mensagem> getLast(String s) throws Exception {
		ObservableList<Mensagem> nova = FXCollections.observableArrayList();
		for (int i = 0; i < emails.size(); i++) {
			DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
			Date date = format.parse(emails.get(i).getDate());
			Instant now = Instant.now();
			if (s == "24h") {
				if ((!date.toInstant().isBefore(now.minus(24, ChronoUnit.HOURS))) && (date.toInstant().isBefore(now))) {
					nova.add(new MensagemEmail((MensagemEmail) emails.get(i)));
				}
			}
			if (s == "week") {
				if ((!date.toInstant().isBefore(now.minus(7, ChronoUnit.DAYS))) && (date.toInstant().isBefore(now))) {
					nova.add(new MensagemEmail((MensagemEmail) emails.get(i)));
				}
			}
			if (s == "month") {
				if ((!date.toInstant().isBefore(now.minus(30, ChronoUnit.DAYS))) && (date.toInstant().isBefore(now))) {
					nova.add(new MensagemEmail((MensagemEmail) emails.get(i)));
				}
			}
		}
		Map<String, String> filterAttr = new HashMap<>();
		filterAttr.put("value", "true");
		if (!XMLclass.existsChildNode(XMLclass.configFile, XMLclass.emailService, emailCredential, "filter" + s,
				filterAttr))
			XMLclass.addChild(XMLclass.configFile, XMLclass.emailService, emailCredential, "filter" + s, filterAttr);
		return nova;
	}

	/**
	 * Procedimento que filtra os emails de um utilizador consoante os emails que pertencem ao utilizador pedido
	 * @param text String
	 * @return lista dos emails do utilizador filtrada
	 */
	public ObservableList<Mensagem> filterUser(String text) {
		try {
			
			ObservableList<Mensagem> nova = FXCollections.observableArrayList();
			 for (int i = 0; i < emails.size(); i++) {
		            if(emails.get(i).getFrom_to().contains(text) ) {
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
	 * Procedimento que filtra os emails de um utilizador das ultimas 24 horas
	 * @return lista dos emails do utilizador filtrada
	 */
	public ObservableList<Mensagem> getLast24h() {
		try {
			
			ObservableList<Mensagem> nova = FXCollections.observableArrayList();
			 for (int i = 0; i < emails.size(); i++) {
				 DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
				 Date date = format.parse(emails.get(i).getDate());
				 Date today = DateFormat.getDateTimeInstance().getCalendar().getTime();
				 Date twentyfourhoursbefore = DateFormat.getDateTimeInstance().getCalendar().getTime();
				 twentyfourhoursbefore.setTime(twentyfourhoursbefore.getTime() - (24*60*60*1000));
				    if(date.after(twentyfourhoursbefore) && date.before(today)){
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
	
	public static boolean sendEmails(String to, String sub, String text, Credential emailCredential) {
		System.out.println(to + sub + text);
		// Sender's email ID needs to be mentioned

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "outlook.office365.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(emailCredential.username, emailCredential.password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(emailCredential.username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(sub);
			message.setText(text);

			Transport.send(message);

			System.out.println("Done");
			System.out.println("Sent message successfully....");
			return true;
		} catch (MessagingException mex) {
			mex.printStackTrace();
			return false;
		}
	}

	/**
	 * Devolve o folder atual
	 * 
	 * @return String folder
	 */
	public static String getFolder() {
		return folder;
	}

	/**
	 * Muda o folder para o folder dado como parametro
	 * 
	 * @param folder
	 *            String
	 */
	public static void setFolder(String folder) {
		Email.folder = folder;
	}

	/**
	 * Retorna o destinatario de uma possivel mensagem
	 * 
	 * @return String to
	 */
	public static String getTo() {
		return to;
	}

	/**
	 * Altera o destinatario de uma possivel mensagem
	 * 
	 * @param to  String
	 *           
	 */
	public static void setTo(String to) {
		Email.to = to;
	}

	/**
	 * devolve as credencias do email
	 * @return Credential
	 */
	public Credential getCredential() {
		return this.emailCredential;
	}
}
