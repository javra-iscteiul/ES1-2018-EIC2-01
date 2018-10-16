package BDA;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.UserDataHandler;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

public class XMLclass {
		private static Document doc;
	public static void main(String[] args){
	      try {	
	         File inputFile = new File("config.xml");
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize();         
	         
	         System.out.println("\n----- Search the XML document with xpath queries -----");  
	         // Query 1 
	         System.out.println("Query 1: ");
	         XPathFactory xpathFactory = XPathFactory.newInstance();
	         XPath xpath = xpathFactory.newXPath();
	         XPathExpression expr = xpath.compile("/XML/Service/@*");
	         NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
	         for (int i = 0; i < nl.getLength(); i++) {
	             System.out.print(nl.item(i).getNodeName()  + ": ");
	             System.out.println(nl.item(i).getFirstChild().getNodeValue());
	         }
	         // Query 2
	         System.out.println("\nQuery 2: ");         
	         expr = xpath.compile("/XML/Paths/docPath");
	         String str = (String) expr.evaluate(doc, XPathConstants.STRING);
	         
	         System.out.println("docPath: " + str);
	       
	         
	         // Adding new element Service with attributes to the XML document (root node)
	         System.out.println("\n----- Adding new element <Service> with attributes to the XML document -----");

	         
	      } catch (Exception e) { e.printStackTrace(); }
	   }

	
	 public static void addElement(String service, String protocol, String tokenapi , String tokenuser ){
		 Element newElement1 = doc.createElement(service);
         System.out.println("ola");
         newElement1.setAttribute("Protocol", protocol);
         newElement1.setAttribute("TokenAPI", tokenapi);
         newElement1.setAttribute("TokenUSER", tokenuser);

         Node n = doc.getDocumentElement();
         n.appendChild(newElement1);
	 }
	 
	 public static void saveXML () throws TransformerFactoryConfigurationError, FileNotFoundException, TransformerException {
		// Save XML document
         System.out.println("\nSave XML document.");
         Transformer transformer = TransformerFactory.newInstance().newTransformer();
         transformer.setOutputProperty(OutputKeys.INDENT, "yes");
         StreamResult result = new StreamResult(new FileOutputStream("config.xml"));
         DOMSource source = new DOMSource(doc);
         transformer.transform(source, result);
		 
	 }
	
}
