package BDA;

import org.w3c.dom.Node;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ControllerBDA {
	/**
	 * ListView (biblioteca Javafx)
	 */

	@FXML
	private ListView<Mensagem> list;
	
	
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
	 * Procedimento que filtra a lista de emails dada uma palavra ou frase
	 * @param event
	 */
	@FXML
	private void filter(ActionEvent event) {
		System.out.println(pesquisa.getText());
		list.setItems(bda.setFilter(pesquisa.getText()));
	}
}
