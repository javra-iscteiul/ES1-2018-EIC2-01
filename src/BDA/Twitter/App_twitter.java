package BDA.Twitter;
import java.util.List;

import BDA.XMLclass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
/*	@FXML
	private ListView<String> tweetsList;
*/	
	/**
	 * TextField correspondente à palavra ou frase a pesquisar
	 */
/*	@FXML
	private TextField pesquisa;

*/	
	/**
	 * Obtém a timeline atualizada após o clique do botão "refresh" 
	 * @param event MouseEvent
	 * @throws TwitterException
	 */
/*	@FXML
    private void refresh_timeline_Clicked(MouseEvent event) throws TwitterException
    {
		getTimeline();
    }
*/	
	/**
	 * Obtém a timeline atualizada após a procura correspondente ao textField pesquisa
	 * @param event 
	 * @throws TwitterException
	 */
/*	@FXML
    private void filter(ActionEvent event) throws TwitterException 
    {
		System.out.println(pesquisa.getText());
		filter(pesquisa.getText());
    }
	
*/	
	
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
	public boolean getTimeline( ListView<String> tweetsList) throws TwitterException{ 
		try{
			Paging paging = new Paging(1, 40);
			List<Status> statuses = twitter.getHomeTimeline(paging);
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
			return true;
		} catch (Exception e) {
			System.out.println("oiii");
			e.printStackTrace();
			return false;
		}
	}
	
	
	/**
	 * Procedimento que filtra os tweets da timeline segundo determinada frase ou palavra
	 * @param quote String 
	 * @throws TwitterException
	 */
	public boolean filter(String quote, ListView<String> tweetsList) throws TwitterException {
		 try{
			Paging paging = new Paging(1, 40);
			List<Status> statuses = twitter.getHomeTimeline(paging);
	        System.out.println("------------------------\n Showing home timeline \n------------------------");
			int counter=0;
			int counterTotal = 0;
			tweets.clear();
			tweetsList.getItems().clear();
			for (Status status : statuses) {
				if (status.getUser().getName() != null && status.getText().contains(quote)) {
					String s = status.getCreatedAt() + " " +  status.getUser().getName() + ":" + status.getText();
					System.out.println(status.getUser().getName() + ":" + status.getText() );
					tweets.add(counter, s);
					counter++;
				}
				counterTotal++;
	        }
			
		    tweetsList.setItems(tweets);
			System.out.println("-------------\nNº of Results: " + counter+"/"+counterTotal);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
