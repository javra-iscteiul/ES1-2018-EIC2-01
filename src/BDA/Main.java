package BDA;

import BDA.Twitter.App_twitter;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
	
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
	
	@FXML
    private void twitterLogo_clicked(MouseEvent event)
    {
		if(!XMLclass.existsElement("twitter")){
			XMLclass.addElement("twitter", "smtp","EsJarh","grupo1grupo"
		    		,"yPv2NQ8ozCWIQ1jZeXjWLGUce", "w7lfg9hNlQ8qFAfb5k7fMtzdiYhqhBFe5S6PNu0PfTy0FL6Vo8",
		    		"1051761005406154752-yRmIyBEYTX21kensmMUAvpNVRfC15Q","F7mHLVxLhBOG3OHELLvYG5etmlIFtnXnNStgnlpHCShLX");
		}
		
		FuncoesGerais.mudarVistaFXML(event, getClass().getResource("./Twitter/twitter.fxml"));
		App_twitter.init();
    }
	
	@FXML
    private void facebookLogo_clicked(MouseEvent event)
    {
		if(!XMLclass.existsElement("facebook")){
			XMLclass.addElement("facebook", "smtp","EsJarh","grupo1grupo"
		    		,"", "","EAAEq0X5xdpMBAOzHJoC0VA7aUgvTaQUkuwpMHxVaPR3JDZBIECyEv8DTbv3k5Bbsi5JJo7ZALaJsCheHNQle5bHd328RsQSAZCMfVcL0TM9xLEK7EZA7UBlk6zqf0cUrT0CkYuOHjQK13qk3PAAdk5T0wdZAfAoEBy92hMctTpwZDZD" + 
		    				"","");
		}
		
		FuncoesGerais.mudarVistaFXML(event, getClass().getResource("./Facebook/facebookProfile.fxml"));		
    }
	
	@FXML
    private void emailLogo_clicked(MouseEvent event)
    {
		FuncoesGerais.mudarVistaFXML(event, getClass().getResource("login.fxml"));
    }
	
	public static void main(String[] args)
	{
		launch(args);
	}

}
