package BDA;


/**
 * Date: Oct 22 2018
 * 
 * @author ES1-2018-EIC2-01
 * @version 1.0 interface do Controller dos diferentes serviços
 */
public interface IServiceController {	
	/**
	 * 
	 * @param cred Credential
	 * @throws Exception e
	 */
	public void init(Credential cred) throws Exception;
}
