package BDA;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
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
 * @version 1.0 Classe responsavel por guardas ass credenciais no ficheiro
 *          config.xml
 */
public class XMLclass {

	/**
	 * Atributo do tipo File responsavel por definir o destino das credenciais
	 */
	public static String facebookService = "facebook";
	
	/**
	 * Atributo do tipo File responsavel por definir o destino das credenciais
	 */
	public static String twitterService = "twitter";
	
	/**
	 * Atributo do tipo File responsavel por definir o destino das credenciais
	 */
	public static String emailService = "email";
	
	/**
	 * Atributo do tipo File responsavel por definir o destino das credenciais
	 */
	public static File configFile = new File("config.xml");

	/**
	 * Atributo do tipo File responsavel por guardar os posts para consultar em
	 * modo offline
	 */
	public static File storedDataFile = new File("storedData.xml");

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
	 * Dado um determinado serviço verifica se o mesmo já existe no documento
	 * com as credenciais
	 * 
	 * @param service
	 *            String
	 * @return boolean
	 */
	public static boolean existsNode(File inputFile, String service, Credential serviceCredencial) {
		try {
			return XMLclass.getNode(inputFile, service, serviceCredencial) != null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Dado um determinado serviço verifica se o mesmo já existe no documento
	 * com as credenciais
	 * 
	 * @param service
	 *            String
	 * @return boolean
	 */
	public static boolean existsChildNode(File inputFile, String service, Credential serviceCredencial, String child,
			Map<String, String> childAttributes) {
		try {
			return XMLclass.getChildNode(inputFile, service, serviceCredencial, child, childAttributes) != null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Dado um determinado serviço e atributos com o nome e valor verifica se o
	 * mesmo já existe no documento com as credenciais
	 * 
	 * @param service
	 *            String
	 * @param Attributes
	 *            Map<String, String> (Nome do atributo, Valor do atributo)
	 * @return boolean
	 */
//	public static boolean verifyNodeAttributesUnchanged(File inputFile, String service, Credential serviceCredencial) {
//		try {
//			Node serviceNode = XMLclass.getNode(inputFile, service, serviceCredencial);
//			if (serviceNode == null)
//				return false;
//
//			NamedNodeMap nodeAttributes = serviceNode.getAttributes();
//			for (Map.Entry<String, String> attribute : attributes.entrySet()) {
//				if (nodeAttributes.getNamedItem(attribute.getKey()) == null)
//					return false;
//				if (!nodeAttributes.getNamedItem(attribute.getKey()).getNodeValue().equals(attribute.getValue()))
//					return false;
//			}
//			return true;
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return false;
//		}
//	}

	/**
	 * Dado um determinado serviço elimina o mesmo no documento com as
	 * credenciais
	 * 
	 * @param service
	 *            String
	 * @return boolean
	 */
	public static boolean deleteNode(File inputFile, String node, Credential serviceCredencial) {
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();

			Node serviceNode = XMLclass.getNode(inputFile, node, serviceCredencial, doc);
			if (serviceNode == null)
				return false;

			serviceNode.getParentNode().removeChild(serviceNode);

			saveXMLfile(inputFile, doc);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Dado um determinado serviço elimina o mesmo no documento com as
	 * credenciais
	 * 
	 * @param service
	 *            String
	 * @param child
	 *            String
	 * @return boolean
	 */
	public static boolean deleteChild(File inputFile, String service, Credential serviceCredencial, String child, Map<String, String> childAttributes) {
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();

			Node childNode = XMLclass.getChildNode(inputFile, service, serviceCredencial, child, childAttributes);
			if (childNode == null)
				return false;

			childNode.getParentNode().removeChild(childNode);

			saveXMLfile(inputFile, doc);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Dado um determinado serviço retorna o node correspondente
	 * 
	 * @param service
	 *            String
	 * @return Node
	 */
	public static Node getNode(File inputFile, String service, Credential serviceCredencial) {
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();

			return getNode(inputFile, service, serviceCredencial, doc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Dado um determinado serviço retorna o node correspondente
	 * 
	 * @param service
	 *            String
	 * @return Node
	 */
	private static Node getNode(File inputFile, String service, Credential serviceCredencial, Document doc) {
		try {
			NodeList nodes = doc.getElementsByTagName(service);

			for (int i = 0; i < nodes.getLength(); i++) {
				Credential cred = new Credential(nodes.item(i).getAttributes());
				if(cred.equals(serviceCredencial))
					return nodes.item(i);
			}

			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Dado um determinado serviço retorna o node correspondente
	 * 
	 * @param service
	 *            String
	 * @return Node
	 */
	public static NodeList getNodeList(File inputFile, String service) {
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();

			return doc.getElementsByTagName(service);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Dado um determinado serviço, o nome do filho e os seus attributos retorna
	 * o mesmo
	 * 
	 * @param service
	 *            String
	 * @return Node
	 */
	public static Node getChildNode(File inputFile, String service, Credential serviceCredencial, String child, Map<String, String> childAttributes) {
		try {

			Node serviceNode = XMLclass.getNode(inputFile, service, serviceCredencial);
			if (serviceNode == null)
				return null;
			NodeList childList = serviceNode.getChildNodes();

			for (int i = 0; i < childList.getLength(); i++) {
				if (childList.item(i).getNodeName().equals(child)) {
					NamedNodeMap attributes = childList.item(i).getAttributes();
					boolean isChildNode = false;
					for (Map.Entry<String, String> attribute : childAttributes.entrySet()) {
						if (attributes.getNamedItem(attribute.getKey()) != null && attributes
								.getNamedItem(attribute.getKey()).getNodeValue().equals(attribute.getValue()))
							isChildNode = true;
						else
							isChildNode = false;
					}
					if (isChildNode)
						return childList.item(i);
				}
			}

			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Adiciona o novo serviço e respetivos atributos ao ficheiro config.xml
	 * 
	 * @param service
	 *            String
	 * @param protocol
	 *            String
	 * @param username
	 *            String
	 * @param password
	 *            String
	 * @param consumerKey
	 *            String
	 * @param consumerSecret
	 *            String
	 * @param token
	 *            String
	 * @param tokensecret
	 *            String
	 */
	public static boolean addNode(File inputFile, String service, Credential serviceCredencial) {
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			
			return addNode(inputFile, service, serviceCredencial);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean addNode(File inputFile, String service, Credential serviceCredencial, Document doc) {
		try {
			Element serviceElement = doc.createElement(service);
			for (Map.Entry<String, String> attribute : serviceCredencial.getCredentiaAsMap().entrySet()) {
				serviceElement.setAttribute(attribute.getKey(), attribute.getValue());
			}

			Node n = doc.getDocumentElement();
			n.appendChild(serviceElement);

			saveXMLfile(inputFile, doc);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean addNodeAndChild(File inputFile, String service, Credential serviceCredencial, Map<String, Map<String, String>> Attributes) {
		try {
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
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean addChild(File inputFile, String service, Credential serviceCredencial, String child, Map<String, String> childAttributes) {
		try {
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
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private static void saveXMLfile(File inputFile, Document doc) {
		Transformer transformer;
		try {
			transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			StreamResult result = new StreamResult(new FileOutputStream(inputFile));
			DOMSource source = new DOMSource(doc);
			transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
