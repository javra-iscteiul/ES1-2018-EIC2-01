package BDA;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import twitter4j.TwitterException;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;


/**
 * Date: Oct 22 2018
 * @author ES1-2018-EIC2-01
 * @version 1.0
 * Classe responsavel por guardas ass credenciais no ficheiro config.xml
 */
public class XMLclass {
	
	/**
	 * Atributo do tipo File responsavel por definir o destino das credenciais 
	 */
	private static File inputFile = new File("config.xml");
	
	/**
	 * Atributo do tipo DocumentBuilderFactory responsavel por construir um documento a partir do ficheiro dado 
	 */
	private static DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	
	/**
 	 * Dado um determinado serviço verifica se o mesmo já existe no documento com as credenciais 
	 * @param service String
	 * @return boolean
	 */
	public static boolean existsElement(String service) {
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();

			return doc.getElementsByTagName(service).getLength() > 0;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
 	 * Dado um determinado serviço e atributos com o nome e valor verifica se o mesmo já existe no documento com as credenciais 
	 * @param service String
	 * @param Attributes Map<String, String> (Nome do atributo, Valor do atributo)
	 * @return boolean
	 */
	public static boolean verifyElementAttributesUnchanged(String service, Map<String, String> Attributes) {
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			
			NodeList nodeList = doc.getElementsByTagName(service);
			if(nodeList.getLength() == 0)
				return false;
			
			NamedNodeMap nodeAttributes = nodeList.item(0).getAttributes();
			for(Map.Entry<String, String> Attribute : Attributes.entrySet()){
				if(nodeAttributes.getNamedItem(Attribute.getKey()) == null)
					return false;
				if(!nodeAttributes.getNamedItem(Attribute.getKey()).getNodeValue().equals(Attribute.getValue()))
					return false;
				
				System.out.println(nodeAttributes.getNamedItem(Attribute.getKey()).getNodeValue() + "  " + Attribute.getValue());
			}
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
 	 * Dado um determinado serviço elimina o mesmo no documento com as credenciais 
	 * @param service String
	 * @return boolean
	 */
	public static boolean deleteElement(String service) {
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			
			NodeList nodeList = doc.getElementsByTagName(service);
			if(nodeList.getLength() == 0)
				return false;			
			
			Element element = (Element)nodeList.item(0);			
			element.getParentNode().removeChild(element);
			
			System.out.println("\nSave XML document.");
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			StreamResult result = new StreamResult(new FileOutputStream(inputFile));
			DOMSource source = new DOMSource(doc);
			transformer.transform(source, result);
			
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	
	/**
 	 * Dado um determinado serviço retorna o node correspondente
	 * @param service String
	 * @return Node
	 */
	public static Node getElement(String service) {
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();

			return doc.getElementsByTagName(service).item(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	
	/**
 	 * Adiciona o novo serviço e respetivos atributos ao ficheiro config.xml
	 * @param service String
	 * @param protocol String 	 
	 * @param username String 
	 * @param password String
	 * @param consumerKey String 
	 * @param consumerSecret String
	 * @param token String 
	 * @param tokensecret String
	 */
	public static boolean addElement(String service, String protocol, String username, String password, String consumerKey,
			String consumerSecret, String token, String tokensecret) {
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();

			System.out.println("\n----- Search the XML document with xpath queries -----");
			// Query 1
			System.out.println("Query 1: ");
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			XPathExpression expr = xpath.compile("/XML/Service/@*");
			NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			for (int i = 0; i < nl.getLength(); i++) {
				System.out.print(nl.item(i).getNodeName() + ": ");
				System.out.println(nl.item(i).getFirstChild().getNodeValue());
			}
			// Query 2
			System.out.println("\nQuery 2: ");
			expr = xpath.compile("/XML/Paths/docPath");
			String str = (String) expr.evaluate(doc, XPathConstants.STRING);

			System.out.println("docPath: " + str);

			// Adding new element Service with attributes to the XML document
			// (root node)
			System.out.println("\n----- Adding new element <Service> with attributes to the XML document -----");

			Element newElement1 = doc.createElement(service);
			newElement1.setAttribute("Protocol", protocol);
			newElement1.setAttribute("UserName", username);
			newElement1.setAttribute("Password", password);
			newElement1.setAttribute("ConsumerKey", consumerKey);
			newElement1.setAttribute("ConsumerSecret", consumerSecret);
			newElement1.setAttribute("AccessToken", token);
			newElement1.setAttribute("AccessTokenSecret", tokensecret);

			Node n = doc.getDocumentElement();
			n.appendChild(newElement1);
			System.out.println("\nSave XML document.");
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			StreamResult result = new StreamResult(new FileOutputStream("config.xml"));
			DOMSource source = new DOMSource(doc);
			transformer.transform(source, result);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
