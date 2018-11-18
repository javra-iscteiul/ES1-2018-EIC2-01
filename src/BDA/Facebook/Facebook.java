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
	public List<Message> getTimeLine() {
		try {
			Connection<Post> results = fbClient.fetchConnection("me/feed", Post.class);

			if (XMLclass.existsElement(XMLclass.storedDataFile, "facebook")) {
				XMLclass.deleteElement(XMLclass.storedDataFile, "facebook");
			}

			int counter = 0;
			List<Message> posts = new ArrayList<Message>();
			Map<String, Map<String, String>> dataToStore = new HashMap<>();
			for (List<Post> page : results) {
				for (Post aPost : page) {
					String userName = aPost.getName();
					Date dateCreated = aPost.getCreatedTime();
					String dateCreatedString = dateCreated.getDate() + "/" + dateCreated.getMonth() + "/" + (dateCreated.getYear() + 1900);
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

			return posts;
		} catch (Exception e) {
			return getStoredTimeLine();
		}
	}

	private List<Message> getStoredTimeLine() {
		List<Message> posts = new ArrayList<Message>();

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

			return posts;
		} catch (Exception ex) {
			return null;
		}
	}

	public void setFilter(String filter) {
		// TODO Auto-generated method stub

	}

	public void getMessages() {
		// TODO Auto-generated method stub

	}

	public void sendMessage(String messageToSend) {
		// TODO Auto-generated method stub
	}
}
