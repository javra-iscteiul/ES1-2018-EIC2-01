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
 * Aplicação agregadora de aplicações: canal do Twitter
 */
public final class App_twitter {
	
	/**
	 * ObservableList com os tweets 
	 */
	private ObservableList<Mensagem> tweets = FXCollections.observableArrayList();
	
	/**
	 * Atributo do tipo Twitter utilizados nos procedimentos seguintes 
	 */
	private static Twitter twitter;
	


	
	 /**
	 * Inicialização dos atributos relacionados com o AccessToken  
	 */
	public static void init() {
		 ConfigurationBuilder cb = new ConfigurationBuilder();
		Node twitterConfig = XMLclass.getNode(XMLclass.configFile, "twitter");
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey(twitterConfig.getAttributes().getNamedItem("ConsumerKey").getNodeValue())
		  .setOAuthConsumerSecret(twitterConfig.getAttributes().getNamedItem("ConsumerSecret").getNodeValue())
		  .setOAuthAccessToken(twitterConfig.getAttributes().getNamedItem("AccessToken").getNodeValue())
		  .setOAuthAccessTokenSecret(twitterConfig.getAttributes().getNamedItem("AccessTokenSecret").getNodeValue());
		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();
				
	 }
	
	
	/**
	 * Este procedimento permite que sejam adicionados tweets e o respectivo user à ObservableList, de modo a colocá-los na ListView. 
	 * Guarda a nova lista de tweets num ficheiro xml e invoca uma excecao nos casos em que se encontra offline
	 * @param tweetsList
	 * @return
	 * @throws TwitterException
	 */
	public ObservableList<Mensagem>  getTimeline( ) throws TwitterException{ 
	//	ListView<String> tweetsList = new ListView<>();
		Map<String, Map<String, String>> dataToStore = new HashMap<>();
		tweets.clear();
		try{
			Paging paging = new Paging(1, 40);
			List<Status> statuses = twitter.getHomeTimeline(paging);
			
			if (XMLclass.existsNode(XMLclass.storedDataFile, "twitter")) {
				XMLclass.deleteNode(XMLclass.storedDataFile, "twitter");
			}
			
			int counter=0;
	        for (Status status : statuses) {
	        	String userName = status.getUser().getName();
	        	String text =status.getText();
	        	Date dateCreated = status.getCreatedAt();
	        	String date = dateCreated.toString();
	        	String s = status.getUser().getName() + "\n" + status.getText();
	        	Mensagem m = new Mensagem(userName, date, text);
	        	
	        	Map<String, String> childAttributesToStore = new HashMap<>();
				childAttributesToStore.put("userName", userName);
				childAttributesToStore.put("dateCreated", date);
				childAttributesToStore.put("statusText", text);
				dataToStore.put("post" + counter, childAttributesToStore);
			
				tweets.add(counter, m);
				counter++;
	        }
	        
	        XMLclass.addElementAndChild(XMLclass.storedDataFile, "twitter", dataToStore);
	        
	      //  tweetsList.setItems(tweets);
			return tweets;
		} catch (Exception e) {
			if(e.getCause() instanceof UnknownHostException){
				System.out.println("problema de conexao");
				solveConectionProblems();
				return tweets;
			}
			else{
				e.printStackTrace();
			}
			return null;
		}
	}
	
	/**
	 * Funcao responsavel por resolver os problemas de conexao, colocando a ultima lista de tweets guardada
	 * @param tweetsList
	 */
	private void solveConectionProblems() {
		if (XMLclass.existsNode(XMLclass.storedDataFile, "twitter")) {
			Node twitterNode = XMLclass.getNode(XMLclass.storedDataFile, "twitter");
			int counter = 0;
			for (int i = 0; i < twitterNode.getChildNodes().getLength(); i++) {
				NamedNodeMap childAttributes = twitterNode.getChildNodes().item(i).getAttributes();
				if (childAttributes != null) {
					String userName = childAttributes.getNamedItem("userName").getNodeValue();
					String title = childAttributes.getNamedItem("statusText").getNodeValue();
					String date = childAttributes.getNamedItem("dateCreated").getNodeValue();
					Mensagem m= new Mensagem(userName, date, title);
					tweets.add(counter, m);
					counter++;
				}
			}
		}
	}


	/**
	 * Procedimento que filtra os tweets da timeline segundo determinada frase ou palavra
	 * @param quote
	 * @param tweetsList
	 * @return
	 * @throws TwitterException
	 */
	public boolean filter(String quote, ListView<Mensagem> tweetsList) throws TwitterException {
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
					Mensagem m = new Mensagem(status.getUser().getName(), status.getCreatedAt().toString(), status.getText());
					System.out.println(status.getUser().getName() + ":" + status.getText() );
					tweets.add(counter, m);
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
	
	/**
	 * Funcao responsavel por publicar novos tweets
	 * @param quote
	 * @return
	 */
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
	
	
	/**
	 * Funcao utilizada para obter lista de mensagens enviadas e recebidas, devido a updates recentes na api do twitter, 
	 * este código encontra-se desatualizado e não é utilizado de momento
	 * @return
	 */
	public DirectMessageList getMessageList(){
	
	/*	try {
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
		}*/
		return null;
	
	}
	

	

}
