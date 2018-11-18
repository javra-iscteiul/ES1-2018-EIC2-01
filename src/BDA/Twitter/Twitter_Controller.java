package BDA.Twitter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import twitter4j.TwitterException;

public class Twitter_Controller {
	
	
	//private ObservableList<String> tweets = FXCollections.observableArrayList();
	App_twitter tt = new App_twitter();
	
	@FXML
	private ListView<String> tweetsList;
	
	@FXML
	private TextField pesquisa;
	
	
	@FXML
    public void initialize() {
		try {
			tt.getTimeline(tweetsList);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			System.out.println("deu porcaria");
		}
    }
	
	@FXML
    private void refresh_timeline_Clicked(MouseEvent event) throws TwitterException
    {
		tt.getTimeline(tweetsList);
    }
	
	
	@FXML
    private void filter(ActionEvent event) throws TwitterException 
    {
		System.out.println(pesquisa.getText());
		tt.filter(pesquisa.getText(), tweetsList);
    }
	
	
}
