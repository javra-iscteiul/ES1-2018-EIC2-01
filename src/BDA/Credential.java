package BDA;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.NamedNodeMap;

/**
 * Date: Oct 22 2018
 * 
 * @author ES1-2018-EIC2-01
 * @version 1.0 classe Credential, credenciais dos serviços
 */
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
	 * define o protocolo da credencial
	 * @param protocolo String
	 */
	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	/**
	 * devolve o username da credencial
	 * @return String
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * define o username da credencial
	 * @param username String
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * devolve a password da credencial
	 * @return String
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * define a password da credencial
	 * @param password String
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 *  devolve o consumerKey da credencial
	 * @return String
	 */
	public String getConsumerKey() {
		return consumerKey;
	}

	/**
	 * define o consumerKey da credencial
	 * @param consumerKey String
	 */
	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	/**
	 * devolve o consumerSecret da credencial
	 * @return String
	 */
	public String getConsumerSecret() {
		return consumerSecret;
	}

	/**
	 * define o consumerSecret da credencial
	 * @param consumerSecret String
	 */
	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	/**
	 * devolve o accessToken da credencial
	 * @return String
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * define o accessToken da credencial
	 * @param accessToken String
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * devolve o accessTokenSecret da credencial
	 * @return String
	 */
	public String getAccessTokenSecret() {
		return accessTokenSecret;
	}

	/**
	 * define o accessTokenSecret da credencial
	 * @param accessTokenSecret String
	 */
	public void setAccessTokenSecret(String accessTokenSecret) {
		this.accessTokenSecret = accessTokenSecret;
	}
	
	/**
	 * devolve o group da credencial
	 * @return String
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * define o group da credencial
	 * @param group String
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 *  devolve o login da credencial
	 * @return String
	 */
	public String getLogin() {
		return this.login;
	}

	/**
	 * define o login da credencial
	 * @param login String
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * retorna as credencias em tipo Map
	 * @return Map
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
	 *  retorna se a credencial é igual a credencial passada como parametro
	 * @param cred Credential
	 * @return boolean
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
