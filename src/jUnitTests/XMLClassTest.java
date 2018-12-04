package jUnitTests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import javax.xml.XMLConstants;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import BDA.Credential;
import BDA.XMLclass;

public class XMLClassTest {
	
	public static Credential credentialsTest = new Credential("protocolTest",
			"usernameTest",
			"passwordTest",
			"consumerKeyTest",
			"consumerSecretTest",
			"tokenTest",
			"tokensecretTest");
	
	public static String serviceTest = "serviceTest";
	public static String serviceChildTest = "serviceChildTest";
	
	@BeforeClass
	public static void addNodeTest() throws Exception{		
		//apaga se por erro tivesse sido deixado no config uma tag com o mesmo nome
		assertFalse(XMLclass.deleteNode(XMLclass.testFile, serviceTest, credentialsTest));
		
		assertTrue(XMLclass.addNode(XMLclass.testFile, serviceTest, credentialsTest));
		Map<String, String> filterTestAttributes = new HashMap<>();
		filterTestAttributes.put("valueTest", "Test");
		assertTrue(XMLclass.addChild(XMLclass.testFile, serviceTest, credentialsTest, "childTest", filterTestAttributes));
		assertTrue(XMLclass.setLogin(XMLclass.testFile, serviceTest, credentialsTest, "True"));
	}
	
	@Before
	public void existsNodeTest() throws Exception {
		//verifica se o servico criado existe
		assertTrue(XMLclass.existsNode(XMLclass.testFile, serviceTest, credentialsTest));
	}
	
	@Before
	public void existsChildNodeTest() throws Exception {
		//verifica se o servico criado existe
		Map<String, String> filterTestAttributes = new HashMap<>();
		filterTestAttributes.put("valueTest", "Test");
		assertTrue(XMLclass.existsChildNode(XMLclass.testFile, serviceTest, credentialsTest, "childTest", filterTestAttributes));
	}
	
	@Test
	public void getNodeTest() throws Exception{
		//verifica se consegue receber o serviço teste
		assertNotNull(XMLclass.getNode(XMLclass.testFile, serviceTest, credentialsTest));
	}
	
	@Test
	public void getChildNodeTest() throws Exception{
		Map<String, String> filterTestAttributes = new HashMap<>();
		filterTestAttributes.put("valueTest", "Test");
		//verifica se consegue receber o serviço teste
		assertNotNull(XMLclass.getChildNode(XMLclass.testFile, serviceTest, credentialsTest, "childTest", filterTestAttributes));
	}
	
	@Test
	public void addNodeAndChildTest() throws Exception{
		//apaga se por erro tivesse sido deixado no config uma tag com o mesmo nome
		assertFalse(XMLclass.deleteNode(XMLclass.testFile, serviceChildTest, credentialsTest));
			
		//cria 5 filhos do serviço de teste
		Map<String, Map<String, String>> serviceChilds = new HashMap<String, Map<String, String>>();
		for(int i = 0; i < 5; i++){
			Map<String, String> childAttributes = new HashMap<String, String>();
			childAttributes.put("Attr1", "teste");
			childAttributes.put("Attr2", "123");
			serviceChilds.put("child" + i, childAttributes);
		}
		
		//adiciona o serviço e os 5 filhos e verifica que consegue inserir
		assertTrue(XMLclass.addNodeAndChild(XMLclass.testFile, serviceChildTest, credentialsTest, serviceChilds));
	}
	
	@Test
	public void getLoginTest() throws Exception{
		//verifica se consegue apagar os serviços criados
		assertFalse(XMLclass.existsLogin(XMLclass.testFile, ""));
		assertNull(XMLclass.getLogin(XMLclass.testFile, ""));
		assertTrue(XMLclass.existsLogin(XMLclass.testFile, serviceTest));
		assertNotNull(XMLclass.getLogin(XMLclass.testFile, serviceTest));
	}
	
	@Test
	public void deleteChildTest() throws Exception{
		//verifica se consegue apagar os serviços criados
		Map<String, String> filterTestAttributes = new HashMap<>();
		filterTestAttributes.put("valueTest", "Test");
		assertTrue(XMLclass.deleteChild(XMLclass.testFile, serviceTest, credentialsTest, "childTest", filterTestAttributes));
	}
	
	@AfterClass
	public static void deleteNodeTest() throws Exception{
		//verifica se consegue apagar os serviços criados
		assertTrue(XMLclass.deleteNode(XMLclass.testFile, serviceTest, credentialsTest));
		assertTrue(XMLclass.deleteNode(XMLclass.testFile, serviceChildTest, credentialsTest));
	}
}
