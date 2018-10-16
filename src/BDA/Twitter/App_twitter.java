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
		    	cb.setDebugEnabled(true)
		    	  .setOAuthConsumerKey("yPv2NQ8ozCWIQ1jZeXjWLGUce")
		    	  .setOAuthConsumerSecret("w7lfg9hNlQ8qFAfb5k7fMtzdiYhqhBFe5S6PNu0PfTy0FL6Vo8")
		    	  .setOAuthAccessToken("1051761005406154752-yRmIyBEYTX21kensmMUAvpNVRfC15Q")
		    	  .setOAuthAccessTokenSecret("F7mHLVxLhBOG3OHELLvYG5etmlIFtnXnNStgnlpHCShLX");
		   // 	XMLclass.addElement("twitter", "smtp","yPv2NQ8ozCWIQ1jZeXjWLGUce", "w7lfg9hNlQ8qFAfb5k7fMtzdiYhqhBFe5S6PNu0PfTy0FL6Vo8" );
		  //  	XMLclass.saveXML();     mete um try catch aqui 
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
