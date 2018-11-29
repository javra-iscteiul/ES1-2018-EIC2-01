package jUnitTests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import BDA.XMLclass;
import BDA.Facebook.Facebook;
import BDA.Facebook.IFacebook;
import BDA.Facebook.Message;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class facebookTest {
	private static IFacebook facebook = new Facebook();
	
	@Test
    public void getTimeline() {
		assertTrue(XMLclass.existsNode(XMLclass.configFile, "facebook"));
		assertTrue(XMLclass.existsNode(XMLclass.storedDataFile, "facebook"));
		//se tiver data guardada tem que dar sempre true
		assertNotNull(facebook.getTimeLine());
    }
}
