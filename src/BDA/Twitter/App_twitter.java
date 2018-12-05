package BDA.Twitter;

import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import BDA.Credential;
import BDA.IService;
import BDA.Mensagem;
import BDA.XMLclass;
import BDA.Email.MensagemEmail;
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
 * 
 * @author ES1-2018-EIC2-01
 * @version 1.0 Aplica��o agregadora de aplica��es: canal do Twitter
 */
public final class App_twitter implements IService {

	private Credential twitterCredential;

	/**
	 * ObservableList com os tweets
	 */
	private ObservableList<Mensagem> tweets = FXCollections.observableArrayList();

	/**
	 * Atributo do tipo Twitter utilizados nos procedimentos seguintes
	 */
	private static Twitter twitter;

	/**
	 * Inicializa��o dos atributos relacionados com o AccessToken
	 */
	public void init(Credential cred) {
		twitterCredential = cred;

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(twitterCredential.consumerKey)
				.setOAuthConsumerSecret(twitterCredential.consumerSecret)
				.setOAuthAccessToken(twitterCredential.accessToken)
				.setOAuthAccessTokenSecret(twitterCredential.accessTokenSecret);
		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();

	}

	/**
	 * Este procedimento permite que sejam adicionados tweets e o respectivo
	 * user � ObservableList, de modo a coloc�-los na ListView. Guarda a nova
	 * lista de tweets num ficheiro xml e invoca uma excecao nos casos em que se
	 * encontra offline
	 * 
	 * @param tweetsList
	 * @return
	 * @throws Exception
	 * @throws TwitterException
	 */
	public ObservableList<Mensagem> getTimeLine() throws Exception {
		// ListView<String> tweetsList = new ListView<>();
		Map<String, Map<String, String>> dataToStore = new HashMap<>();
		tweets.clear();
		try {
			Paging paging = new Paging(1, 40);
			List<Status> statuses = twitter.getHomeTimeline(paging);

			if (XMLclass.existsNode(XMLclass.storedDataFile, XMLclass.twitterService, twitterCredential)) {
				XMLclass.deleteNode(XMLclass.storedDataFile, XMLclass.twitterService, twitterCredential);
			}

			int counter = 0;
			for (Status status : statuses) {
				String userName = status.getUser().getName();
				String text = status.getText();
				Date dateCreated = status.getCreatedAt();
				String date = dateCreated.toString();
				String s = status.getUser().getName() + "\n" + status.getText();
				MensagemTwitter m = new MensagemTwitter(userName, date, text);
	        	m.setMedia(status.getMediaEntities());
				Map<String, String> childAttributesToStore = new HashMap<>();
				childAttributesToStore.put("userName", userName);
				childAttributesToStore.put("dateCreated", date);
				childAttributesToStore.put("statusText", text);
				dataToStore.put("post" + counter, childAttributesToStore);

				tweets.add(counter, m);
				counter++;
			}

			XMLclass.addNodeAndChild(XMLclass.storedDataFile, XMLclass.twitterService, twitterCredential, dataToStore);

			// tweetsList.setItems(tweets);
			return tweets;
		} catch (Exception e) {
			solveConectionProblems();
			return tweets;
		}
	}

	/**
	 * Funcao responsavel por resolver os problemas de conexao, colocando a
	 * ultima lista de tweets guardada
	 * 
	 * @param tweetsList
	 * @throws Exception
	 */
	public boolean solveConectionProblems() throws Exception {
		if (XMLclass.existsNode(XMLclass.storedDataFile, XMLclass.twitterService, twitterCredential)) {
			Node twitterNode = XMLclass.getNode(XMLclass.storedDataFile, XMLclass.twitterService, twitterCredential);
			int counter = 0;
			for (int i = 0; i < twitterNode.getChildNodes().getLength(); i++) {
				NamedNodeMap childAttributes = twitterNode.getChildNodes().item(i).getAttributes();
				if (childAttributes != null) {
					String userName = childAttributes.getNamedItem("userName").getNodeValue();
					String title = childAttributes.getNamedItem("statusText").getNodeValue();
					String date = childAttributes.getNamedItem("dateCreated").getNodeValue();
					MensagemTwitter m = new MensagemTwitter(userName, date, title);
					tweets.add(counter, m);
					counter++;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Procedimento que filtra os tweets da timeline segundo determinada frase
	 * ou palavra
	 * 
	 * @param quote
	 * @param tweetsList
	 * @return
	 * @throws TwitterException
	 */
	public ObservableList<Mensagem> setFilter(String quote) throws TwitterException {
		ObservableList<Mensagem> nova = FXCollections.observableArrayList();
		System.out.println("------------------------\n Showing home timeline \n------------------------");
		int counter = 0;
		int counterTotal = 0;
		for (Mensagem status : tweets) {
			if (status.getUser() != null && status.getContent().contains(quote)) {
				// String s = status.getCreatedAt() + " " +
				// status.getUser().getName() + ":" + status.getText();
				MensagemTwitter m = new MensagemTwitter(status.getUser(), status.getDate().toString(),
						status.getContent());
				// System.out.println(status.getUser().getName() + ":" +
				// status.getText() );
				nova.add(counter, m);
				counter++;
			}
			counterTotal++;

		}

		System.out.println("-------------\nN� of Results: " + counter + "/" + counterTotal);
		return nova;
	}

	public ObservableList<Mensagem> filter_users(String quote) throws TwitterException {
		Paging paging = new Paging(1, 40);
		List<Status> statuses = twitter.getHomeTimeline(paging);
		System.out.println("------------------------\n Showing home timeline \n------------------------");
		int counter = 0;
		int counterTotal = 0;
		tweets.clear();
		for (Status status : statuses) {
			if (status.getUser().getName() != null && status.getUser().getName().contains(quote)) {
				MensagemTwitter m = new MensagemTwitter(status.getUser().getName(), status.getCreatedAt().toString(),
						status.getText());
				System.out.println("encontrado utilizador");
				tweets.add(counter, m);
				counter++;
			}
			counterTotal++;
		}

		// tweetsList.setItems(tweets);
		System.out.println("-------------\nN� of Results: " + counter + "/" + counterTotal);
		return tweets;
	}

	public ObservableList<Mensagem> timeFilter(String type) throws TwitterException {
		ObservableList<Mensagem> nova = FXCollections.observableArrayList();	
		try {
			List<Status> statuses = twitter.getHomeTimeline();
			int counter = 0;
			int counterTotal = 0;
			tweets.clear();
			for (Status status : statuses) {
				DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
				Date date = format.parse(status.getCreatedAt().toString());
				Instant now = Instant.now();
				
				if(type.equals("lastDay")){
					
					if ((!date.toInstant().isBefore(now.minus(24, ChronoUnit.HOURS))) && (date.toInstant().isBefore(now))) {
						
						MensagemTwitter m = new MensagemTwitter(status.getUser().getName(), status.getCreatedAt().toString(),
								status.getText());
						nova.add(counter, m);
						counter++;
					}
				} if (type.equals("lastMonth")){
					System.out.println("yo" + now);
					if ((!date.toInstant().isBefore(now.minus(30, ChronoUnit.DAYS))) && (date.toInstant().isBefore(now))) {
						MensagemTwitter m = new MensagemTwitter(status.getUser().getName(), status.getCreatedAt().toString(),
								status.getText());
						nova.add(counter, m);
						counter++;
					}
					
				}
				counterTotal++;
			}
			return nova;
			
		} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
		}
	}
		

		// tweetsList.setItems(tweets);
	//	System.out.println("-------------\nN� of Results: " + counter + "/" + counterTotal);
	
//		System.out.println("ola");
//		Query q = new Query(type);
//		if (q.getQuery().equals("lastDay")) {
//			System.out.println("entrei");
//			SimpleDateFormat sdf = new SimpleDateFormat("y-M-d");
//
//			  Calendar calendar = Calendar.getInstance();
//			  calendar.add(Calendar.HOUR_OF_DAY, -24);
//
//			  String day = sdf.format(calendar.getTime());
//			  System.out.println(day);
//			  q.setSince(day);
//		} else if (q.getQuery().equals("lastMonth")) {
//			SimpleDateFormat sdf = new SimpleDateFormat("y-M-d");
//
//			  Calendar calendar = Calendar.getInstance();
//			  calendar.add(Calendar.DAY_OF_YEAR, -30);
//
//			  String day = sdf.format(calendar.getTime());
//			  System.out.println(day);
//			  q.setSince(day);
//		}
//
//		QueryResult result = twitter.search(q);
//		List<Status> stat= twitter.getHomeTimeline();
//		
//		List<Status> statuses = result.getTweets();
//		ObservableList<Mensagem> nova = FXCollections.observableArrayList();
//		int counter = 0;
//		for (Status status : stat) {
//			
//			// String s = status.getCreatedAt() + " " +
//			// status.getUser().getName() + ":" + status.getText();
//			Relationship relation = twitter.showFriendship(twitter.getScreenName(), status.getUser().getScreenName());
//			System.out.println(relation.isSourceFollowingTarget());
//			if(relation.isSourceFollowingTarget()){
//				MensagemTwitter m = new MensagemTwitter(status.getUser().getName(), status.getCreatedAt().toString(),
//						status.getText());
//				// System.out.println(status.getUser().getName() + ":" +
//				// status.getText() );
//				System.out.println(m.toString());
//				nova.add(counter, m);
//				counter++;
//			}
//		}
//
//		return nova;
	

	/**
	 * Funcao responsavel por publicar novos tweets
	 * 
	 * @param quote
	 * @return
	 * @throws TwitterException 
	 */
	public String post(String quote) throws TwitterException {
		twitter.updateStatus(quote);
		System.out.println("Successfully updated the status to [" + quote + "].");
		return quote;
	}

	public Credential getCredential() {
		return this.twitterCredential;
	}
}
