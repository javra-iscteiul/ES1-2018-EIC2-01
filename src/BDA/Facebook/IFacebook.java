package BDA.Facebook;

import java.util.List;

import javafx.scene.control.ListView;

/**
 * Date: Oct 22 2018
 * @author ES1-2018-EIC2-01
 * @version 1.0 
 * 			Esta interface fornece procedimentos que permitem realizar
 *          operações para determinados objetos de forma a aceder às informações
 *          disponibilizadas neste canal (facebook)
 */
public interface IFacebook {

	public void changeConfig();

	public void createPost();

	public boolean getTimeLine(ListView<Message> postsList);

	public boolean setFilter(ListView<Message> postsList, String filter);

	public void getMessages();

	public void sendMessage(String messageToSend);
}
