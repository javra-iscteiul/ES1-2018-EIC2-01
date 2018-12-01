package jUnitTests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import BDA.XMLclass;
import BDA.Twitter.App_twitter;
import javafx.scene.control.ListView;
import twitter4j.TwitterException;

public class TestTwitter {
	private static App_twitter twitter = new App_twitter();
	@Test
	public void getTimeline() {
		try {
			twitter.init();
			assertTrue(XMLclass.existsNode(XMLclass.configFile, "twitter"));
			assertTrue(XMLclass.existsNode(XMLclass.storedDataFile, "twitter"));
			//se tiver data guardada tem que dar sempre true
			assertNotNull(twitter.getTimeLine());
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}