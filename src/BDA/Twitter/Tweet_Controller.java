package BDA.Twitter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import twitter4j.MediaEntity;
 
public class Tweet_Controller {
	private MensagemTwitter mensagem;

	@FXML
	private Label user;

	@FXML
	private Label content;

	@FXML
	private Label date;

	@FXML
	private ImageView image;

	public void initialize() {
		date.setText(mensagem.getDate());
		user.setText(mensagem.getUser());
		content.setText(mensagem.getContent());

		if (mensagem.getMedia().length != 0) {
			
			image.setImage(new Image(mensagem.getMedia()[0].getMediaURL()));
			image.setVisible(true);
		}

	}

	public void createWindow() {
		date.setText(mensagem.getDate());
		user.setText(mensagem.getDate());

	}

	public MensagemTwitter getMensagem() {
		return mensagem;
	}

	public void setMensagem(MensagemTwitter mensagem) {
		this.mensagem = mensagem;
	}

}
