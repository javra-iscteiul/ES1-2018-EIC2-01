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
	}
	
	public Credential(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public Credential(NamedNodeMap credentials) {
		if(credentials.getNamedItem("Protocol") != null)
			this.protocolo = credentials.getNamedItem("Protocol").getNodeValue();
		if(credentials.getNamedItem("UserName") != null)
			this.username = credentials.getNamedItem("UserName").getNodeValue();
		if(credentials.getNamedItem("Password") != null)
			this.password = credentials.getNamedItem("Password").getNodeValue();
		if(credentials.getNamedItem("ConsumerKey") != null)
			this.consumerKey = credentials.getNamedItem("ConsumerKey").getNodeValue();
		if(credentials.getNamedItem("ConsumerSecret") != null)
			this.consumerSecret = credentials.getNamedItem("ConsumerSecret").getNodeValue();
		if(credentials.getNamedItem("AccessToken") != null)
			this.accessToken = credentials.getNamedItem("AccessToken").getNodeValue();
		if(credentials.getNamedItem("AccessTokenSecret") != null)
			this.accessTokenSecret = credentials.getNamedItem("AccessTokenSecret").getNodeValue();
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
	
	public Map<String, String> getCredentiaAsMap(){
		Map<String, String> cred = new HashMap<String, String>();
		cred.put("Protocol", this.protocolo);
		cred.put("UserName", this.username);
		cred.put("Password", this.password);
		cred.put("ConsumerKey", this.consumerKey);
		cred.put("ConsumerSecret", this.consumerSecret);
		cred.put("AccessToken", this.accessToken);
		cred.put("AccessTokenSecret", this.accessTokenSecret);
		return cred;
	}
	
	public boolean equals(Credential cred){
		return this.username.equals(cred.username) && this.password.equals(cred.password);
	}
}
