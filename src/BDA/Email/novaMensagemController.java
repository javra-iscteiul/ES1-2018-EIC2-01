package BDA.Email;

import BDA.FuncoesGerais;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class novaMensagemController {
	@FXML
	private TextField to;
	
	@FXML
	private TextField sub;
	
	@FXML
	private TextArea msg;
	
	
	
	/**
	 * 
	 * @param event 
	 */
	@FXML
	public void enviar(MouseEvent event){
		if(to.getText()!=null && msg.getText()!=null && sub.getText()!=null ) {
			Email.sendEmails(to.getText().toString(), sub.getText().toString(), msg.getText().toString());
			FuncoesGerais.mudarVistaFXML(event, getClass().getResource("email.fxml"));
		}
	}

}
