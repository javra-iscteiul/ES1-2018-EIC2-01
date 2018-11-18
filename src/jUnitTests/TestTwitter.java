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

	@Test
	public void getTimeline() {
		if(!XMLclass.existsElement(XMLclass.configFile, "twitter")){
			Map<String, String> attributes = new HashMap<String, String>();
			attributes.put("Protocol", "smtp");
			attributes.put("UserName", "EsJarh");
			attributes.put("Password", "grupo1grupo");
			attributes.put("ConsumerKey", "yPv2NQ8ozCWIQ1jZeXjWLGUce");
			attributes.put("ConsumerSecret", "w7lfg9hNlQ8qFAfb5k7fMtzdiYhqhBFe5S6PNu0PfTy0FL6Vo8");
			attributes.put("AccessToken", "1051761005406154752-yRmIyBEYTX21kensmMUAvpNVRfC15Q");
			attributes.put("AccessTokenSecret", "1051761005406154752-F7mHLVxLhBOG3OHELLvYG5etmlIFtnXnNStgnlpHCShLX");
			
			XMLclass.addElement(XMLclass.configFile, "twitter", attributes);
		}
		ListView<String> tweetsList = new ListView<String>();
		//App_twitter.init();
	//	App_twitter tt = ;
		try {
			assertNotNull(new App_twitter().getTimeline(tweetsList));
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}