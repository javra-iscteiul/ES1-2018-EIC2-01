package jUnitTests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import BDA.Credential;
import BDA.XMLclass;
import BDA.Email.Email;

public class emailTest {
	
	@Test
    public void getTimeline() throws Exception {
		Email email = new Email();
		Credential credTest = new Credential(
				XMLclass.getLogin(XMLclass.configFile, XMLclass.emailService).getAttributes());
		assertTrue(XMLclass.existsNode(XMLclass.configFile, XMLclass.emailService, credTest));
		assertTrue(XMLclass.existsNode(XMLclass.storedDataFile, "emailSent", credTest));
		assertTrue(XMLclass.existsNode(XMLclass.storedDataFile, "emailInbox", credTest));
		email.init(credTest);
		Email.setFolder("Sent");
		assertNotNull(email.getTimeLine());
		assertNotNull(email.getStoredTimeLine());
		Email.setFolder("INBOX");
		assertNotNull(email.getTimeLine());
		assertNotNull(email.getStoredTimeLine());
		
		assertNotNull(email.setFilter("a"));
		assertNotNull(email.filterUser("a"));
		assertNotNull(email.getLast("24h"));
		assertNotNull(email.getLast("week"));
		assertNotNull(email.getLast("month"));
		Email.setTo("ola");
		assertNotNull(Email.getTo());
		assertEquals("ola", Email.getTo());
		assertNotNull(email.getCredential());
		assertEquals(credTest, email.getCredential());
		assertFalse(Email.sendEmails(Email.getTo(), "subject", "text", credTest));
    }
}
