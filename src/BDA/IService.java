package BDA;

import org.w3c.dom.DOMException;

import javafx.collections.ObservableList;

public interface IService {
	
	public void init(Credential credential);
	
	public ObservableList<Mensagem> getTimeLine() throws Exception;
	 
	public ObservableList<Mensagem> setFilter(String s) throws Exception;
}
