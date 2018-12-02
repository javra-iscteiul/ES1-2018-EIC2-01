package BDA.Facebook;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Post;

import BDA.Credential;
import BDA.IService;
import BDA.Mensagem;
import BDA.XMLclass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Date: Oct 22 2018
 * 
 * @author ES1-2018-EIC2-01
 * @version 1.0 Aplicação agregadora de conteúdos académicos: canal do Facebook
 *
 */

public class Facebook implements IService {
	/**
	 * ObservableList com os posts do facebook
	 */
	private ObservableList<Mensagem> posts = FXCollections.observableArrayList();
	
	private Credential facebookCredential;
	
	public void init(Credential cred){
		this.facebookCredential = cred;
	}
	
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
	public ObservableList<Mensagem> getTimeLine() {
		//variavel que vai ser utilizada para armazenar os dados recebidos no ficheiro xml, para ler que do tiver offline
		Map<String, Map<String, String>> dataToStore = new HashMap<>();
		posts.clear();
		
		try {
			FacebookClient fbClient = new DefaultFacebookClient(facebookCredential.getAccessToken());
			//cria a conecção com o facebook e recebe os posts
			Connection<Post> results = fbClient.fetchConnection("me/feed", Post.class);

			//no caso de existirem posts guardados elimina-os
			if (XMLclass.existsNode(XMLclass.storedDataFile, XMLclass.facebookService, facebookCredential)) {
				XMLclass.deleteNode(XMLclass.storedDataFile, XMLclass.facebookService, facebookCredential);
			}

			//cria as mensagens para mostrar no ecrã e adiciona as mesmas a lista de dados a guardar
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

					posts.add(counter, new MensagemFacebook(userName, dateCreatedString, title));
					counter++;
				}
			}

			XMLclass.addNodeAndChild(XMLclass.storedDataFile, XMLclass.facebookService, facebookCredential, dataToStore);

			return posts;
		} catch (Exception e) {
			//no caso de dar erro vai buscar os dados guardados na sessão anterior
			return getStoredTimeLine();
		}
	}

	/**
	 * Este método permite que seja obtida uma lista dos posts de um utilizador
	 * na timeline do facebook da sessão anterior, ou seja está offline (interface)
	 * 
	 * @return retorna uma lista dos posts do utilizador no facebook da sessão anterior
	 */
	private ObservableList<Mensagem> getStoredTimeLine() {
		try {
			if (XMLclass.existsNode(XMLclass.storedDataFile, XMLclass.facebookService, facebookCredential)) {
				Node facebookNode = XMLclass.getNode(XMLclass.storedDataFile, XMLclass.facebookService, facebookCredential);

				int counter = 0;
				for (int i = 0; i < facebookNode.getChildNodes().getLength(); i++) {
					NamedNodeMap childAttributes = facebookNode.getChildNodes().item(i).getAttributes();

					if (childAttributes != null) {
						String userName = childAttributes.getNamedItem("userName").getNodeValue();
						String dateCreated = childAttributes.getNamedItem("dateCreated").getNodeValue();
						String title = childAttributes.getNamedItem("title").getNodeValue();

						posts.add(counter, new MensagemFacebook(userName, dateCreated, title));
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
	 * Este método permite que seja obtida uma lista dos posts de um utilizador
	 * na timeline filtradas com o filtro passado como parametro (interface)
	 * 
	 * @return retorna uma lista dos posts do utilizador no facebook
	 */
	public ObservableList<Mensagem> setFilter(String filter) {
		try {
			ObservableList<Mensagem> filteredMsg = FXCollections.observableArrayList();
			for(Mensagem post : getTimeLine())
			{
				if(post.containsFilter(filter)) 
					filteredMsg.add(post);
			}
			posts = filteredMsg;
			
			Map<String, String> filterAttr = new HashMap<>();
			filterAttr.put("value", filter);
			if(!XMLclass.existsChildNode(XMLclass.configFile, XMLclass.facebookService, facebookCredential, "filter", filterAttr))
				XMLclass.addChild(XMLclass.configFile, XMLclass.facebookService, facebookCredential, "filter", filterAttr);
			
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
