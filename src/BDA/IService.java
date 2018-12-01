package BDA;

import javafx.collections.ObservableList;

public interface IService {
	
	public ObservableList<Mensagem> getTimeLine();
	
	public ObservableList<Mensagem> setFilter(String s);
}
