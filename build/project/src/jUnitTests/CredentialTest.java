package jUnitTests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import BDA.Credential;
import BDA.XMLclass;
import BDA.Email.Email;

public class CredentialTest {
	
	@Test
    public void testCredentials() throws Exception {
		Credential cred1 = new Credential("protocol", "user", "pass", "consumerkey", "consumerSecret", "accesstoken", "accesstokenSecret", "group");
		Credential cred2 = new Credential("protocol", "user", "pass", "consumerkey", "consumerSecret", "accesstoken", "accesstokenSecret");
		Credential cred3 = new Credential("user1", "pass1");
		Credential cred4 = new Credential(XMLclass.getNodeList(XMLclass.configFile, XMLclass.facebookService).item(0).getAttributes());
		
		assertNotNull(cred1);
		assertNotNull(cred2);
		assertNotNull(cred3);
		assertNotNull(cred4);
		
		assertNotNull(cred1.getProtocolo());
		cred1.setProtocolo("protocolTeste");
		assertEquals("protocolTeste", cred1.getProtocolo());
		
		assertNotNull(cred1.getUsername());
		cred1.setUsername("usernameTeste");
		assertEquals("usernameTeste", cred1.getUsername());
		
		assertNotNull(cred1.getPassword());
		cred1.setPassword("passTeste");
		assertEquals("passTeste", cred1.getPassword());
		
		assertNotNull(cred1.getConsumerKey());
		cred1.setConsumerKey("consumerkeyTeste");
		assertEquals("consumerkeyTeste", cred1.getConsumerKey());
		
		assertNotNull(cred1.getConsumerSecret());
		cred1.setConsumerSecret("consumersecretTeste");
		assertEquals("consumersecretTeste", cred1.getConsumerSecret());
		
		assertNotNull(cred1.getAccessToken());
		cred1.setAccessToken("accesstokenTeste");
		assertEquals("accesstokenTeste", cred1.getAccessToken());
		
		assertNotNull(cred1.getAccessTokenSecret());
		cred1.setAccessTokenSecret("accesstokensecretTeste");
		assertEquals("accesstokensecretTeste", cred1.getAccessTokenSecret());
		
		assertNotNull(cred1.getGroup());
		cred1.setGroup("groupTeste");
		assertEquals("groupTeste", cred1.getGroup());
		
		assertNotNull(cred1.getLogin());
		cred1.setLogin("True");
		assertEquals("True", cred1.getLogin());
		
		assertNotNull(cred1.toString());
		
		assertTrue(cred1.equals(cred1));
		assertFalse(cred1.equals(cred3));
		
    }
}