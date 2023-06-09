package filehandling;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CountChildElements {
	public static void main(String[] args) {
		try {
			// Load and parse the XML document
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse("D:\\InnovationTool\\Demo.xml");

			// Get the root element
			Element rootElement = document.getDocumentElement();
			System.out.println("Root is " + rootElement.getNodeName());

			// Get the attributes of the root element
			NamedNodeMap rootAttributes = rootElement.getAttributes();
			System.out.println("Number of Attributes of " + rootElement.getNodeName() + " is " + rootAttributes.getLength());
			//            int childElementCount = elementCount ;
			//            System.out.println("Number of childElement of " + rootElement.getNodeName() + " is " + childElementCount);

			// Get the child elements of the root element
			NodeList childNodes = rootElement.getChildNodes();

			// Initialize count variables
			int elementCount = 0;
			int textCount = 0;

			// Iterate over the child nodes
			for (int i = 0; i < childNodes.getLength(); i++) {
				Node childNode = childNodes.item(i);
				int childElementCount = elementCount ;
				System.out.println("Number of childElement of " + rootElement.getNodeName() + " is " + childElementCount);
				if (childNode.getNodeType() == Node.ELEMENT_NODE) {
					elementCount++;
					Element childElement = (Element) childNode;
					System.out.println("\t" + elementCount + " is " + childElement.getNodeName());

					// Get the attributes of the child element
					NamedNodeMap childAttributes = childElement.getAttributes();
					System.out.println("\tNumber of Attributes of " + childElement.getNodeName() + " is " + childAttributes.getLength());

					// Get the child elements of the child element
					NodeList grandChildNodes = childElement.getChildNodes();

					// Initialize count variables for grandchild elements
					int grandChildElementCount = 0;
					int grandChildTextCount = 0;

					// Iterate over the grandchild nodes
					for (int j = 0; j < grandChildNodes.getLength(); j++) {
						Node grandChildNode = grandChildNodes.item(j);
						if (grandChildNode.getNodeType() == Node.ELEMENT_NODE) {
							grandChildElementCount++;
							Element grandChildElement = (Element) grandChildNode;
							System.out.println("\t\t" + grandChildElementCount + " is " + grandChildElement.getNodeName());

							// Get the attributes of the grandchild element
							NamedNodeMap grandChildAttributes = grandChildElement.getAttributes();
							System.out.println("\t\tNumber of Attributes of " + grandChildElement.getNodeName() + " is " + grandChildAttributes.getLength());

							// Get the child elements of the grandchild element
							NodeList greatGrandChildNodes = grandChildElement.getChildNodes();

							// Count the text nodes
							for (int k = 0; k < greatGrandChildNodes.getLength(); k++) {
								Node greatGrandChildNode = greatGrandChildNodes.item(k);
								if (greatGrandChildNode.getNodeType() == Node.TEXT_NODE) {
									textCount++;
								}
							}
						}
					}
				}
			}

			// Calculate the number of child elements
			// int childElementCount = elementCount ;
			// System.out.println("Number of childElement of " + rootElement.getNodeName() + " is " + childElementCount);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

