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
		//verifica se não existem credenciais do facebook
		if(!XMLclass.existsElement(XMLclass.configFile, "facebook")){
			
			//valida se existe data guardada de logins anteriores
			if(XMLclass.existsElement(XMLclass.storedDataFile, "facebook")){
				//verifica se consegue carregar esses dados offline
				assertNotNull(facebook.getTimeLine());
			}else{
				//verifica se da false quando não se consegue obter dados
				assertNull(facebook.getTimeLine());
			}
			
			//insere as credenciais do face no config
			Map<String, String> attributes = new HashMap<String, String>();
			attributes.put("UserName", "EsJarh");
			attributes.put("Password", "grupo1grupo");
			attributes.put("AccessToken", "EAAEq0X5xdpMBAOzHJoC0VA7aUgvTaQUkuwpMHxVaPR3JDZBIECyEv8DTbv3k5Bbsi5JJo7ZALaJsCheHNQle5bHd328RsQSAZCMfVcL0TM9xLEK7EZA7UBlk6zqf0cUrT0CkYuOHjQK13qk3PAAdk5T0wdZAfAoEBy92hMctTpwZDZD");
			attributes.put("AccessTokenSecret", "1051761005406154752-F7mHLVxLhBOG3OHELLvYG5etmlIFtnXnNStgnlpHCShLX");
			XMLclass.addElement(XMLclass.configFile, "facebook", attributes);
		}
		
		//se não tem data guardada e está online verifica se vai buscar os dados e guarda esses dados
		if(!XMLclass.existsElement(XMLclass.storedDataFile, "facebook")){
			if(facebook.getTimeLine() != null){
				assertNotNull(XMLclass.existsElement(XMLclass.storedDataFile, "facebook"));
			}
		}else{
			//se tiver data guardada tem que dar sempre true
			assertNotNull(facebook.getTimeLine());
		}
    }
}
