package jUnitTests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import BDA.XMLclass;
import BDA.Twitter.App_twitter;
import twitter4j.TwitterException;
import javafx.scene.control.ListView;

public class TestTwitter {

	@Test
	public void getTimeline() {
		if(!XMLclass.existsElement(XMLclass.configFile, "twitter")){
			Map<String, String> attributes = new HashMap<String, String>();
			attributes.put("UserName", "EsJarh");
			attributes.put("Password", "grupo1grupo");
			attributes.put("AccessToken", "EAAEq0X5xdpMBAOzHJoC0VA7aUgvTaQUkuwpMHxVaPR3JDZBIECyEv8DTbv3k5Bbsi5JJo7ZALaJsCheHNQle5bHd328RsQSAZCMfVcL0TM9xLEK7EZA7UBlk6zqf0cUrT0CkYuOHjQK13qk3PAAdk5T0wdZAfAoEBy92hMctTpwZDZD");
			attributes.put("AccessTokenSecret", "1051761005406154752-F7mHLVxLhBOG3OHELLvYG5etmlIFtnXnNStgnlpHCShLX");
			
			XMLclass.addElement(XMLclass.configFile, "twitter", attributes);
		}
		try {
			assertNotNull(new App_twitter().getTimeline(null));
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
