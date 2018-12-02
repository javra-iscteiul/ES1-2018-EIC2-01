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
		FuncoesGerais.mudarVistaParaLoginFXML(event, getClass().getResource("./login.fxml"), XMLclass.twitterService);	
    }
	
	
	/**
	 * Procedimento responsavel por lançar a janela da aplicação facebook
	 * @param event MouseEvent 
	 */
	@FXML
    private void facebookLogo_clicked(MouseEvent event)
    {
		FuncoesGerais.mudarVistaParaLoginFXML(event, getClass().getResource("./login.fxml"), XMLclass.facebookService);		
    }
	
	
	/**
	 * Procedimento responsavel por lançar a janela da aplicação email
	 * @param event MouseEvent 
	 */
	@FXML
    private void emailLogo_clicked(MouseEvent event){
		FuncoesGerais.mudarVistaParaLoginFXML(event, getClass().getResource("./login.fxml"), XMLclass.emailService);
    }
	
	/**
	 * Procedimento responsavel por lançar a janela da aplicação email
	 * @param event MouseEvent 
	 */
	@FXML
    private void apps_clicked(MouseEvent event){	
		FuncoesGerais.mudarVistaFXML(event, getClass().getResource("Timeline.fxml"));

    }
	
	
	
	private static void main(String[] args)
	{
		launch(args);
	}

}
