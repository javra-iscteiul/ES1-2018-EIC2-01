package jUnitTests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import BDA.XMLclass;
import BDA.Facebook.Facebook;

public class facebookTest {
	@Test
    public void getTimeline() {
		if(!XMLclass.existsElement("facebookProfile")){
			//assertNull(Facebook.getTimeline());
			XMLclass.addElement("facebook", "smtp","EsJarh","grupo1grupo"
		    		,"", "","EAAEq0X5xdpMBAOzHJoC0VA7aUgvTaQUkuwpMHxVaPR3JDZBIECyEv8DTbv3k5Bbsi5JJo7ZALaJsCheHNQle5bHd328RsQSAZCMfVcL0TM9xLEK7EZA7UBlk6zqf0cUrT0CkYuOHjQK13qk3PAAdk5T0wdZAfAoEBy92hMctTpwZDZD" + 
		    				"","");
			
		}
		assertNotNull(Facebook.getTimeline());
    }

}
