package jUnitTests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import BDA.XMLclass;
import BDA.Email.Email;

public class emailTest {
	@Test
    public void getTimeline() {
		if(!XMLclass.existsElement("email")){
			assertNull(Email.getTimeline());
			XMLclass.addElement("email", "", "es1g1@outlook.com", "grupo1grupo", "", "", "", "");
			
		}
		assertNotNull(Email.getTimeline());
    }
}
