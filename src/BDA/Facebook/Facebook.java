package BDA.Facebook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Post;

import BDA.XMLclass;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.w3c.dom.Node;

public class Facebook{
	
	public void changeConfig() {
		// TODO Auto-generated method stub
		
	}

	public void createPost() {
		// TODO Auto-generated method stub
		
	}

	public static List<String> getTimeLine() {
		Node facebookConfig = XMLclass.getElement("facebook");
		
		FacebookClient fbClient = new DefaultFacebookClient(facebookConfig.getAttributes().getNamedItem("AccessToken").getNodeValue());
		
		Connection<Post> results = fbClient.fetchConnection("me/feed", Post.class);
		
		int counter = 0;
		List<String> posts = new ArrayList<String>();
		for(List<Post> page : results){
			for(Post aPost : page){
				String s = aPost.getName() + ":" + aPost.getMessage() + ":" + "fb.com/" + aPost.getId();
				
				posts.add(counter, s);
			}
		}
		return posts;
	}

	public void setFilter() {
		// TODO Auto-generated method stub
		
	}

	public void getMessages() {
		// TODO Auto-generated method stub
		
	}

	public void sendMessage() {
		// TODO Auto-generated method stub
		
	}
}
