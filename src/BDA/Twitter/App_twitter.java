package BDA.Twitter;
import java.util.List;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;


public class App_twitter {

	public static void main(String[] args) {
		
	}
	
	public void getTimeline(){
		try {
			ConfigurationBuilder cb = new ConfigurationBuilder();
	    	cb.setDebugEnabled(true)
	    	  .setOAuthConsumerKey("yPv2NQ8ozCWIQ1jZeXjWLGUce")
	    	  .setOAuthConsumerSecret("w7lfg9hNlQ8qFAfb5k7fMtzdiYhqhBFe5S6PNu0PfTy0FL6Vo8")
	    	  .setOAuthAccessToken("1051761005406154752-yRmIyBEYTX21kensmMUAvpNVRfC15Q")
	    	  .setOAuthAccessTokenSecret("F7mHLVxLhBOG3OHELLvYG5etmlIFtnXnNStgnlpHCShLX");
	    	TwitterFactory tf = new TwitterFactory(cb.build());
			Twitter twitter = tf.getInstance();  		
            List<Status> statuses = twitter.getHomeTimeline();
            System.out.println("------------------------\n Showing home timeline \n------------------------");
    		int counter=0;
    		int counterTotal = 0;
            for (Status status : statuses) {
				// Filters only tweets from user "catarina"
				//if (status.getUser().getName() != null && status.getUser().getName().contains("iscte")) {
					System.out.println(status.getUser().getName() + ":" + status.getText());
					counter++;
				//}
				counterTotal++;
            }
    		System.out.println("-------------\nNº of Results: " + counter+"/"+counterTotal);
        } catch (Exception e) { System.out.println(e.getMessage()); }
	}

}
