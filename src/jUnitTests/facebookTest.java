package jUnitTests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import BDA.Credential;
import BDA.XMLclass;
import BDA.Facebook.Facebook;
import BDA.Facebook.MensagemFacebook;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class facebookTest {
	private static Facebook facebook = new Facebook();
	
	@Test
    public void getTimeline() throws Exception {
		Credential credTest = new Credential(
				XMLclass.getNodeList(XMLclass.configFile, XMLclass.facebookService).item(0).getAttributes());
		assertTrue(XMLclass.existsNode(XMLclass.configFile, XMLclass.facebookService, credTest));
		assertTrue(XMLclass.existsNode(XMLclass.storedDataFile, XMLclass.facebookService, credTest));
		//se tiver data guardada tem que dar sempre true
		facebook.init(credTest);
		assertNotNull(facebook.getTimeLine());
		assertNotNull(facebook.getStoredTimeLine());
		assertNotNull(facebook.getCredential());
		
		assertNotNull(facebook.setFilter("a"));
		assertNotNull(facebook.setUserFilter("a"));
		assertNotNull(facebook.getLast("24h"));
		assertNotNull(facebook.getLast("week"));
		assertNotNull(facebook.getLast("month"));
    }
}
