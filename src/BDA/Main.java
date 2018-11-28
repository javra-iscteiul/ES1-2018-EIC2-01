package BDA;

import java.util.HashMap;
import java.util.Map;

import BDA.Email.Email;
import BDA.Twitter.App_twitter;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Date: Oct 22 2018
 * @author ES1-2018-EIC2-01
 * @version 1.0
 * Aplicação principal 
 */

public class Main extends Application {
	
	
	/**
	 * Procedimento responsavel por lançar a janela principal da aplicação
	 * @param janelaInicial Stage 
	 */
	@Override
	public void start(Stage janelaInicial){ 
		try{			
			janelaInicial.setTitle("Bom Dia Academia");
			janelaInicial.setScene(new Scene((Pane)FXMLLoader.load(getClass().getResource("mainWindow.fxml"))));
			janelaInicial.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Procedimento responsavel por lançar a janela da aplicação twitter
	 * @param event MouseEvent 
	 */
	@FXML
    private void twitterLogo_clicked(MouseEvent event)
    {
		if(!XMLclass.existsNode(XMLclass.configFile, "twitter")){
			Map<String, String> attributes = new HashMap<String, String>();
			attributes.put("Protocol", "smtp");
			attributes.put("UserName", "EsJarh");
			attributes.put("Password", "grupo1grupo");
			attributes.put("ConsumerKey", "yPv2NQ8ozCWIQ1jZeXjWLGUce");
			attributes.put("ConsumerSecret", "w7lfg9hNlQ8qFAfb5k7fMtzdiYhqhBFe5S6PNu0PfTy0FL6Vo8");
			attributes.put("AccessToken", "1051761005406154752-yRmIyBEYTX21kensmMUAvpNVRfC15Q");
			attributes.put("AccessTokenSecret", "1051761005406154752-F7mHLVxLhBOG3OHELLvYG5etmlIFtnXnNStgnlpHCShLX");
			
			XMLclass.addNode(XMLclass.configFile, "twitter", attributes);
		}
		
		App_twitter.init();
		FuncoesGerais.mudarVistaFXML(event, getClass().getResource("./Twitter/twitter.fxml"));
		
    }
	
	
	/**
	 * Procedimento responsavel por lançar a janela da aplicação facebook
	 * @param event MouseEvent 
	 */
	@FXML
    private void facebookLogo_clicked(MouseEvent event)
    {
		if(!XMLclass.existsNode(XMLclass.configFile, "facebook")){
			Map<String, String> attributes = new HashMap<String, String>();
			attributes.put("UserName", "EsJarh");
			attributes.put("Password", "grupo1grupo");
			attributes.put("AccessToken", "EAAEq0X5xdpMBAOzHJoC0VA7aUgvTaQUkuwpMHxVaPR3JDZBIECyEv8DTbv3k5Bbsi5JJo7ZALaJsCheHNQle5bHd328RsQSAZCMfVcL0TM9xLEK7EZA7UBlk6zqf0cUrT0CkYuOHjQK13qk3PAAdk5T0wdZAfAoEBy92hMctTpwZDZD");
			attributes.put("AccessTokenSecret", "1051761005406154752-F7mHLVxLhBOG3OHELLvYG5etmlIFtnXnNStgnlpHCShLX");
			
			XMLclass.addNode(XMLclass.configFile, "facebook", attributes);
		}
		
		FuncoesGerais.mudarVistaFXML(event, getClass().getResource("./Facebook/facebookProfile.fxml"));		
    }
	
	
	/**
	 * Procedimento responsavel por lançar a janela da aplicação email
	 * @param event MouseEvent 
	 */
	@FXML
    private void emailLogo_clicked(MouseEvent event){
		if(XMLclass.existsNode(XMLclass.configFile, "email")){
			Email.init();
			FuncoesGerais.mudarVistaFXML(event, getClass().getResource("./Email/email.fxml"));
		}else {
			FuncoesGerais.mudarVistaFXML(event, getClass().getResource("./Email/loginEmail.fxml"));
		}
    }
	
	
	
	private static void main(String[] args)
	{
		launch(args);
	}

}
