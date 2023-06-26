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
import com.yantra.yfs.japi.YFSEnvironment;


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
		Element ele = doc.getDocumentElement();
		String orderHeaderKey = ele.getAttribute("OrderHeaderKey");
		Document docChangeOrdInput = SCXmlUtil.createDocument("Order");
		Element eleChangeOrdRoot = docChangeOrdInput.getDocumentElement();
		Element eleOrdLineHoldTypes = SCXmlUtil.createChild(eleChangeOrdRoot, "OrderHoldTypes");
		Element eleOrdLineHoldType = SCXmlUtil.createChild(eleOrdLineHoldTypes, "OrderHoldType");
		eleChangeOrdRoot.setAttribute("OrderHeaderKey", orderHeaderKey);
		eleChangeOrdRoot.setAttribute("Override", "Y");
		eleOrdLineHoldType.setAttribute("HoldType", "AC_REMORSE_HOLD");
		eleOrdLineHoldType.setAttribute("Status", "1300");
		Element extn = SCXmlUtil.createChild(eleChangeOrdRoot, "Extn");
		extn.setAttribute("HoldStatus", "Resolved");
		return docChangeOrdInput;
	}
}
