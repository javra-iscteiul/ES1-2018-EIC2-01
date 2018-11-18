package jUnitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import BDA.XMLclass;
import BDA.Twitter.App_twitter;

public class TestTwitter {

	@Test
	public void getTimeline() {
		if(!XMLclass.existsElement("twitter")){
			XMLclass.addElement("twitter", "smtp","EsJarh","grupo1grupo"
					,"yPv2NQ8ozCWIQ1jZeXjWLGUce", "w7lfg9hNlQ8qFAfb5k7fMtzdiYhqhBFe5S6PNu0PfTy0FL6Vo8",
					"1051761005406154752-yRmIyBEYTX21kensmMUAvpNVRfC15Q","F7mHLVxLhBOG3OHELLvYG5etmlIFtnXnNStgnlpHCShLX");
		}
		assertNotNull(App_witter.getTimeline());
	}

}
