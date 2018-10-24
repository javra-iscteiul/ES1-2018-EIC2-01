package BDA.Email;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

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
	
	/**
	 * Este método permite que seja obtida uma lista dos emails de um utilizador (interface)
	 * @return	retorna uma lista dos emails do utilizador
	 */
	public static List<String> getTimeline() {
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
			Node emailConfig = XMLclass.getElement("email");
			store.connect(emailConfig.getAttributes().getNamedItem("UserName").getNodeValue(),
					emailConfig.getAttributes().getNamedItem("Password").getNodeValue());
			
			/* open Inbox folder */
			Folder emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.READ_ONLY);

			Message[] messages = emailFolder.getMessages();
			if (messages.length == 0) {
				System.out.println("Your Inbox is empty!");
			}
			
			// retrieve the messages from the folder in an array and print it
			System.out.println("messages.length---" + messages.length);

			List<String> emails = new ArrayList<String>();
			for (int i = 0 ; i < messages.length; i++) {
				Message message = messages[i];
				System.out.println("---------------------------------");
				System.out.println("Email Number " + (i + 1));
				System.out.println("Subject: " + message.getSubject());
				System.out.println("From: " + message.getFrom()[0]);
				String s = "Email Number " + (i + 1) + "Subject: "
						+ message.getSubject() + "From: " + message.getFrom()[0] + "From: " + message.getFrom()[0];

				emails.add(s);
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
}
