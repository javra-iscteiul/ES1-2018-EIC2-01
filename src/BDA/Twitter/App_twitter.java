package BDA.Twitter;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import BDA.XMLclass;
import BDA.Facebook.Message;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;



/**
 * Date: Oct 22 2018
 * @author ES1-2018-EIC2-01
 * @version 1.0
 * Aplica��o agregadora de aplica��es: canal do Twitter
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
	 * ListView onde os tweets s�o disponibilizados na interface (biblioteca Javafx)
	 */
/*	@FXML
	private ListView<String> tweetsList;
*/	
	/**
	 * TextField correspondente � palavra ou frase a pesquisar
	 */
/*	@FXML
	private TextField pesquisa;

*/	
	/**
	 * Obt�m a timeline atualizada ap�s o clique do bot�o "refresh" 
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
	 * Obt�m a timeline atualizada ap�s a procura correspondente ao textField pesquisa
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
	 * Inicializa��o dos atributos relacionados com o AccessToken  
	 */
	public static void init() {
		 ConfigurationBuilder cb = new ConfigurationBuilder();
		Node twitterConfig = XMLclass.getElement(XMLclass.configFile, "twitter");
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey(twitterConfig.getAttributes().getNamedItem("ConsumerKey").getNodeValue())
		  .setOAuthConsumerSecret(twitterConfig.getAttributes().getNamedItem("ConsumerSecret").getNodeValue())
		  .setOAuthAccessToken(twitterConfig.getAttributes().getNamedItem("AccessToken").getNodeValue())
		  .setOAuthAccessTokenSecret(twitterConfig.getAttributes().getNamedItem("AccessTokenSecret").getNodeValue());
		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();
				
	 }
	
	
	/**
	 * Este procedimento permite que sejam adicionados tweets e o respectivo user � ObservableList, de modo a coloc�-los na ListView 
	 * @throws TwitterException
	 */
	public boolean getTimeline( ListView<String> tweetsList) throws TwitterException{ 
		Map<String, Map<String, String>> dataToStore = new HashMap<>();
		tweets.clear();
		tweetsList.getItems().clear();
		try{
			

			
			Paging paging = new Paging(1, 40);
			List<Status> statuses = twitter.getHomeTimeline(paging);
			
			if (XMLclass.existsElement(XMLclass.storedDataFile, "twitter")) {
				XMLclass.deleteElement(XMLclass.storedDataFile, "twitter");
			}
			
			int counter=0;
			int counterTotal = 0;
	        for (Status status : statuses) {
	        	String userName = status.getUser().getName();
	        	String text =status.getText();
	        	Date dateCreated = status.getCreatedAt();
	        	String date = dateCreated.toString();
	        	String s = status.getUser().getName() + "\n" + status.getText();
	        	
	        	Map<String, String> childAttributesToStore = new HashMap<>();
				childAttributesToStore.put("userName", userName);
				childAttributesToStore.put("dateCreated", date);
				childAttributesToStore.put("statusText", text);
				dataToStore.put("post" + counter, childAttributesToStore);
			
				tweets.add(counter, s);
				counter++;
				counterTotal++;
	        }
	        
	        XMLclass.addElementAndChild(XMLclass.storedDataFile, "twitter", dataToStore);
	        
	        tweetsList.setItems(tweets);
			return true;
		} catch (Exception e) {
			System.out.println("oiii");
			if(e.getCause() instanceof UnknownHostException){
				System.out.println("problema de conexao");
				solveConectionProblems(tweetsList);
			}
			else{
				e.printStackTrace();
			}
			return false;
		}
	}
	
	
	private void solveConectionProblems(ListView<String> tweetsList) {
		if (XMLclass.existsElement(XMLclass.storedDataFile, "twitter")) {
			Node twitterNode = XMLclass.getElement(XMLclass.storedDataFile, "twitter");
			int counter = 0;
			for (int i = 0; i < twitterNode.getChildNodes().getLength(); i++) {
				System.out.println("estou no for");
				NamedNodeMap childAttributes = twitterNode.getChildNodes().item(i).getAttributes();
				System.out.println(childAttributes.toString());
				if (childAttributes != null) {
					String userName = childAttributes.getNamedItem("userName").getNodeValue();
					String title = childAttributes.getNamedItem("title").getNodeValue();

					tweets.add(counter, (userName + ":" + title));
					System.out.println(title);
					counter++;
				}
			}
		}
		tweetsList.setItems(tweets);
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
			System.out.println("-------------\nN� of Results: " + counter+"/"+counterTotal);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public String post(String quote){
		try {
			twitter.updateStatus(quote);
			System.out.println("Successfully updated the status to [" + quote + "].");
			
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return quote;
	}
	
	
	public DirectMessageList getMessageList(){
	
		try {
			DirectMessageList messages;
		    int count = 20;
			String cursor = null;
			 do {
				messages = cursor == null ? twitter.getDirectMessages(count) : twitter.getDirectMessages(count, cursor);
				for (DirectMessage message : messages) {
	                 System.out.println("From: " + message.getSenderId() + " id:" + message.getId()
	                         + " [" + message.getCreatedAt() + "]"
	                         + " - " + message.getText());
				 }
				cursor = messages.getNextCursor();
			 } while (messages.size() > 0 && cursor != null);
			return messages;
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	

	

}
