package BDA;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javafx.scene.image.*;

/**
 * Date: Oct 22 2018
 * 
 * @author ES1-2018-EIC2-01
 * @version 1.0 Aplicação principal
 */

public class Main extends Application {

	/**
	 * Procedimento responsavel por lançar a janela principal da aplicação
	 * 
	 * @param janelaInicial
	 *            Stage
	 */
	@Override
	public void start(Stage janelaInicial) {
		try {
			janelaInicial.setTitle("Bom Dia Academia");
			janelaInicial.setScene(new Scene((Pane) FXMLLoader.load(Main.class.getResource("mainWindow.fxml"))));
			janelaInicial.getIcons().add(new Image("file:///../Graphics/ISCTE-IUL__360_x_358__400x400.png"));
			janelaInicial.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Procedimento responsavel por lançar a janela da aplicação twitter
	 * 
	 * @param event
	 *            MouseEvent
	 * @throws Exception
	 */
	@FXML
	private void twitterLogo_clicked(MouseEvent event) throws Exception {
		if (!XMLclass.existsLogin(XMLclass.configFile, XMLclass.twitterService)) {
			FuncoesGerais.mudarVistaParaLoginFXML(event, Main.class.getResource("login.fxml"),
					XMLclass.twitterService);
		} else {
			Credential cred = new Credential(
					XMLclass.getLogin(XMLclass.configFile, XMLclass.twitterService).getAttributes());

			FuncoesGerais.mudarVistaFromLoginFXML(event, Main.class.getResource("Twitter/twitter.fxml"), cred);
		}
	}

	/**
	 * Procedimento responsavel por lançar a janela da aplicação facebook
	 * 
	 * @param event
	 *            MouseEvent
	 * @throws Exception
	 */
	@FXML
	private void facebookLogo_clicked(MouseEvent event) throws Exception {
		if (!XMLclass.existsLogin(XMLclass.configFile, XMLclass.facebookService)) {
			FuncoesGerais.mudarVistaParaLoginFXML(event, Main.class.getResource("login.fxml"),
					XMLclass.facebookService);
		} else {
			Credential cred = new Credential(
					XMLclass.getLogin(XMLclass.configFile, XMLclass.facebookService).getAttributes());

			FuncoesGerais.mudarVistaFromLoginFXML(event, Main.class.getResource("Facebook/facebook.fxml"), cred);
		}
	}

	/**
	 * Procedimento responsavel por lançar a janela da aplicação email
	 * 
	 * @param event
	 *            MouseEvent
	 * @throws Exception
	 */
	@FXML
	private void emailLogo_clicked(MouseEvent event) throws Exception {
		if (!XMLclass.existsLogin(XMLclass.configFile, XMLclass.emailService)) {
			FuncoesGerais.mudarVistaParaLoginFXML(event, Main.class.getResource("login.fxml"), XMLclass.emailService);
		} else {
			Credential cred = new Credential(
					XMLclass.getLogin(XMLclass.configFile, XMLclass.emailService).getAttributes());

			FuncoesGerais.mudarVistaFromLoginFXML(event, Main.class.getResource("Email/email.fxml"), cred);
		}
	}

	/**
	 * Procedimento responsavel por lançar a janela da aplicação email
	 * 
	 * @param event
	 *            MouseEvent
	 * @throws IOException 
	 */
	@FXML
	private void apps_clicked(MouseEvent event) throws IOException {
		FuncoesGerais.mudarVistaFXML(event, Main.class.getResource("Timeline.fxml"));
	}

	/**
	 * Procedimento que mostra a interface de edicao de credencias
	 * 
	 * @param event MouseEvent
	 * @throws IOException 
	 */
	@FXML
	private void editar(MouseEvent event) throws IOException {
		FuncoesGerais.mudarVistaFXML(event, Main.class.getResource("EditCredentials.fxml"));
	}

	public static void main(String[] args) {
		launch(args);
	}

}
