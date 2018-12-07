package BDA.Facebook;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.GraphResponse;
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
	
	/**
	 * variavel que indica se os posts a ver são do grupo
	 */
	private boolean group = false;
	
	/**
	 * Credencial da conta que fez login no facebook
	 */
	private Credential facebookCredential;
	
	public void init(Credential cred){
		this.facebookCredential = cred;
	}
	
	/**
	 * Este método permite que seja obtida uma lista dos posts do utilizador logado
	 * na timeline do facebook (interface)
	 * 
	 * @return retorna uma lista dos posts do utilizador no facebook
	 * @throws Exception e
	 */
	public ObservableList<Mensagem> getTimeLine() throws Exception {
		//variavel que vai ser utilizada para armazenar os dados recebidos no ficheiro xml, para ler que do tiver offline
		Map<String, Map<String, String>> dataToStore = new HashMap<>();
		posts.clear();
		
		try {
			FacebookClient fbClient = new DefaultFacebookClient(facebookCredential.getAccessToken());
			//cria a conecção com o facebook e recebe os posts
			Connection<Post> results;
			if(group)
				results = fbClient.fetchConnection("/" + facebookCredential.getGroup() + "/feed", Post.class);
			else
				results = fbClient.fetchConnection("me/feed", Post.class);
			
			//no caso de existirem posts guardados elimina-os
			if (XMLclass.existsNode(XMLclass.storedDataFile, XMLclass.facebookService, facebookCredential)) {
				XMLclass.deleteNode(XMLclass.storedDataFile, XMLclass.facebookService, facebookCredential);
			}

			//cria as mensagens para mostrar no ecrã e adiciona as mesmas a lista de dados a guardar
			int counter = 0;
			for (List<Post> page : results) {
				for (Post aPost : page) {
					String userName = aPost.getName();
					Date dateCreated = (group ? aPost.getUpdatedTime() : aPost.getCreatedTime());
					String dateCreatedString = (dateCreated.getDate() < 10 ? "0" : "") + dateCreated.getDate() + "/" 
							+ ((dateCreated.getMonth() + 1) < 10 ? "0" : "") + (dateCreated.getMonth() + 1) + "/"
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
	 * @throws Exception e
	 */
	public ObservableList<Mensagem> getStoredTimeLine() throws Exception {
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
	}

	/**
	 * Este método permite que seja obtida uma lista dos posts de um utilizador
	 * na timeline filtradas com o filtro passado como parametro (interface)
	 * @param filter String
	 * @return retorna uma lista dos posts filtrados do utilizador no facebook
	 */
	public ObservableList<Mensagem> setFilter(String filter) throws Exception {
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
	}
	
	/**
	 * Este método permite que seja obtida uma lista dos posts de um utilizador
	 * na timeline filtradas pelo utilizador que fez o post com o filtro passado como parametro (interface)
	 * @param filter String
	 * @return retorna uma lista dos posts do utilizador no facebook
	 * @throws Exception e
	 */
	public ObservableList<Mensagem> setUserFilter(String filter) throws Exception {
//			ObservableList<Mensagem> filteredMsg = FXCollections.observableArrayList();
//			for(Mensagem post : getTimeLine())
//			{
//				if(post.userContainsFilter(filter)) 
//					filteredMsg.add(post);
//			}
//			posts = filteredMsg;
//			
//			Map<String, String> filterAttr = new HashMap<>();
//			filterAttr.put("value", filter);
//			if(!XMLclass.existsChildNode(XMLclass.configFile, XMLclass.facebookService, facebookCredential, "filter", filterAttr))
//				XMLclass.addChild(XMLclass.configFile, XMLclass.facebookService, facebookCredential, "filter", filterAttr);
//			
//			return posts;
		return null;
	}
	
	
	/**
	 * Este método permite que seja obtida uma lista dos posts de um utilizador
	 * na timeline filtradas pelo tempo o filtro é passado como parametro (interface)
	 * @param filter String
	 * @return retorna uma lista dos posts do utilizador no facebook
	 * @throws Exception e
	 */
	public ObservableList<Mensagem> getLast(String filter) throws Exception {
			ObservableList<Mensagem> filteredMsg = FXCollections.observableArrayList();
			for(Mensagem post : getTimeLine())
			{				
				DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				Date date = format.parse(post.getDate());
				Instant now = Instant.now();
				if (filter == "24h") {
					if ((!date.toInstant().isBefore(now.minus(24, ChronoUnit.HOURS))) && (date.toInstant().isBefore(now))) {
						filteredMsg.add(post);
					}
				}else if (filter == "week") {
					if ((!date.toInstant().isBefore(now.minus(7, ChronoUnit.DAYS))) && (date.toInstant().isBefore(now))) {
						filteredMsg.add(post);
					}
				}else if (filter == "month") {					
					if ((!date.toInstant().isBefore(now.minus(30, ChronoUnit.DAYS))) && (date.toInstant().isBefore(now))) {
						filteredMsg.add(post);
					}
				}
			}
			posts = filteredMsg;
			
			Map<String, String> filterAttr = new HashMap<>();
			filterAttr.put("value", filter);
			if(!XMLclass.existsChildNode(XMLclass.configFile, XMLclass.facebookService, facebookCredential, "filter", filterAttr))
				XMLclass.addChild(XMLclass.configFile, XMLclass.facebookService, facebookCredential, "filter", filterAttr);
			
			return posts;
	}
	
	/**
	 * publica um post atraves do grupo no facebook
	 * @param publishText String
	 * @return boolean
	 */
	public boolean publish(String publishText){
		FacebookClient fbClient = new DefaultFacebookClient(facebookCredential.getAccessToken());
		GraphResponse response = fbClient.publish(facebookCredential.getGroup() + "/feed", GraphResponse.class, Parameter.with("message", publishText));
		return response.isSuccess();
	}
	
	/**
	 * @return retorna as credenciais da conta que fez login
	 */
	public Credential getCredential() {
		return this.facebookCredential;
	}
	
	/**
	 * Altera o grupo
	 * @param group boolean
	 */
	public void setGroup(boolean group){
		this.group = group;
	}
	
	/**
	 * Devolve o grupo
	 * @return boolean
	 */
	public boolean getGroup(){
		return group;
	}
}
