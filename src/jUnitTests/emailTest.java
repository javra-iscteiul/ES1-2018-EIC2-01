package jUnitTests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import BDA.Credential;
import BDA.XMLclass;
import BDA.Email.Email;

public class emailTest {
	private static Email email = new Email();
	
	@Test
    public void getTimeline() {
		Credential credTest = new Credential(
				XMLclass.getNodeList(XMLclass.configFile, XMLclass.emailService).item(0).getAttributes());
		assertTrue(XMLclass.existsNode(XMLclass.configFile, XMLclass.emailService, credTest));
		assertTrue(XMLclass.existsNode(XMLclass.storedDataFile, "emailSent", credTest));
		assertTrue(XMLclass.existsNode(XMLclass.storedDataFile, "emailInbox", credTest));
		email.init(credTest);
		assertNotNull(email.getTimeLine());
    }
}
