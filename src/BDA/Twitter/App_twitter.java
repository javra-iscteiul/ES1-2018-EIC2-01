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



/**
 * Date: Oct 22 2018
 * @author ES1-2018-EIC2-01
 * @version 1.0
 * Aplicação agregadora de aplicações: canal do Twitter
 */
public final class App_twitter {
	
	/**
	 * ObservableList com os tweets 
	 */
	private ObservableList<String> tweets = FXCollections.observableArrayList();
	
	/**
	 * Atributo do tipo Twitter utilizados nos procedimentos seguintes 
	 */
	private static Twitter twitter;
	
	
	/**
	 * ListView onde os tweets são disponibilizados na interface (biblioteca Javafx)
	 */
	@FXML
	private ListView<String> tweetsList;
	
	
	/**
	 * Obtém a timeline atualizada após o botão "refresh" ser clicado
	 * @param event 
	 * @throws TwitterException
	 */
	@FXML
    private void refresh_timeline_Clicked(MouseEvent event) throws TwitterException
    {
		getTimeline();
    }
	
	 /**
	 * Inicialização dos atributos relacionados com o AccessToken  
	 */
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
	
	/**
	 * Este procedimento permite que sejam adicionados tweets e o respectivo user à ObservableList, de modo a colocá-los na ListView 
	 * @throws TwitterException
	 */
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
	
	
	/**
	 * @param s String 
	 * @throws TwitterException
	 * Procedimento que filtra determinados tweets segundo determinadas condições
	 */
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
