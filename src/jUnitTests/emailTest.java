package jUnitTests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import BDA.XMLclass;
import BDA.Email.Email;

public class emailTest {
	private static Email email = new Email();
	
	@Test
    public void getTimeline() {
		assertTrue(XMLclass.existsNode(XMLclass.configFile, "email"));
		assertTrue(XMLclass.existsNode(XMLclass.storedDataFile, "emailSent"));
		assertTrue(XMLclass.existsNode(XMLclass.storedDataFile, "emailInbox"));
		email.init();
		assertNotNull(email.getTimeLine());
    }
}
