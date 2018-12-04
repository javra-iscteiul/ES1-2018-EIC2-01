package BDA;

import java.io.IOException;
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

import javafx.scene.image.*;

/**
 * Date: Oct 22 2018
 * 
 * @author ES1-2018-EIC2-01
 * @version 1.0 Aplica��o principal
 */

public class Main extends Application {

	/**
	 * Procedimento responsavel por lan�ar a janela principal da aplica��o
	 * 
	 * @param janelaInicial
	 *            Stage
	 * @throws IOException 
	 */
	@Override
	public void start(Stage janelaInicial) {
		try {
			janelaInicial.setTitle("Bom Dia Academia");
			janelaInicial.setScene(new Scene((Pane) FXMLLoader.load(getClass().getResource("mainWindow.fxml"))));
			janelaInicial.getIcons().add(new Image("file:///../Graphics/ISCTE-IUL__360_x_358__400x400.png"));
			janelaInicial.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Procedimento responsavel por lan�ar a janela da aplica��o twitter
	 * 
	 * @param event
	 *            MouseEvent
	 * @throws Exception
	 */
	@FXML
	private void twitterLogo_clicked(MouseEvent event) throws Exception {
		if (!XMLclass.existsLogin(XMLclass.configFile, XMLclass.twitterService)) {
			FuncoesGerais.mudarVistaParaLoginFXML(event, getClass().getResource("./login.fxml"),
					XMLclass.twitterService);
		} else {
			Credential cred = new Credential(
					XMLclass.getLogin(XMLclass.configFile, XMLclass.twitterService).getAttributes());

			FuncoesGerais.mudarVistaFromLoginFXML(event, getClass().getResource("./Twitter/twitter.fxml"), cred);
		}
	}

	/**
	 * Procedimento responsavel por lan�ar a janela da aplica��o facebook
	 * 
	 * @param event
	 *            MouseEvent
	 * @throws Exception
	 */
	@FXML
	private void facebookLogo_clicked(MouseEvent event) throws Exception {
		if (!XMLclass.existsLogin(XMLclass.configFile, XMLclass.facebookService)) {
			FuncoesGerais.mudarVistaParaLoginFXML(event, getClass().getResource("./login.fxml"),
					XMLclass.facebookService);
		} else {
			Credential cred = new Credential(
					XMLclass.getLogin(XMLclass.configFile, XMLclass.facebookService).getAttributes());

			FuncoesGerais.mudarVistaFromLoginFXML(event, getClass().getResource("./Facebook/facebook.fxml"), cred);
		}
	}

	/**
	 * Procedimento responsavel por lan�ar a janela da aplica��o email
	 * 
	 * @param event
	 *            MouseEvent
	 * @throws Exception
	 */
	@FXML
	private void emailLogo_clicked(MouseEvent event) throws Exception {
		if (!XMLclass.existsLogin(XMLclass.configFile, XMLclass.emailService)) {
			FuncoesGerais.mudarVistaParaLoginFXML(event, getClass().getResource("./login.fxml"), XMLclass.emailService);
		} else {
			Credential cred = new Credential(
					XMLclass.getLogin(XMLclass.configFile, XMLclass.emailService).getAttributes());

			FuncoesGerais.mudarVistaFromLoginFXML(event, getClass().getResource("./Email/email.fxml"), cred);
		}
	}

	/**
	 * Procedimento responsavel por lan�ar a janela da aplica��o email
	 * 
	 * @param event
	 *            MouseEvent
	 * @throws IOException 
	 */
	@FXML
	private void apps_clicked(MouseEvent event) throws IOException {
		FuncoesGerais.mudarVistaFXML(event, getClass().getResource("Timeline.fxml"));
	}

	/**
	 * Procedimento que mostra a interface de edicao de credencias
	 * 
	 * @param event
	 * @throws IOException 
	 */
	@FXML
	private void editar(MouseEvent event) throws IOException {
		FuncoesGerais.mudarVistaFXML(event, getClass().getResource("EditCredentials.fxml"));
	}

	private static void main(String[] args) {
		launch(args);
	}

}
