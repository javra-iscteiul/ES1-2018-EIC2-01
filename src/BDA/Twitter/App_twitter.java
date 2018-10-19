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


public class App_twitter {
	private ObservableList<String> tweets = FXCollections.observableArrayList();
//	private Twitter twitter;
	
	@FXML
	private ListView<String> tweetsList;
	
	@FXML
    private void refresh_timeline_Clicked(MouseEvent event)
    {
		getTimeline();
    }
	
	public void getTimeline(){
		
			try { 
				ConfigurationBuilder cb = new ConfigurationBuilder();
				Node twitterConfig = XMLclass.getElement("twitter");
		    	cb.setDebugEnabled(true)
		    	  .setOAuthConsumerKey(twitterConfig.getAttributes().getNamedItem("ConsumerKey").getNodeValue())
		    	  .setOAuthConsumerSecret(twitterConfig.getAttributes().getNamedItem("ConsumerSecret").getNodeValue())
		    	  .setOAuthAccessToken(twitterConfig.getAttributes().getNamedItem("AccessToken").getNodeValue())
		    	  .setOAuthAccessTokenSecret(twitterConfig.getAttributes().getNamedItem("AccessTokenSecret").getNodeValue());
		    	TwitterFactory tf = new TwitterFactory(cb.build());
				Twitter twitter = tf.getInstance();
		             
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
	        
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
