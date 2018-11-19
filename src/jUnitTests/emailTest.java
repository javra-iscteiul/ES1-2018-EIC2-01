package jUnitTests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import BDA.XMLclass;
import BDA.Email.Email;

public class emailTest {
	@Test
    public void getTimeline() {
		if(!XMLclass.existsElement(XMLclass.configFile, "email")){
			assertNull(Email.getTimeline());
			
			Map<String, String> attributes = new HashMap<String, String>();
			attributes.put("UserName", "es1g1@outlook.com");
			attributes.put("Password", "grupo1grupo");
			
			XMLclass.addElement(XMLclass.configFile, "email", attributes);
			
		}
		assertNotNull(Email.getTimeline());
    }
}
