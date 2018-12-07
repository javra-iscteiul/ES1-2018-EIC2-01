package jUnitTests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.w3c.dom.NodeList;

import BDA.Credential;
import BDA.XMLclass;
import BDA.Twitter.App_twitter;
import javafx.scene.control.ListView;
import twitter4j.TwitterException;

public class TestTwitter {
	private static App_twitter twitter = new App_twitter();

	@Test
	public void getTimeline() throws Exception {
		Credential credTest = new Credential(
				XMLclass.getNodeList(XMLclass.configFile, XMLclass.twitterService).item(0).getAttributes());
		assertTrue(XMLclass.existsNode(XMLclass.configFile, XMLclass.twitterService, credTest));
		assertTrue(XMLclass.existsNode(XMLclass.storedDataFile, XMLclass.twitterService, credTest));
		// se tiver data guardada tem que dar sempre true
		twitter.init(credTest);
		assertNotNull(twitter.getTimeLine());
		
		assertNotNull(twitter.filter_users(" "));
		assertNotNull(twitter.timeFilter("lastDay"));
		assertNotNull(twitter.timeFilter("lastMonth"));
		assertNotNull(twitter.getCredential());
		assertTrue(twitter.solveConectionProblems());
	}
}