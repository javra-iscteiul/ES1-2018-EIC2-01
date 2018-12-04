package jUnitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import BDA.Credentials;
import BDA.XMLclass;

public class CredentialsTest {
	@Test
	public void getCredentials() throws Exception{
		Credentials cred = new Credentials();
		assertNotNull(cred.getCredentials(XMLclass.facebookService));
		assertNotNull(cred.getCredentials(XMLclass.twitterService));
		assertNotNull(cred.getCredentials(XMLclass.emailService));
	}
}
