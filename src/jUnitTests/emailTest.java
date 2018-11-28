package jUnitTests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import BDA.XMLclass;
import BDA.Email.Email;

public class emailTest {
	private static Email email = new Email();
	@Test
    public void getTimeline() {
		//verifica se não existem credenciais do email
		if(!XMLclass.existsNode(XMLclass.configFile, "email")){
			
			//valida se existe data guardada de logins anteriores
			if(XMLclass.existsNode(XMLclass.storedDataFile, "email")){
				//verifica se consegue carregar esses dados offline
				assertNotNull(email.getTimeline());
			}else{
				//verifica se da false quando não se consegue obter dados
				assertNull(email.getTimeline());
			}
			
			//insere as credenciais do face no config
			Map<String, String> attributes = new HashMap<String, String>();
			attributes.put("UserName", "es1g1@outlook.com");
			attributes.put("Password", "grupo1grupo");
			XMLclass.addElement(XMLclass.configFile, "email", attributes);
		}
		
		//se não tem data guardada e está online verifica se vai buscar os dados e guarda esses dados
		if(!XMLclass.existsNode(XMLclass.storedDataFile, "email")){
			if(email.getTimeline() != null){
				assertNotNull(XMLclass.existsNode(XMLclass.storedDataFile, "email"));
			}
		}else{
			//se tiver data guardada tem que dar sempre true
			assertNotNull(email.getTimeline());
		}
    }
}
