package BDA;

import java.io.IOException;
import java.net.URL;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import twitter4j.TwitterException;

/**
 * Date: Oct 22 2018
 * @author ES1-2018-EIC2-01
 * @version 1.0
 * classe com as funçoes usadas nas varias aplicaçoes
 */

public class FuncoesGerais {
	
	
	/**
	 * Procedimento que permite alternar entre interfaces 
	 * @param event Event 
	 * @param ficheiroURL URL
	 */
	public static void mudarVistaFXML(Event event, URL ficheiroURL){
		try {
			Node node = (Node)event.getSource();
			Stage stage = (Stage)node.getScene().getWindow();
			stage.setScene(new Scene(FXMLLoader.load(ficheiroURL)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
