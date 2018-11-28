package BDA.Facebook;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Post;

import BDA.XMLclass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Date: Oct 22 2018
 * 
 * @author ES1-2018-EIC2-01
 * @version 1.0 Aplica��o agregadora de conte�dos acad�micos: canal do Facebook
 *
 */

public class Facebook implements IFacebook {

	/**
	 * Node correspondente �s configura��es do facebook no ficheiro config
	 */
	private final Node facebookConfig = XMLclass.getNode(XMLclass.configFile, "facebook");

	/**
	 * FacebookClient correspondente � conec��o do cliente ao facebook
	 */
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
	 * Este m�todo permite que seja obtida uma lista dos posts de um utilizador
	 * na timeline do facebook (interface)
	 * 
	 * @return retorna uma lista dos posts do utilizador no facebook
	 */
	public ObservableList<Message> getTimeLine() {
		//variavel que vai ser utilizada para armazenar os dados recebidos no ficheiro xml, para ler que do tiver offline
		Map<String, Map<String, String>> dataToStore = new HashMap<>();
		posts.clear();
		
		try {
			//cria a conec��o com o facebook e recebe os posts
			Connection<Post> results = fbClient.fetchConnection("me/feed", Post.class);

			//no caso de existirem posts guardados elimina-os
			if (XMLclass.existsNode(XMLclass.storedDataFile, "facebook")) {
				XMLclass.deleteNode(XMLclass.storedDataFile, "facebook");
			}

			//cria as mensagens para mostrar no ecr� e adiciona as mesmas a lista de dados a guardar
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

			return posts;
		} catch (Exception e) {
			//no caso de dar erro vai buscar os dados guardados na sess�o anterior
			return getStoredTimeLine();
		}
	}

	/**
	 * Este m�todo permite que seja obtida uma lista dos posts de um utilizador
	 * na timeline do facebook da sess�o anterior, ou seja est� offline (interface)
	 * 
	 * @return retorna uma lista dos posts do utilizador no facebook da sess�o anterior
	 */
	private ObservableList<Message> getStoredTimeLine() {
		try {
			if (XMLclass.existsNode(XMLclass.storedDataFile, "facebook")) {
				Node facebookNode = XMLclass.getNode(XMLclass.storedDataFile, "facebook");

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

	/**
	 * Este m�todo permite que seja obtida uma lista dos posts de um utilizador
	 * na timeline filtradas com o filtro passado como parametro (interface)
	 * 
	 * @return retorna uma lista dos posts do utilizador no facebook
	 */
	public ObservableList<Message> setFilter(String filter) {
		try {
			posts.clear();
			for(Message post : getTimeLine())
			{
				if((post.getTitle() != null && post.getTitle().contains(filter)) ||
						(post.getDateCreated() != null && post.getDateCreated().contains(filter)))
					posts.add(post);
			}
			
			Map<String, String> filterAttr = new HashMap<>();
			filterAttr.put("value", filter);
			if(!XMLclass.existsChildNode(XMLclass.configFile, "facebook", "filter", filterAttr))
				XMLclass.addChild(XMLclass.configFile, "facebook", "filter", filterAttr);
			
			return posts;
		} catch (Exception ex) {
			return null;
		}
	}

	public void getMessages() {
		// TODO Auto-generated method stub

	}

	public void sendMessage(String messageToSend) {
		// TODO Auto-generated method stub
	}
}
