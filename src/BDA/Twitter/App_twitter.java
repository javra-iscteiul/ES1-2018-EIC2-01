package BDA.Twitter;
import java.util.List;

import BDA.XMLclass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import org.w3c.dom.Node;


public final class App_twitter {
	private ObservableList<String> tweets = FXCollections.observableArrayList();
	private static Twitter twitter;
	
	@FXML
	private ListView<String> tweetsList;
	
	@FXML
    private void refresh_timeline_Clicked(MouseEvent event) throws TwitterException
    {
		getTimeline();
    }
	 public static void init() {
		 ConfigurationBuilder cb = new ConfigurationBuilder();
		Node twitterConfig = XMLclass.getElement("twitter");
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey(twitterConfig.getAttributes().getNamedItem("ConsumerKey").getNodeValue())
		  .setOAuthConsumerSecret(twitterConfig.getAttributes().getNamedItem("ConsumerSecret").getNodeValue())
		  .setOAuthAccessToken(twitterConfig.getAttributes().getNamedItem("AccessToken").getNodeValue())
		  .setOAuthAccessTokenSecret(twitterConfig.getAttributes().getNamedItem("AccessTokenSecret").getNodeValue());
		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();
				
	 }
	public void getTimeline() throws TwitterException{
		 List<Status> statuses = twitter.getHomeTimeline();   
		int counter=0;
		int counterTotal = 0;
        for (Status status : statuses) {
        	String s = status.getUser().getName() + ":" + status.getText();
			 
			tweets.add(counter, s);
			counter++;
			counterTotal++;
        }
        tweetsList.setItems(tweets);
		System.out.println("-------------\nNº of Results: " + counter+"/"+counterTotal);
		}
	
	
	public void filter(String s) throws TwitterException {
		 List<Status> statuses = twitter.getHomeTimeline();
         System.out.println("------------------------\n Showing home timeline \n------------------------");
 		int counter=0;
		int counterTotal = 0;
		 for (Status status : statuses) {
				// Filters only tweets from user "catarina"
				if (status.getUser().getName() != null && status.getUser().getName().contains("catarina")) {
					System.out.println(status.getUser().getName() + ":" + status.getText());
					counter++;
				}
				counterTotal++;
         }
 		System.out.println("-------------\nNº of Results: " + counter+"/"+counterTotal);
		
	}

}
