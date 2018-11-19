package jUnitTests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import javax.xml.XMLConstants;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import BDA.XMLclass;

public class xmlClass {
	
	@BeforeClass
	public static void addElementTest(){
		//apaga se por erro tivesse sido deixado no config uma tag com o mesmo nome
		if(XMLclass.existsElement(XMLclass.configFile, "serviceTest"))
			XMLclass.deleteElement(XMLclass.configFile, "serviceTest");
			
		Map<String, String> attributes = new HashMap<String, String>();
		attributes.put("Protocol", "protocolTest");
		attributes.put("UserName", "usernameTest");
		attributes.put("Password", "passwordTest");
		attributes.put("ConsumerKey", "consumerKeyTest");
		attributes.put("ConsumerSecret", "consumerSecretTest");
		attributes.put("AccessToken", "tokenTest-yRmIyBEYTX21kensmMUAvpNVRfC15Q");
		attributes.put("AccessTokenSecret", "tokensecretTest-F7mHLVxLhBOG3OHELLvYG5etmlIFtnXnNStgnlpHCShLX");
		
		assertTrue(XMLclass.addElement(XMLclass.configFile, "serviceTest", attributes));
		assertFalse(XMLclass.addElement(XMLclass.configFile, null, null));
	}

	@Before
	public void existsElementTest() {
		assertTrue(XMLclass.existsElement(XMLclass.configFile, "serviceTest"));
		assertFalse(XMLclass.existsElement(XMLclass.configFile, ""));
		assertFalse(XMLclass.existsElement(XMLclass.configFile, null));
	}

	@Test
	public void verifyElementAttributesUnchangedTest(){
		Map<String, String> Attributes = new HashMap<String, String>();
		Attributes.put("Protocol", "protocolTest");
		Attributes.put("UserName", "usernameTest");
		Attributes.put("Password", "passwordTest");
		Attributes.put("ConsumerKey", "consumerKeyTest");
		Attributes.put("ConsumerSecret", "consumerSecretTest");
		Attributes.put("AccessToken", "tokenTest");
		Attributes.put("AccessTokenSecret", "tokensecretTest");
		assertTrue(XMLclass.verifyElementAttributesUnchanged(XMLclass.configFile, "serviceTest", Attributes));
						
		//funções que esperam excepção ficam para ultimo
		assertFalse(XMLclass.verifyElementAttributesUnchanged(XMLclass.configFile, "", null));
		
		Map<String, String> AttributesFail = new HashMap<String, String>();
		AttributesFail.put("Protocol", "protocolTest");
		AttributesFail.put("UserName", "usernameTest");
		AttributesFail.put("Password", "passwordTest");
		AttributesFail.put("ConsumerKey", "consumerKeyTestFail");
		AttributesFail.put("ConsumerSecret", "consumerSecretTest");
		AttributesFail.put("AccessToken", "tokenTest");
		AttributesFail.put("AccessTokenSecret", "tokensecretTest");
		assertFalse(XMLclass.verifyElementAttributesUnchanged(XMLclass.configFile, "serviceTest", AttributesFail));
	}
	
	@Test
	public void getElementTest(){
		assertNotNull(XMLclass.getElement(XMLclass.configFile, "serviceTest"));
		assertNull(XMLclass.getElement(XMLclass.configFile, ""));
		assertNull(XMLclass.getElement(XMLclass.configFile, null));
	}
	
	@AfterClass
	public static void deleteElementTest(){
		assertTrue(XMLclass.existsElement(XMLclass.configFile, "serviceTest"));
		assertFalse(XMLclass.existsElement(XMLclass.configFile, ""));
		assertFalse(XMLclass.existsElement(XMLclass.configFile, null));
	}
	
}
