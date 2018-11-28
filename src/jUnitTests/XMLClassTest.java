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
	public static void addNodeTest(){
		//apaga se por erro tivesse sido deixado no config uma tag com o mesmo nome
		assertFalse(XMLclass.deleteNode(XMLclass.testFile, "serviceTest"));
			
		//cria um serviço teste no xml teste
		Map<String, String> attributes = new HashMap<String, String>();
		attributes.put("Protocol", "protocolTest");
		attributes.put("UserName", "usernameTest");
		attributes.put("Password", "passwordTest");
		attributes.put("ConsumerKey", "consumerKeyTest");
		attributes.put("ConsumerSecret", "consumerSecretTest");
		attributes.put("AccessToken", "tokenTest");
		attributes.put("AccessTokenSecret", "tokensecretTest");
		
		assertTrue(XMLclass.addNode(XMLclass.testFile, "serviceTest", attributes));
		Map<String, String> filterTestAttributes = new HashMap<>();
		filterTestAttributes.put("valueTest", "Test");
		assertTrue(XMLclass.addChild(XMLclass.testFile, "serviceTest", "childTest", filterTestAttributes));
		
		assertFalse(XMLclass.addNode(XMLclass.testFile, null, null));
		assertFalse(XMLclass.addChild(XMLclass.testFile, null, null, null));
	}
	
	@Before
	public void existsNodeTest() {
		//verifica se o servico criado existe
		assertTrue(XMLclass.existsNode(XMLclass.testFile, "serviceTest"));
		assertFalse(XMLclass.existsNode(XMLclass.testFile, ""));
		assertFalse(XMLclass.existsNode(XMLclass.testFile, null));
	}
	
	@Before
	public void existsChildNodeTest() {
		//verifica se o servico criado existe
		Map<String, String> filterTestAttributes = new HashMap<>();
		filterTestAttributes.put("valueTest", "Test");
		assertTrue(XMLclass.existsChildNode(XMLclass.testFile, "serviceTest", "childTest", filterTestAttributes));
		assertFalse(XMLclass.existsChildNode(XMLclass.testFile, "", "", null));
		assertFalse(XMLclass.existsChildNode(XMLclass.testFile, null, null, null));
	}

	@Test
	public void verifyNodeAttributesUnchangedTest(){
		//verifica se deteta que os atributos são iguais aos que estão no ficheiro xml
		Map<String, String> Attributes = new HashMap<String, String>();
		Attributes.put("Protocol", "protocolTest");
		Attributes.put("UserName", "usernameTest");
		Attributes.put("Password", "passwordTest");
		Attributes.put("ConsumerKey", "consumerKeyTest");
		Attributes.put("ConsumerSecret", "consumerSecretTest");
		Attributes.put("AccessToken", "tokenTest");
		Attributes.put("AccessTokenSecret", "tokensecretTest");
		assertTrue(XMLclass.verifyNodeAttributesUnchanged(XMLclass.testFile, "serviceTest", Attributes));
						
		//funções que esperam excepção ficam para ultimo
		assertFalse(XMLclass.verifyNodeAttributesUnchanged(XMLclass.testFile, "", null));
		
		//verifica que atributos são diferentes, aos que estão no ficheiro xml
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
	public void getNodeTest(){
		//verifica se consegue receber o serviço teste
		assertNotNull(XMLclass.getNode(XMLclass.testFile, "serviceTest"));
		assertNull(XMLclass.getNode(XMLclass.testFile, ""));
		assertNull(XMLclass.getNode(XMLclass.testFile, null));
	}
	
	@Test
	public void getChildNodeTest(){
		Map<String, String> filterTestAttributes = new HashMap<>();
		filterTestAttributes.put("valueTest", "Test");
		//verifica se consegue receber o serviço teste
		assertNotNull(XMLclass.getChildNode(XMLclass.testFile, "serviceTest", "childTest", filterTestAttributes));
		assertNull(XMLclass.getChildNode(XMLclass.testFile, "", "", null));
		assertNull(XMLclass.getChildNode(XMLclass.testFile, null, null, null));
	}
	
	@Test
	public void addNodeAndChildTest(){
		//apaga se por erro tivesse sido deixado no config uma tag com o mesmo nome
		assertFalse(XMLclass.deleteNode(XMLclass.testFile, "serviceTestWithChild"));
			
		//cria 5 filhos do serviço de teste
		Map<String, Map<String, String>> serviceChilds = new HashMap<String, Map<String, String>>();
		for(int i = 0; i < 5; i++){
			Map<String, String> childAttributes = new HashMap<String, String>();
			childAttributes.put("Attr1", "teste");
			childAttributes.put("Attr2", "123");
			serviceChilds.put("child" + i, childAttributes);
		}
		
		//adiciona o serviço e os 5 filhos e verifica que consegue inserir
		assertTrue(XMLclass.addNodeAndChild(XMLclass.testFile, "serviceTestWithChild", serviceChilds));
		assertFalse(XMLclass.addNodeAndChild(XMLclass.testFile, null, null));
	}
	
	@Test
	public void deleteChildTest(){
		//verifica se consegue apagar os serviços criados
		assertFalse(XMLclass.deleteChild(XMLclass.testFile, "", "", null));
		assertFalse(XMLclass.deleteChild(XMLclass.testFile, null, null, null));
		Map<String, String> filterTestAttributes = new HashMap<>();
		filterTestAttributes.put("valueTest", "Test");
		assertTrue(XMLclass.deleteChild(XMLclass.testFile, "serviceTest", "childTest", filterTestAttributes));
	}
	
	@AfterClass
	public static void deleteNodeTest(){
		//verifica se consegue apagar os serviços criados
		assertFalse(XMLclass.deleteNode(XMLclass.testFile, ""));
		assertFalse(XMLclass.deleteNode(XMLclass.testFile, null));
		assertTrue(XMLclass.deleteNode(XMLclass.testFile, "serviceTest"));
		assertTrue(XMLclass.deleteNode(XMLclass.testFile, "serviceTestWithChild"));
	}
}
