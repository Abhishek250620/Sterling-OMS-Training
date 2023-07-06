package com.training.xmlmodifiaction;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlToXml {

	public static void main(String[] args) throws Exception {
		String filePath = "D:\\InnovationTool\\in.xml";
		Document xmlDocument = genrateXML(filePath);
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes"); // Set output property to indent the XML
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); // Set the number of spaces to use for indentation
		DOMSource source = new DOMSource(xmlDocument);
		StreamResult result = new StreamResult(System.out);
		transformer.transform(source, result);
	}

	public static Document genrateXML(String filePath) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document outputDoc = builder.newDocument();

		Document inputDoc = builder.parse(filePath);

		Element MultiApi = outputDoc.createElement("MultiApi");
		outputDoc.appendChild(MultiApi);

		NodeList list = inputDoc.getElementsByTagName("PropertyMetadata");

		for(int i=0;i<list.getLength();i++) {
			Node propertyMetadata = list.item(i);
			if (propertyMetadata.getNodeType() == Node.ELEMENT_NODE) {
				Element propertyMetadataElement = (Element) propertyMetadata;

				Element Api = outputDoc.createElement("API");
				Api.setAttribute("Name", "manageProperty");
				MultiApi.appendChild(Api);

				Element Input = outputDoc.createElement("Input");
				Api.appendChild(Input);

				Element Property = outputDoc.createElement("Property");
				Property.setAttribute("BasePropertyName", propertyMetadataElement.getAttribute("BasePropertyName") );
				Property.setAttribute("Category", propertyMetadataElement.getAttribute("Category") );
				Property.setAttribute("PropertyOverride", propertyMetadataElement.getAttribute("PropertyOverride") );
				Property.setAttribute("PropertyOverrideName", propertyMetadataElement.getAttribute("PropertyOverrideName") );
				Property.setAttribute("PropertyValue", propertyMetadataElement.getAttribute("PropertyValue") );
				Input.appendChild(Property);

				Element PropertyMetaData = outputDoc.createElement("PropertyMetaData");
				PropertyMetaData.setAttribute("BasePropertyName", propertyMetadataElement.getAttribute("BasePropertyName") );
				PropertyMetaData.setAttribute("Category", propertyMetadataElement.getAttribute("Category") );
				PropertyMetaData.setAttribute("Description", propertyMetadataElement.getAttribute("Description") );
				PropertyMetaData.setAttribute("JvmType", propertyMetadataElement.getAttribute("JvmType") );
				PropertyMetaData.setAttribute("Modifiable", propertyMetadataElement.getAttribute("Modifiable") );
				PropertyMetaData.setAttribute("ModifiableAtRuntime", propertyMetadataElement.getAttribute("ModifiableAtRuntime") );
				PropertyMetaData.setAttribute("PropertyType", propertyMetadataElement.getAttribute("PropertyType") );
				PropertyMetaData.setAttribute("Scope", propertyMetadataElement.getAttribute("Scope") );
				PropertyMetaData.setAttribute("ServerOverride", propertyMetadataElement.getAttribute("ServerOverride") );
				PropertyMetaData.setAttribute("UserOverride", propertyMetadataElement.getAttribute("UserOverride") );
				Property.appendChild(PropertyMetaData);
			}

		}
		return outputDoc;
	}
}

