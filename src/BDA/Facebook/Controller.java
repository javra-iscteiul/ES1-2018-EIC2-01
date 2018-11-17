package BDA.Facebook;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

/**
 * Date: Oct 22 2018
 * @author ES1-2018-EIC2-01
 * @version 1.0
 * Aplicação agregadora de aplicações: controlador do facebook
 *
 */

public class Controller {
	
	/**
	 * ObservableList com os posts do facebook
	 */
	private ObservableList<String> posts = FXCollections.observableArrayList();
	
	
	/**
	 * ListView (biblioteca Javafx)
	 */
	@FXML
	private ListView<String> facebookList;
	
	private IFacebook facebook = new Facebook();
	
	/**
	 * Procedimento que adiciona posts à timeline cada vez que esta é clicada com o rato (biblioteca Javafx)
	 * @param event 
	 */
	@FXML
    private void timeline_clicked(MouseEvent event){
		for(String post : facebook.getTimeLine()){
			posts.add(post);
		}
		facebookList.setItems(posts);
	}
}
