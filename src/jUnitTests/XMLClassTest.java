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

public class XMLClassTest {
	
	@BeforeClass
	public static void addElementTest(){
		//apaga se por erro tivesse sido deixado no config uma tag com o mesmo nome
		if(XMLclass.existsNode(XMLclass.testFile, "serviceTest"))
			XMLclass.deleteNode(XMLclass.testFile, "serviceTest");
			
		//cria um servi�o teste no xml teste
		Map<String, String> attributes = new HashMap<String, String>();
		attributes.put("Protocol", "protocolTest");
		attributes.put("UserName", "usernameTest");
		attributes.put("Password", "passwordTest");
		attributes.put("ConsumerKey", "consumerKeyTest");
		attributes.put("ConsumerSecret", "consumerSecretTest");
		attributes.put("AccessToken", "tokenTest");
		attributes.put("AccessTokenSecret", "tokensecretTest");
		
		assertTrue(XMLclass.addElement(XMLclass.testFile, "serviceTest", attributes));
		assertFalse(XMLclass.addElement(XMLclass.testFile, null, null));
	}

	@Before
	public void existsElementTest() {
		//verifica se o servico criado existe
		assertTrue(XMLclass.existsNode(XMLclass.testFile, "serviceTest"));
		assertFalse(XMLclass.existsNode(XMLclass.testFile, ""));
		assertFalse(XMLclass.existsNode(XMLclass.testFile, null));
	}

	@Test
	public void verifyElementAttributesUnchangedTest(){
		//verifica se deteta que os atributos s�o iguais aos que est�o no ficheiro xml
		Map<String, String> Attributes = new HashMap<String, String>();
		Attributes.put("Protocol", "protocolTest");
		Attributes.put("UserName", "usernameTest");
		Attributes.put("Password", "passwordTest");
		Attributes.put("ConsumerKey", "consumerKeyTest");
		Attributes.put("ConsumerSecret", "consumerSecretTest");
		Attributes.put("AccessToken", "tokenTest");
		Attributes.put("AccessTokenSecret", "tokensecretTest");
		assertTrue(XMLclass.verifyNodeAttributesUnchanged(XMLclass.testFile, "serviceTest", Attributes));
						
		//fun��es que esperam excep��o ficam para ultimo
		assertFalse(XMLclass.verifyNodeAttributesUnchanged(XMLclass.testFile, "", null));
		
		//verifica que atributos s�o diferentes, aos que est�o no ficheiro xml
		Map<String, String> AttributesFail = new HashMap<String, String>();
		AttributesFail.put("Protocol", "protocolTest");
		AttributesFail.put("UserName", "usernameTest");
		AttributesFail.put("Password", "passwordTest");
		AttributesFail.put("ConsumerKey", "consumerKeyTestFail");//atributo diferente
		AttributesFail.put("ConsumerSecret", "consumerSecretTest");
		AttributesFail.put("AccessToken", "tokenTest");
		AttributesFail.put("AccessTokenSecret", "tokensecretTest");
		assertFalse(XMLclass.verifyNodeAttributesUnchanged(XMLclass.testFile, "serviceTest", AttributesFail));
	}
	
	@Test
	public void getElementTest(){
		//verifica se consegue receber o servi�o teste
		assertNotNull(XMLclass.getNode(XMLclass.testFile, "serviceTest"));
		assertNull(XMLclass.getNode(XMLclass.testFile, ""));
		assertNull(XMLclass.getNode(XMLclass.testFile, null));
	}
	
	@Test
	public void addElementAndChildTest(){
		//apaga se por erro tivesse sido deixado no config uma tag com o mesmo nome
		if(XMLclass.existsNode(XMLclass.testFile, "serviceTestWithChild"))
			XMLclass.deleteNode(XMLclass.testFile, "serviceTestWithChild");
			
		//cria 5 filhos do servi�o de teste
		Map<String, Map<String, String>> serviceChilds = new HashMap<String, Map<String, String>>();
		for(int i = 0; i < 5; i++){
			Map<String, String> childAttributes = new HashMap<String, String>();
			childAttributes.put("Attr1", "teste");
			childAttributes.put("Attr2", "123");
			serviceChilds.put("child" + i, childAttributes);
		}
		
		//adiciona o servi�o e os 5 filhos e verifica que consegue inserir
		assertTrue(XMLclass.addElementAndChild(XMLclass.testFile, "serviceTestWithChild", serviceChilds));
		assertFalse(XMLclass.addElementAndChild(XMLclass.testFile, null, null));
	}
	
	@AfterClass
	public static void deleteElementTest(){
		//verifica se consegue apagar os servi�os criados
		assertFalse(XMLclass.deleteNode(XMLclass.testFile, ""));
		assertFalse(XMLclass.deleteNode(XMLclass.testFile, null));
		assertTrue(XMLclass.deleteNode(XMLclass.testFile, "serviceTest"));
		assertTrue(XMLclass.deleteNode(XMLclass.testFile, "serviceTestWithChild"));
	}
	
}
