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
		FuncoesGerais.mudarVistaFXML(event, getClass().getResource("./Twitter/loginTwitter.fxml"));
    }
	
	@FXML
    private void facebookLogo_clicked(MouseEvent event)
    {
		FuncoesGerais.mudarVistaFXML(event, getClass().getResource("./Facebook/login.fxml"));		
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
