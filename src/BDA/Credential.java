package BDA;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.NamedNodeMap;

public class Credential {
	public String protocolo;
	public String username;
	public String password;
	public String consumerKey;
	public String consumerSecret;
	public String accessToken;
	public String accessTokenSecret;
	public String login;
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

	public Credential(String username, String password) {
		this.username = username;
		this.password = password;
	}

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

	public String getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConsumerKey() {
		return consumerKey;
	}

	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccessTokenSecret() {
		return accessTokenSecret;
	}

	public void setAccessTokenSecret(String accessTokenSecret) {
		this.accessTokenSecret = accessTokenSecret;
	}
	
	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

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

	public boolean equals(Credential cred) {
		return this.username.equals(cred.username) && this.password.equals(cred.password);
	}

	@Override
	public String toString() {
		return (this.protocolo != "" ? "Protocol: " + this.protocolo + "\r\n" : "")
				+ (this.username != "" ? "UserName: " + this.username + "\r\n" : "")
				+ (this.password != "" ? "Password: " + this.password + "\r\n" : "")
				+ (this.consumerKey != "" ? "Consumer Key: " + this.consumerKey + "\r\n" : "")
				+ (this.consumerSecret != "" ? "Consumer Secret: " + this.consumerSecret + "\r\n" : "")
				+ (this.accessToken != "" ? "Access Token: " + this.accessToken + "\r\n" : "")
				+ (this.accessTokenSecret != "" ? "Access Token Secret: " + this.accessTokenSecret + "\r\n" : "")
				+ (this.group != "" ? "Group: " + this.group : "");
	}
}
