package BDA;

import javafx.collections.ObservableList;

/**
 * Date: Oct 22 2018
 * 
 * @author ES1-2018-EIC2-01
 * @version 1.0 interface dos diversos serviços com funçoes comuns
 */
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
