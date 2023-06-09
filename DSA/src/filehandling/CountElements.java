package filehandling;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.File;
import java.io.IOException;

public class CountElements {
	public static void main(String[] args) {
		String filePath = "D:\\InnovationTool\\Demo.xml";
		File xmlFile = new File(filePath);

		if (xmlFile.exists()) {
			try {
				// Create a DocumentBuilderFactory
				DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

				// Create a DocumentBuilder
				DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

				// Parse the XML file
				Document document = documentBuilder.parse(xmlFile);

				// Get the root element
				Element rootElement = document.getDocumentElement();

				// Get the number of elements
				NodeList elements = rootElement.getElementsByTagName("*");
				int numElements = elements.getLength();
				System.out.println("Number of elements underroot element: " + numElements);
				
				for(int i=0;i<numElements;i++) {
					Element element = (Element)elements.item(i);
					System.out.println(element.getTagName()+": "+element.getAttributes().getLength());
				}
			
			} catch (ParserConfigurationException | SAXException | IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("XML file does not exist at the specified path: " + filePath);
		}
	}
}

