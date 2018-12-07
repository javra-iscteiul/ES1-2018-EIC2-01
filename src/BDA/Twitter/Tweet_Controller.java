package BDA.Twitter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import twitter4j.MediaEntity;
 
/**
 * Date: Oct 22 2018
 * 
 * @author ES1-2018-EIC2-01
 * @version 1.0 Classe controlador de um tweet
 */
public class Tweet_Controller {
	/**
	 * mensagemTwitter a ser consultada
	 */
	private MensagemTwitter mensagem;

	/**
	 * Label do user da mensagem
	 */
	@FXML
	private Label user;

	/**
	 * Label do conteudo da mensagem
	 */
	@FXML
	private Label content;

	/**
	 * Label da data da mensagem
	 */
	@FXML
	private Label date;

	/**
	 * ImageView da imagem da mensagem
	 */
	@FXML
	private ImageView image;

	/**
	 * Metodo que inicia a interface com os campos da mensagem
	 */
	public void initialize() {
		date.setText(mensagem.getDate());
		user.setText(mensagem.getUser());
		content.setText(mensagem.getContent());

		if (mensagem.getMedia().length != 0) {
			
			image.setImage(new Image(mensagem.getMedia()[0].getMediaURL()));
			image.setVisible(true);
		}

	}

	/**
	 * Metodo que cria a janela com os dados da mensagem
	 */
	public void createWindow() {
		date.setText(mensagem.getDate());
		user.setText(mensagem.getUser());

	}

	/**
	 * Devolve a mesagem em uso
	 * @return MensagemTwitter
	 */
	public MensagemTwitter getMensagem() {
		return mensagem;
	}

	/**
	 * Altera a mensagem em uso
	 * @param mensagem MensagemTwitter
	 */
	public void setMensagem(MensagemTwitter mensagem) {
		this.mensagem = mensagem;
	}

}
