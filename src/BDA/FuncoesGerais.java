package BDA;

import java.io.IOException;
import java.net.URL;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * Date: Oct 22 2018
 * 
 * @author ES1-2018-EIC2-01
 * @version 1.0 classe com as funçoes usadas nas varias aplicaçoes
 */

public class FuncoesGerais {

	/**
	 * Procedimento que permite alternar entre interfaces
	 * 
	 * @param event
	 *            Event
	 * @param ficheiroURL
	 *            URL
	 * @throws IOException
	 */
	public static boolean mudarVistaFXML(Event event, URL ficheiroURL) throws IOException {
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.setScene(new Scene(FXMLLoader.load(ficheiroURL)));

		return true;
	}

	/**
	 * Procedimento que permite alternar entre interfaces com credenciais
	 * 
	 * @param event
	 *            Event
	 * @param ficheiroURL
	 *            URL
	 * @throws IOException
	 */
	public static boolean mudarVistaFromLoginFXML(Event event, URL ficheiroURL, Credential cred) throws Exception {
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(ficheiroURL);
		Region region = (Region) loader.load();

		IServiceController servico = loader.<IServiceController>getController();
		servico.init(cred);

		stage.setScene(new Scene(region));

		return true;
	}

	public static boolean mudarVistaParaLoginFXML(Event event, URL ficheiroURL, String service) throws IOException {
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(ficheiroURL);
		Region region = (Region) loader.load();

		loginController servico = loader.<loginController>getController();
		servico.login_init(service);

		stage.setScene(new Scene(region));

		return true;
	}
}
