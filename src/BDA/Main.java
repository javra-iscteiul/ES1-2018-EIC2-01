package BDA;

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
    }
	
	@FXML
    private void facebookLogo_clicked(MouseEvent event)
    {
		if(!XMLclass.existsElement("facebook")){
			XMLclass.addElement("facebook", "smtp","EsJarh","grupo1grupo"
		    		,"", "","EAAEq0X5xdpMBAAkyge0JQPpsLgiBoc2zPHRzwBQ02lBnhflhv5k6bGG5xOIYMZC9w68iaMPCs5ZAAntplDusY1gInIaHUr9xZCcGGFv8jzkqb28pxqUF5NdMBIql9SRZCM3AzubjtaLqPZAFKXyOeiuZAAUSRbHNkZD\r\n" + 
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
