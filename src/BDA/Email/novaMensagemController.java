package BDA.Email;

import BDA.Credential;
import BDA.FuncoesGerais;
import BDA.IServiceController;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


/**
 * Date: Oct 24 2018
 * @author ES1-2018-EIC2-01
 * @version 1.0
 * Aplica��o agregadora de conte�dos acad�micos: controlador do Email
 *
 */
public class novaMensagemController implements IServiceController {
	private Credential emailCredential;
	
	/**
	 *  TextField destinatario da mensagem
	 */
	@FXML
	private TextField to;
	
	/**
	 * TextField assunto da mensagem
	 */
	@FXML
	private TextField sub;
	
	/**
	 * TextArea conteudo da mensagem
	 */
	@FXML
	private TextArea msg;
	
	@Override
	public void init(Credential cred) {
		emailCredential = cred;
	}
	
	/**
	 * Ao clicar no botao enviar chama a fun��o do email responsavel pelo envio da mensagem com os parametros indicados pelo utilizador
	 * @param event MouseEvent
	 */
	@FXML
	public void enviar(MouseEvent event){
		if(to.getText()!=null && msg.getText()!=null && sub.getText()!=null ) {
			Email.sendEmails(to.getText().toString(), sub.getText().toString(), msg.getText().toString(), emailCredential);
			FuncoesGerais.mudarVistaFXML(event, getClass().getResource("email.fxml"));
		}
	}

	/**
	 * Devolve o destinatario da mensagem
	 * @return TextField
	 */
	public TextField getTo() {
		return to;
	}

	/**
	 * Altera o destinatario da mensagem
	 * @param o String
	 */
	public void setTo(String o) {
		to.setText(o);
	}
	
	/**
	 * Inicia a interface com o campo destinatario preenchido no caso de resposta a uma mensagem especifica
	 */
	@FXML
	public void initialize() {
		to.setText(Email.getTo());
	}
}