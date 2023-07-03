package com.trainings.api;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.sterlingcommerce.baseutil.SCXmlUtil;

public class XMLModifiation { 
	public static void main(String[] args) throws Exception {
		File inputFile = new File("D:\\InnovationTool\\in.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(inputFile);

		document = createDocument(document);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");

		FileOutputStream fos = new FileOutputStream("D:\\InnovationTool\\out.xml");
		transformer.transform(new DOMSource(document), new StreamResult(fos));
		System.out.println("XML file has been modified and saved.");

	}
	public static Document beforeCreateOrder( Document inputDoc) {
		NodeList orderLineList = inputDoc.getElementsByTagName("OrderLine");

		for(int i=0;i<orderLineList.getLength();i++) {
			Element orderLine = (Element) orderLineList.item(i);
			Element eleItem = (Element) orderLine.getElementsByTagName("Item").item(0);
			String itemid = eleItem.getAttribute("ItemID");

			if (itemid.isEmpty()) {
				System.out.println("Item is empty");
			}

			Element extn = inputDoc.createElement("Extn");
			extn.setAttribute("SubItemId", "V-"+itemid);
			orderLine.appendChild(extn);
		}

		Element rootElement = inputDoc.getDocumentElement();

		Random random = new Random();
		String randomNum = ""+random.nextInt(10000);

		Element extn = inputDoc.createElement("Extn");
		extn.setAttribute("CustomerNo",randomNum);
		rootElement.appendChild(extn);
		return inputDoc;
	}
	public static  Document createDocument( Document doc) {
		Document outputDoc = SCXmlUtil.createDocument("Shipment");
		Element shipmentElement = outputDoc.getDocumentElement();

		Element rootElement = doc.getDocumentElement();

		shipmentElement.setAttribute("EnterpriseCode", rootElement.getAttribute("EnterpriseCode"));
		shipmentElement.setAttribute("Action", "Create-Modify");
		
		Element extnIndoc = SCXmlUtil.getChildElement(rootElement, "Extn");
		Element extn = SCXmlUtil.createChild(shipmentElement, "Extn");
		extn.setAttribute("CustomerNo", extnIndoc.getAttribute("CustomerNo"));
		extn.setAttribute("HoldStatus", extnIndoc.getAttribute("HoldStatus"));

//		Element eleChangeOrdRoot = outputDoc.getDocumentElement();
		Element shipmentLines = SCXmlUtil.createChild(shipmentElement, "ShipmentLines");

		Element orderLinesElement = SCXmlUtil.getChildElement(rootElement, "OrderLines");
		NodeList orderLines = orderLinesElement.getElementsByTagName("OrderLine");

		for(int i=0;i<orderLines.getLength();i++) {

			Element shipmentLine = SCXmlUtil.createChild(shipmentLines, "ShipmentLine");
			Element orderLine = (Element) orderLines.item(i);

			shipmentLine.setAttribute("OrderNo", rootElement.getAttribute("OrderNo"));
			shipmentLine.setAttribute("DocumentType", rootElement.getAttribute("DocumentType"));
			shipmentLine.setAttribute("ProductClass", "Good");
			shipmentLine.setAttribute("PrimeLineNo", orderLine.getAttribute("PrimeLineNo"));
			shipmentLine.setAttribute("Quantity", orderLine.getAttribute("OrderedQty"));
			shipmentLine.setAttribute("ReleaseNo", orderLine.getAttribute("PrimeLineNo"));
			shipmentLine.setAttribute("SubLineNo", orderLine.getAttribute("SubLineNo"));
		}
		return outputDoc;
	}
}

//	<Shipment Action="Create-Modify" EnterpriseCode="Matrix">
//	<ShipmentLins>
//	<ShipmentLine DocumentType="0001" OrderNo="Y100000795" PrimeLineNo="2" ProductClass="Good"
//	Quantity="1" SubLineNo="1" ReleaseNo="1">
//	</ShipmentLine>
//	</ShipmentLines>
//	<Extn CustomerNo="12345" HoldStatus="Resolved" />
//	</Shipment>
