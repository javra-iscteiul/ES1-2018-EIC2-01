package BDA;

import java.io.IOException;
import java.net.URL;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FuncoesGerais {
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
