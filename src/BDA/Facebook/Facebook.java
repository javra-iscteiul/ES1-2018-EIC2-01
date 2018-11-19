package BDA.Facebook;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Post;

import BDA.XMLclass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Date: Oct 22 2018
 * 
 * @author ES1-2018-EIC2-01
 * @version 1.0 Aplicação agregadora de conteúdos académicos: canal do Facebook
 *
 */

public class Facebook implements IFacebook {

	private final Node facebookConfig = XMLclass.getElement(XMLclass.configFile, "facebook");

	private final FacebookClient fbClient = new DefaultFacebookClient(
			facebookConfig.getAttributes().getNamedItem("AccessToken").getNodeValue());

	/**
	 * ObservableList com os posts do facebook
	 */
	private ObservableList<Message> posts = FXCollections.observableArrayList();
	
	public void changeConfig() {
		// TODO Auto-generated method stub

	}

	public void createPost() {
		// TODO Auto-generated method stub

	}

	/**
	 * Este método permite que seja obtida uma lista dos posts de um utilizador
	 * na timeline do facebook (interface)
	 * 
	 * @return retorna uma lista dos posts do utilizador no facebook
	 */
	public boolean getTimeLine(ListView<Message> postsList) {
		Map<String, Map<String, String>> dataToStore = new HashMap<>();
		posts.clear();
		postsList.getItems().clear();
		
		try {
			Connection<Post> results = fbClient.fetchConnection("me/feed", Post.class);

			if (XMLclass.existsElement(XMLclass.storedDataFile, "facebook")) {
				XMLclass.deleteElement(XMLclass.storedDataFile, "facebook");
			}

			int counter = 0;
			for (List<Post> page : results) {
				for (Post aPost : page) {
					String userName = aPost.getName();
					Date dateCreated = aPost.getCreatedTime();
					String dateCreatedString = dateCreated.getDate() + "/" + dateCreated.getMonth() + "/"
							+ (dateCreated.getYear() + 1900);
					String title = aPost.getMessage();

					Map<String, String> childAttributesToStore = new HashMap<>();
					childAttributesToStore.put("userName", userName);
					childAttributesToStore.put("dateCreated", dateCreatedString);
					childAttributesToStore.put("title", title);
					dataToStore.put("post" + counter, childAttributesToStore);

					posts.add(counter, new Message(userName, dateCreatedString, title));
					counter++;
				}
			}

			XMLclass.addElementAndChild(XMLclass.storedDataFile, "facebook", dataToStore);

			postsList.setItems(posts);
			return true;
		} catch (Exception e) {
			return getStoredTimeLine(postsList);
		}
	}

	private boolean getStoredTimeLine(ListView<Message> postsList) {
		try {
			if (XMLclass.existsElement(XMLclass.storedDataFile, "facebook")) {
				Node facebookNode = XMLclass.getElement(XMLclass.storedDataFile, "facebook");

				int counter = 0;
				for (int i = 0; i < facebookNode.getChildNodes().getLength(); i++) {
					NamedNodeMap childAttributes = facebookNode.getChildNodes().item(i).getAttributes();

					if (childAttributes != null) {
						String userName = childAttributes.getNamedItem("userName").getNodeValue();
						String dateCreated = childAttributes.getNamedItem("dateCreated").getNodeValue();
						String title = childAttributes.getNamedItem("title").getNodeValue();

						posts.add(counter, new Message(userName, dateCreated, title));
						counter++;
					}
				}
			}
			
			postsList.setItems(posts);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean setFilter(ListView<Message> postsList, String filter) {
		try {
			this.getTimeLine(postsList);
			
			List<Message> newMessage = new ArrayList<Message>();
			for(Message post : posts)
			{
				if((post.getTitle() != null && post.getTitle().contains(filter)) ||
						(post.getDateCreated() != null && post.getDateCreated().contains(filter)))
					newMessage.add(post);
			}
			
			posts.clear();
			for (Message post : newMessage)
				posts.add(post);
			
			postsList.setItems(posts);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public void getMessages() {
		// TODO Auto-generated method stub

	}

	public void sendMessage(String messageToSend) {
		// TODO Auto-generated method stub
	}
}
