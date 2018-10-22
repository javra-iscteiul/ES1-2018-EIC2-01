package BDA.Email;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;


public class Email {



	  private static final String authority = "https://login.microsoftonline.com";
	  private static final String authorizeUrl = authority + "/common/oauth2/v2.0/authorize";

	  private static String[] scopes = { 
	    "openid", 
	    "offline_access",
	    "profile", 
	    "User.Read",
	    "Mail.Read"
	  };

	  private static String appId = null;
	  private static String appPassword = null;
	  private static String redirectUrl = null;

	  private static String getAppId() {
	    if (appId == null) {
	      try {
	        loadConfig();
	      } catch (Exception e) {
	        return null;
	      }
	    }
	    return appId;
	  }
	  private static String getAppPassword() {
	    if (appPassword == null) {
	      try {
	        loadConfig();
	      } catch (Exception e) {
	        return null;
	      }
	    }
	    return appPassword;
	  }

	  private static String getRedirectUrl() {
	    if (redirectUrl == null) {
	      try {
	        loadConfig();
	      } catch (Exception e) {
	        return null;
	      }
	    }
	    return redirectUrl;
	  }

	  private static String getScopes() {
	    StringBuilder sb = new StringBuilder();
	    for (String scope: scopes) {
	      sb.append(scope + " ");
	    }
	    return sb.toString().trim();
	  }

	  private static void loadConfig() throws IOException {
	    String authConfigFile = "auth.properties";
	    InputStream authConfigStream = Email.class.getClassLoader().getResourceAsStream(authConfigFile);

	    if (authConfigStream != null) {
	      Properties authProps = new Properties();
	      try {
	        authProps.load(authConfigStream);
	        appId = authProps.getProperty("appId");
	        appPassword = authProps.getProperty("appPassword");
	        redirectUrl = authProps.getProperty("redirectUrl");
	      } finally {
	        authConfigStream.close();
	      }
	    }
	    else {
	      throw new FileNotFoundException("Property file '" + authConfigFile + "' not found in the classpath.");
	    }
	  }

	
	  
	}

