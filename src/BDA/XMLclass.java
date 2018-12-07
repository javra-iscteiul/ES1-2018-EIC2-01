package BDA;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

/**
 * Date: Oct 22 2018
 * 
 * @author ES1-2018-EIC2-01
 * @version 1.0 Classe responsavel por guardas as credenciais no ficheiro
 *          config.xml
 */
public class XMLclass {

	/**
	 * Propriedade do nome do serviço facebook
	 */
	public static String facebookService = "facebook";

	/**
	 * Propriedade do nome do serviço twitter
	 */
	public static String twitterService = "twitter";

	/**
	 * Propriedade do nome do serviço email
	 */
	public static String emailService = "email";

	/**
	 * Propriedade que define o Login na credencial
	 */
	public static String Login = "True";

	/**
	 * Propriedade que define o Logout na credencial
	 */
	public static String Logout = "False";

	/**
	 * Atributo do tipo File responsavel por definir o destino das credenciais
	 */
	public static File configFile = new File("config.xml");
	
	/**
	 * Atributo do tipo File responsavel por guardar os posts para consultar em
	 * modo offline
	 */
	public static File storedDataFile = new File("config.xml");
	
	/**
	 * Atributo do tipo File responsavel pelos testes
	 */
	public static File testFile = new File("test.xml");
	
	/**
	 * Atributo do tipo DocumentBuilderFactory responsavel por construir um
	 * documento a partir do ficheiro dado
	 */
	private static DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

	/**
	 * Dado um determinado serviço e as credenciais do utilizador verifica se o mesmo já existe no documento
	 *@param inputFile file 
	 * @param service
	 *            String
	 *@param serviceCredencial Credential
	 * @return boolean
	 * @throws Exception e
	 */
	public static boolean existsNode(File inputFile, String service, Credential serviceCredencial) throws Exception {
		return XMLclass.getNode(inputFile, service, serviceCredencial) != null;
	}

	/**
	 * Dado um determinado serviço verifica se existe algum utilizador logado
	 * @param service
	 *            String
	 * @param inputFile File
	 * @return boolean
	 * @throws Exception e
	 */
	public static boolean existsLogin(File inputFile, String service) throws Exception {
		return XMLclass.getLogin(inputFile, service) != null;
	}

	/**
	 * Dado um determinado serviço e as credenciais do utilizador verifica se existe o filho passado como paramentro
	 * com as credenciais
	 * @param inputFile File
	 * @param service String         
	 * @param serviceCredencial Credential
	 * @param child String
	 * @param childAttributes Map
	 * @return boolean
	 * @throws Exception e
	 * 
	 */
	public static boolean existsChildNode(File inputFile, String service, Credential serviceCredencial, String child,
			Map<String, String> childAttributes) throws Exception {
		return XMLclass.getChildNode(inputFile, service, serviceCredencial, child, childAttributes) != null;
	}

	/**
	 * Dado um determinado serviço e as credenciais do utilizador elimina o mesmo no documento com as
	 * credenciais
	 * @param inputFile File
	 * @param node String
	 * @param serviceCredencial Credential
	 * @return boolean
	 * @throws Exception e
	 */
	public static boolean deleteNode(File inputFile, String node, Credential serviceCredencial) throws Exception {
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(inputFile);
		doc.getDocumentElement().normalize();

		Node serviceNode = XMLclass.getNode(inputFile, node, serviceCredencial, doc);
		if (serviceNode == null)
			return false;

		serviceNode.getParentNode().removeChild(serviceNode);

		saveXMLfile(inputFile, doc);
		return true;
	}

	/**
	 * Dado um determinado serviço e as credenciais do utilizador elimina o filho no documento com as
	 * credenciais
	 * @param inputFile File
	 * @param service String        
	 * @param serviceCredencial Credential
	 * @param child String
	 * @param childAttributes Map
	 * @return boolean
	 * @throws Exception e
	 */
	public static boolean deleteChild(File inputFile, String service, Credential serviceCredencial, String child,
			Map<String, String> childAttributes) throws Exception {
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(inputFile);
		doc.getDocumentElement().normalize();

		Node childNode = XMLclass.getChildNode(inputFile, service, serviceCredencial, child, childAttributes);
		if (childNode == null)
			return false;

		childNode.getParentNode().removeChild(childNode);

		saveXMLfile(inputFile, doc);
		return true;
	}

	/**
	 * Dado um determinado serviço e as credenciais do utilizador retorna o node correspondente
	 * @param inputFile File
	 * @param service String
	 * @param serviceCredencial Credential
	 * @return Node
	 * @throws Exception e
	 */
	public static Node getNode(File inputFile, String service, Credential serviceCredencial) throws Exception {
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(inputFile);
		doc.getDocumentElement().normalize();

		return getNode(inputFile, service, serviceCredencial, doc);
	}

	/**
	 * Dado um determinado serviço e as credenciais do utilizador retorna o node correspondente
	 * @param inputFile File
	 * @param service String
	 * @param serviceCredencial Credential
	 * @return Node
	 * @throws Exception e
	 */
	private static Node getNode(File inputFile, String service, Credential serviceCredencial, Document doc)
			throws Exception {
		NodeList nodes = doc.getElementsByTagName(service);

		for (int i = 0; i < nodes.getLength(); i++) {
			Credential cred = new Credential(nodes.item(i).getAttributes());
			if (cred.equals(serviceCredencial))
				return nodes.item(i);
		}

		return null;
	}

	/**
	 * Dado um determinado serviço retorna o node que esta logado correspondente
	 * @param service String
	 * @param inputFile File
	 * @return Node
	 * @throws Exception e
	 */
	public static Node getLogin(File inputFile, String service) throws Exception {
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(inputFile);
		doc.getDocumentElement().normalize();

		NodeList nodes = doc.getElementsByTagName(service);

		for (int i = 0; i < nodes.getLength(); i++) {
			Credential cred = new Credential(nodes.item(i).getAttributes());
			if (cred.login.equals(Login))
				return nodes.item(i);
		}

		return null;
	}

	/**
	 * Dado um determinado serviço e as credenciais do utilizador define se este faz login ou logout
	 * @param inputFile File
	 * @param service String
	 * @param serviceCredential Credential
     * @param Login String
	 * @return Node
	 * @throws Exception e
	 */
	public static boolean setLogin(File inputFile, String service, Credential serviceCredential, String Login)
			throws Exception {
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(inputFile);
		doc.getDocumentElement().normalize();

		Node serviceNode = getNode(inputFile, service, serviceCredential, doc);
		((Element) serviceNode).setAttribute("Login", Login);

		saveXMLfile(inputFile, doc);
		return true;
	}

	/**
	 * Dado um determinado serviço retorna uma lista de todos os nos desse servico
	 * @param inputFile File
	 * @param service String
	 * @return Node
	 * @throws Exception e
	 */
	public static NodeList getNodeList(File inputFile, String service) throws Exception {
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(inputFile);
		doc.getDocumentElement().normalize();

		return doc.getElementsByTagName(service);
	}

	/**
	 * Dado um determinado serviço e as credenciais do utilizador, o nome do filho e os seus attributos retorna
	 * o mesmo
	 * @param inputFile File
	 * @param service String
	 * @param serviceCredencial Credential
	 * @param child String
	 * @param childAttributes Map
	 * @throws Exception e
	 * @return Node
	 */
	public static Node getChildNode(File inputFile, String service, Credential serviceCredencial, String child,
			Map<String, String> childAttributes) throws Exception {
		Node serviceNode = XMLclass.getNode(inputFile, service, serviceCredencial);
		if (serviceNode == null)
			return null;
		NodeList childList = serviceNode.getChildNodes();

		for (int i = 0; i < childList.getLength(); i++) {
			if (childList.item(i).getNodeName().equals(child)) {
				NamedNodeMap attributes = childList.item(i).getAttributes();
				boolean isChildNode = false;
				for (Map.Entry<String, String> attribute : childAttributes.entrySet()) {
					if (attributes.getNamedItem(attribute.getKey()) != null
							&& attributes.getNamedItem(attribute.getKey()).getNodeValue().equals(attribute.getValue()))
						isChildNode = true;
					else
						isChildNode = false;
				}
				if (isChildNode)
					return childList.item(i);
			}
		}

		return null;
	}

	/**
	 * Adiciona o novo serviço e respetivos atributos ao ficheiro
	 * @param inputFile File
	 * @param service String
	 * @param serviceCredencial Credential
	 * @return boolean
	 * @throws Exception e
	 */
	public static boolean addNode(File inputFile, String service, Credential serviceCredencial) throws Exception {
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(inputFile);
		doc.getDocumentElement().normalize();

		return addNode(inputFile, service, serviceCredencial, doc);
	}

	/**
	 * Adiciona o novo serviço e respetivos atributos ao ficheiro
	 * @param inputFile File
	 * @param service String
	 * @param serviceCredencial Credential
	 * @param doc Document
	 * @return boolean
	 * @throws Exception e
	 */
	private static boolean addNode(File inputFile, String service, Credential serviceCredencial, Document doc)
			throws Exception {
		Element serviceElement = doc.createElement(service);
		for (Map.Entry<String, String> attribute : serviceCredencial.getCredentiaAsMap().entrySet()) {
			serviceElement.setAttribute(attribute.getKey(), attribute.getValue());
		}

		Node n = doc.getDocumentElement();
		n.appendChild(serviceElement);

		saveXMLfile(inputFile, doc);
		return true;
	}

	/**
	 * Adiciona o novo serviço e um filho e os seus respetivos atributos ao ficheiro
	 * @param inputFile File
	 * @param service String
	 * @param serviceCredencial Credential
	 * @param Attributes Map
	 * @return boolean
	 * @throws Exception e
	 */
	public static boolean addNodeAndChild(File inputFile, String service, Credential serviceCredencial,
			Map<String, Map<String, String>> Attributes) throws Exception {
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(inputFile);
		doc.getDocumentElement().normalize();

		XMLclass.addNode(inputFile, service, serviceCredencial, doc);
		Node serviceNode = XMLclass.getNode(inputFile, service, serviceCredencial, doc);
		for (Map.Entry<String, Map<String, String>> child : Attributes.entrySet()) {
			Element childElement = doc.createElement(child.getKey());

			for (Map.Entry<String, String> attribute : child.getValue().entrySet()) {
				childElement.setAttribute(attribute.getKey(), attribute.getValue());
			}

			serviceNode.appendChild(childElement);
		}

		Node n = doc.getDocumentElement();
		n.appendChild(serviceNode);

		saveXMLfile(inputFile, doc);
		return true;
	}

	/**
	 * Adiciona um filho ao serviço e as credenciais do utilizador
	 * @param inputFile File
	 * @param service String
	 * @param serviceCredencial Credential
	 * @param child String
	 * @param childAttributes Map
	 * @return boolean
	 * @throws Exception e
	 */
	public static boolean addChild(File inputFile, String service, Credential serviceCredencial, String child,
			Map<String, String> childAttributes) throws Exception {
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(inputFile);
		doc.getDocumentElement().normalize();

		Node serviceNode = XMLclass.getNode(inputFile, service, serviceCredencial, doc);
		Element childElement = doc.createElement(child);

		for (Map.Entry<String, String> attribute : childAttributes.entrySet()) {
			childElement.setAttribute(attribute.getKey(), attribute.getValue());
		}
		serviceNode.appendChild(childElement);

		saveXMLfile(inputFile, doc);
		return true;
	}

	/**
	 * Guarda as alterações feitas ao ficheiro XML
	 * @param inputFile File
	 * @param doc Document
	 * @throws Exception e
	 */
	private static void saveXMLfile(File inputFile, Document doc) throws Exception {
		Transformer transformer;
		transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		StreamResult result = new StreamResult(new FileOutputStream(inputFile));
		DOMSource source = new DOMSource(doc);
		transformer.transform(source, result);
	}
}
