package jUnitTests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import BDA.XMLclass;

public class xmlClass {
	
	@BeforeClass
	public static void addElementTest(){
		//apaga se por erro tivesse sido deixado no config uma tag com o mesmo nome
		if(XMLclass.existsElement("serviceTest"))
			XMLclass.deleteElement("serviceTest");
			
		assertTrue(XMLclass.addElement("serviceTest", "protocolTest", "usernameTest", "passwordTest", "consumerKeyTest",
				"consumerSecretTest", "tokenTest", "tokensecretTest"));
		assertFalse(XMLclass.addElement(null, null, null, null, null, null, null, null));
	}

	@Before
	public void existsElementTest() {
		assertTrue(XMLclass.existsElement("serviceTest"));
		assertFalse(XMLclass.existsElement(""));
		assertFalse(XMLclass.existsElement(null));
	}

	@Test
	public void verifyElementAttributesUnchangedTest(){
		Map<String, String> Attributes = new HashMap();
		Attributes.put("Protocol", "protocolTest");
		Attributes.put("UserName", "usernameTest");
		Attributes.put("Password", "passwordTest");
		Attributes.put("ConsumerKey", "consumerKeyTest");
		Attributes.put("ConsumerSecret", "consumerSecretTest");
		Attributes.put("AccessToken", "tokenTest");
		Attributes.put("AccessTokenSecret", "tokensecretTest");
		assertTrue(XMLclass.verifyElementAttributesUnchanged("serviceTest", Attributes));
						
		//funções que esperam excepção ficam para ultimo
		assertFalse(XMLclass.verifyElementAttributesUnchanged("", null));
		
		Map<String, String> AttributesFail = new HashMap();
		AttributesFail.put("Protocol", "protocolTest");
		AttributesFail.put("UserName", "usernameTest");
		AttributesFail.put("Password", "passwordTest");
		AttributesFail.put("ConsumerKey", "consumerKeyTestFail");
		AttributesFail.put("ConsumerSecret", "consumerSecretTest");
		AttributesFail.put("AccessToken", "tokenTest");
		AttributesFail.put("AccessTokenSecret", "tokensecretTest");
		assertFalse(XMLclass.verifyElementAttributesUnchanged("serviceTest", AttributesFail));
	}
	
	@Test
	public void getElementTest(){
		assertNotNull(XMLclass.getElement("serviceTest"));
		assertNull(XMLclass.getElement(""));
		assertNull(XMLclass.getElement(null));
	}
	
	@AfterClass
	public static void deleteElementTest(){
		assertTrue(XMLclass.existsElement("serviceTest"));
		assertFalse(XMLclass.existsElement(""));
		assertFalse(XMLclass.existsElement(null));
	}
	
}
