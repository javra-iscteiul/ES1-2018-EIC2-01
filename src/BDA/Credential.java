package BDA;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.NamedNodeMap;

public class Credential {
	/**
	 * Protocolo do serviço
	 */
	public String protocolo;
	/**
	 * User Name do utilizador
	 */
	public String username;
	/**
	 * Password do utilizador
	 */
	public String password;
	/**
	 * Consumer Key do Utilizador
	 */
	public String consumerKey;
	/**
	 * Consumer Secret do Utilizador
	 */
	public String consumerSecret;
	/**
	 * AccesToken do Utilizador
	 */
	public String accessToken;
	/**
	 * AccessTokenSecret do Utilizador
	 */
	public String accessTokenSecret;
	/**
	 * Define se o utilizador esta logado(TRUE,FALSE)
	 */
	public String login;
	/**
	 * Groupo a que o utilizador pertence(Facebook)
	 */
	public String group;
	
	/**
	 * @param protocolo String
	 * @param username  String
	 * @param password  String
	 * @param consumerKey  String
	 * @param consumerSecret  String
	 * @param accessToken  String
	 * @param accessTokenSecret  String
	 */
	public Credential(String protocolo, String username, String password, String consumerKey, String consumerSecret,
			String accessToken, String accessTokenSecret) {
		this.protocolo = protocolo;
		this.username = username;
		this.password = password;
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
		this.accessToken = accessToken;
		this.accessTokenSecret = accessTokenSecret;
		this.login = "False";
	}
	
	/**
	 * @param protocolo String
	 * @param username  String
	 * @param password  String
	 * @param consumerKey  String
	 * @param consumerSecret  String
	 * @param accessToken  String
	 * @param accessTokenSecret  String
	 * @param grupo  String
	 */
	public Credential(String protocolo, String username, String password, String consumerKey, String consumerSecret,
			String accessToken, String accessTokenSecret, String grupo) {
		this.protocolo = protocolo;
		this.username = username;
		this.password = password;
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
		this.accessToken = accessToken;
		this.accessTokenSecret = accessTokenSecret;
		this.group = grupo;
		this.login = "False";
	}

	/**
	 * @param username String
	 * @param password String
	 */
	public Credential(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/**
	 * @param credentials NamedNodeMap
	 */
	public Credential(NamedNodeMap credentials) {
		if (credentials.getNamedItem("Protocol") != null)
			this.protocolo = credentials.getNamedItem("Protocol").getNodeValue();
		if (credentials.getNamedItem("UserName") != null)
			this.username = credentials.getNamedItem("UserName").getNodeValue();
		if (credentials.getNamedItem("Password") != null)
			this.password = credentials.getNamedItem("Password").getNodeValue();
		if (credentials.getNamedItem("ConsumerKey") != null)
			this.consumerKey = credentials.getNamedItem("ConsumerKey").getNodeValue();
		if (credentials.getNamedItem("ConsumerSecret") != null)
			this.consumerSecret = credentials.getNamedItem("ConsumerSecret").getNodeValue();
		if (credentials.getNamedItem("AccessToken") != null)
			this.accessToken = credentials.getNamedItem("AccessToken").getNodeValue();
		if (credentials.getNamedItem("AccessTokenSecret") != null)
			this.accessTokenSecret = credentials.getNamedItem("AccessTokenSecret").getNodeValue();
		if (credentials.getNamedItem("Group") != null)
			this.group = credentials.getNamedItem("Group").getNodeValue();
		if (credentials.getNamedItem("Login") != null)
			this.login = credentials.getNamedItem("Login").getNodeValue();
	}

	/**
	 * @return devolve o protocolo da credencial
	 */
	public String getProtocolo() {
		return protocolo;
	}

	/**
	 * @param define o protocolo da credencial
	 */
	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	/**
	 * @return devolve o username da credencial
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param define o username da credencial
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return devolve a password da credencial
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param define a password da credencial
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return devolve o consumerKey da credencial
	 */
	public String getConsumerKey() {
		return consumerKey;
	}

	/**
	 * @param define o consumerKey da credencial
	 */
	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	/**
	 * @return devolve o consumerSecret da credencial
	 */
	public String getConsumerSecret() {
		return consumerSecret;
	}

	/**
	 * @param define o consumerSecret da credencial
	 */
	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	/**
	 * @return devolve o accessToken da credencial
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * @param define o accessToken da credencial
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * @return devolve o accessTokenSecret da credencial
	 */
	public String getAccessTokenSecret() {
		return accessTokenSecret;
	}

	/**
	 * @param define o accessTokenSecret da credencial
	 */
	public void setAccessTokenSecret(String accessTokenSecret) {
		this.accessTokenSecret = accessTokenSecret;
	}
	
	/**
	 * @return devolve o group da credencial
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * @param define o group da credencial
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * @return devolve o login da credencial
	 */
	public String getLogin() {
		return this.login;
	}

	/**
	 * @param define o login da credencial
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return retrna as credencias em tipo Map<String, String>
	 */
	public Map<String, String> getCredentiaAsMap() {
		Map<String, String> cred = new HashMap<String, String>();
		cred.put("Protocol", this.protocolo);
		cred.put("UserName", this.username);
		cred.put("Password", this.password);
		cred.put("ConsumerKey", this.consumerKey);
		cred.put("ConsumerSecret", this.consumerSecret);
		cred.put("AccessToken", this.accessToken);
		cred.put("AccessTokenSecret", this.accessTokenSecret);
		cred.put("Group", this.group);
		cred.put("Login", this.login);
		return cred;
	}

	/**
	 * @param cred
	 * @return retorna se a credencial é igual a credencial passada como parametro
	 */
	public boolean equals(Credential cred) {
		return this.username.equals(cred.username) && this.password.equals(cred.password);
	}

	@Override
	public String toString() {
		return (this.protocolo != "" ? "Protocol: " + this.protocolo + "\r\n" : "")
				+ (this.username != "" ? "UserName: " + this.username + "\r\n" : "")
				+ (this.password != "" ? "Password: " + this.password + "\r\n" : "")
				+ (this.group != "" ? "Group: " + this.group + "\r\n" : "")
				+ (this.consumerKey != "" ? "Consumer Key: " + this.consumerKey + "\r\n" : "")
				+ (this.consumerSecret != "" ? "Consumer Secret: " + this.consumerSecret + "\r\n" : "")
				+ (this.accessToken != "" ? "Access Token: " + this.accessToken + "\r\n" : "")
				+ (this.accessTokenSecret != "" ? "Access Token Secret: " + this.accessTokenSecret : "");
	}
}
