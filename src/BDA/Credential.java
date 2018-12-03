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

	/**
	 * @param protocolo
	 * @param username
	 * @param password
	 * @param consumerKey
	 * @param consumerSecret
	 * @param accessToken
	 * @param accessTokenSecret
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

	public String getLogin() {
		return accessTokenSecret;
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
		cred.put("Login", this.login);
		return cred;
	}

	public boolean equals(Credential cred) {
		return this.username.equals(cred.username) && this.password.equals(cred.password);
	}

	@Override
	public String toString() {
		return (this.protocolo != null ? "Protocol:" + this.protocolo + "\r\n" : "")
				+ (this.username != null ? "UserName:" + this.username + "\r\n" : "")
				+ (this.password != null ? "Password:" + this.password + "\r\n" : "")
				+ (this.consumerKey != null ? "Consumer Key:" + this.consumerKey + "\r\n" : "")
				+ (this.consumerSecret != null ? "Consumer Secret:" + this.consumerSecret + "\r\n" : "")
				+ (this.accessToken != null ? "Access Token:" + this.accessToken + "\r\n" : "")
				+ (this.accessTokenSecret != null ? "Access Token Secret:" + this.accessTokenSecret : "");
	}
}
