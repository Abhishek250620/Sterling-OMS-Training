package com.trainings.api;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sterlingcommerce.baseutil.SCXmlUtil;

public class XMLModifiation { 
	public static void main(String[] args) throws Exception {
		File inputFile = new File("D:\\input.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(inputFile);

		document = createDocumentt(document);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes"); // Set output property to indent the XML
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); // Set the number of spaces to use for indentation
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(System.out);
        transformer.transform(source, result);
        

	}

	public static  Document createDocumentt(Document doc ) {
		Document outputDoc = SCXmlUtil.createDocument("Shipment");
		Element shipment = outputDoc.getDocumentElement();
		
		Element rootElement = doc.getDocumentElement();
		
		shipment.setAttribute("BaseDropStatus", "14000.400");
		shipment.setAttribute("SellerOrganizationCode", rootElement.getAttribute("SellerOrganizationCode"));
		shipment.setAttribute("ShipNode", rootElement.getAttribute("ShipNode"));
		shipment.setAttribute("ShipmentKey", rootElement.getAttribute("ShipmentKey"));
		shipment.setAttribute("ShipmentNo", rootElement.getAttribute("ShipmentNo"));
		shipment.setAttribute("TransactionId", "AC_Received_Shipment.0001.ex");
		
		Element shipmentStatusAudit = SCXmlUtil.createChild(shipment,"ShipmentStatusAudit");
		shipmentStatusAudit.setAttribute("ShipmentKey", rootElement.getAttribute("ShipmentKey"));
		
		return outputDoc;
	}
}