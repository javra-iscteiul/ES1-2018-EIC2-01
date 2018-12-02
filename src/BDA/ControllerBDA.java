package BDA;

import org.w3c.dom.Node;
import javafx.scene.input.MouseEvent;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ControllerBDA {
	/**
	 * ListView (biblioteca Javafx)
	 */

	@FXML
	private ListView<Mensagem> list;
	
	
	@FXML
	private CheckBox email;
	
	@FXML
	private CheckBox facebook;
	
	@FXML
	private CheckBox twitter;
	
	/**
	 * TextField correspondente à palavra ou frase a pesquisar
	 */
	@FXML
	private TextField pesquisa;
	/**
	 * Procedimento responsável por iniciar a aplicação com a timeline 
	 */
	
	TimelineBDA bda = new TimelineBDA();
	@FXML
	public void initialize() {
		list.setItems(bda.getTimeLine());
		
	}
	
	/**
	 * Procedimento que devolve a lista de mensagens
	 * @param event
	 */
	@FXML
	private void getMensagens(MouseEvent event) {
		list.setItems(bda.getTimeLine());
	}
	
	/**
	 * Procedimento que filtra a lista de mensagens dada uma palavra ou frase
	 * @param event
	 */
	@FXML
	private void filter(ActionEvent event) {
		System.out.println(pesquisa.getText());
		list.setItems(bda.setFilter(pesquisa.getText()));
	}
	
	/**
	 * Procedimento que filtra a lista de mensagens
	 * @param event
	 */
	@FXML
	private void email(ActionEvent event) {
		if(email.isSelected()){
			bda.setShowEmail(true);
		}
		if(!email.isSelected()){
			bda.setShowEmail(false);
		}
		list.setItems(bda.getTimeLine());
	}
	/**
	 * Procedimento que filtra a lista de mensagens
	 * @param event
	 */
	@FXML
	private void facebook(ActionEvent event) {
		if(facebook.isSelected()){
			bda.setShowFacebook(true);
		}
		if(!facebook.isSelected()){
			bda.setShowFacebook(false);
		}
		list.setItems(bda.getTimeLine());
	}
	/**
	 * Procedimento que filtra a lista de mensagens
	 * @param event
	 */
	@FXML
	private void twitter(ActionEvent event) {
		if(twitter.isSelected()){
			bda.setShowTwitter(true);
		}
		if(!twitter.isSelected()){
			bda.setShowTwitter(false);
		}
		list.setItems(bda.getTimeLine());
	}
	
	/**
	 * Procedimento que filtra a lista de mensagens
	 * @param event
	 */
	@FXML
	private void onlyEmail(ActionEvent event) {
		list.setItems(bda.getOnlyEmail());
	}
	
	/**
	 * Procedimento que filtra a lista de mensagens
	 * @param event
	 */
	@FXML
	private void onlyFacebook(ActionEvent event) {
		list.setItems(bda.getOnlyFacebook());
	}
	
	/**
	 * Procedimento que filtra a lista de mensagens
	 * @param event
	 */
	@FXML
	private void onlyTwitter(ActionEvent event) {
		list.setItems(bda.getOnlyTwitter());
	}
}
