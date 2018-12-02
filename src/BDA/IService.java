package BDA;

import javafx.collections.ObservableList;

public interface IService {
	
	public void init(Credential credential);
	
	public ObservableList<Mensagem> getTimeLine();
	 
	public ObservableList<Mensagem> setFilter(String s);
}
