package jUnitTests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import BDA.XMLclass;
import BDA.Facebook.Facebook;

public class facebookTest {
	@Test
    public void getTimeline() {
		if(!XMLclass.existsElement(XMLclass.configFile, "facebookProfile")){
			assertNull(new Facebook().getTimeLine());
			
			Map<String, String> attributes = new HashMap<String, String>();
			attributes.put("UserName", "EsJarh");
			attributes.put("Password", "grupo1grupo");
			attributes.put("AccessToken", "EAAEq0X5xdpMBAOzHJoC0VA7aUgvTaQUkuwpMHxVaPR3JDZBIECyEv8DTbv3k5Bbsi5JJo7ZALaJsCheHNQle5bHd328RsQSAZCMfVcL0TM9xLEK7EZA7UBlk6zqf0cUrT0CkYuOHjQK13qk3PAAdk5T0wdZAfAoEBy92hMctTpwZDZD");
			attributes.put("AccessTokenSecret", "1051761005406154752-F7mHLVxLhBOG3OHELLvYG5etmlIFtnXnNStgnlpHCShLX");
			
			XMLclass.addElement(XMLclass.configFile, "facebook", attributes);
			
		}
		assertNotNull(new Facebook().getTimeLine());
    }
}
