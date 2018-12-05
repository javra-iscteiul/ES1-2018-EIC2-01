package BDA;

import org.w3c.dom.DOMException;

import javafx.collections.ObservableList;

public interface IService {
	
	/**
	 * Inicia o servico com as credencias
	 * @param credential Credential
	 */
	public void init(Credential credential);
	
	/**
	 * devolve a lista de mensagens da timeline
	 * @return ObservableList
	 * @throws Exception e
	 */
	public ObservableList<Mensagem> getTimeLine() throws Exception;
	 
	/**
	 * devolve a lista de mensagens da timeline com o filtro aplicado
	 * @param s String
	 * @return ObservableList
	 * @throws Exception e
	 */
	public ObservableList<Mensagem> setFilter(String s) throws Exception;
}
