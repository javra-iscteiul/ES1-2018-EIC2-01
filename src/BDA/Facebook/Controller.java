package BDA.Facebook;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class Controller {
	private ObservableList<String> posts = FXCollections.observableArrayList();
	
	@FXML
	private ListView<String> facebookList;
	
	@FXML
    private void timeline_clicked(MouseEvent event){
		for(String post : Facebook.getTimeLine()){
			posts.add(post);
		}
		facebookList.setItems(posts);
	}
}
