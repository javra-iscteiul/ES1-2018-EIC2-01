package BDA.Facebook;

import java.util.ArrayList;
import java.util.List;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.GraphResponse;
import com.restfb.types.Post;
import com.restfb.types.send.IdMessageRecipient;

import BDA.XMLclass;
import org.w3c.dom.Node;

/**
 * Date: Oct 22 2018
 * @author ES1-2018-EIC2-01
 * @version 1.0
 * Aplicação agregadora de conteúdos académicos: canal do Facebook
 *
 */

public class Facebook implements IFacebook {

	private final Node facebookConfig = XMLclass.getElement("facebook");
	
	private final FacebookClient fbClient = new DefaultFacebookClient(facebookConfig.getAttributes().getNamedItem("AccessToken").getNodeValue());
	
	public void changeConfig() {
		// TODO Auto-generated method stub

	}

	public void createPost() {
		// TODO Auto-generated method stub

	}

	/**
	 * Este método permite que seja obtida uma lista dos posts de um utilizador na
	 * timeline do facebook (interface)
	 * 
	 * @return retorna uma lista dos posts do utilizador no facebook
	 */
	public List<Message> getTimeLine() {
		try {
			Connection<Post> results = fbClient.fetchConnection("me/feed", Post.class);

			int counter = 0;
			List<Message> posts = new ArrayList<Message>();
			for (List<Post> page : results) {
				for (Post aPost : page) {
					posts.add(counter, new Message(aPost.getName(), aPost.getCreatedTime(), aPost.getMessage()));
					counter++;
				}
			}
			return posts;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void setFilter() {
		// TODO Auto-generated method stub

	}

	public void getMessages() {
		// TODO Auto-generated method stub

	}

	public void sendMessage(String messageToSend) {
		fbClient.publish("me/feed", GraphResponse.class, Parameter.with("message", messageToSend));
	}
}
