package BDA;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
	public static void addElement(String service, String protocol, String username, String password, String consumerKey,
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
