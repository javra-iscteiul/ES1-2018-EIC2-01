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
		try{
			//verifica se não existem credenciais do twitter
			if(!XMLclass.existsNode(XMLclass.configFile, "twitter")){
				
				//valida se existe data guardada de logins anteriores
				if(XMLclass.existsNode(XMLclass.storedDataFile, "twitter")){
					//verifica se consegue carregar esses dados offline
					assertNotNull(twitter.getTimeline());
				}else{
					//verifica se da false quando não se consegue obter dados
					assertNull(twitter.getTimeline());
				}
				//insere as credenciais do twitte no config
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
			//se não tem data guardada e está online verifica se vai buscar os dados e guarda esses dados
			if(!XMLclass.existsNode(XMLclass.storedDataFile, "twitter")){
						if(twitter.getTimeline() != null){
							assertNotNull(XMLclass.existsNode(XMLclass.storedDataFile, "twitter"));
						}
			}else{
				//se tiver data guardada tem que dar sempre true
				assertNotNull(twitter.getTimeline());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}