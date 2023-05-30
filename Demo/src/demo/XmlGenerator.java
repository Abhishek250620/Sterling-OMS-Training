
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XmlGenerator {
	
    public static void main(String[] args) throws Exception {
    		 Document xmlDocument = XmlGenerator.generateXml();
             TransformerFactory transformerFactory = TransformerFactory.newInstance();
             Transformer transformer = transformerFactory.newTransformer();
             transformer.setOutputProperty(OutputKeys.INDENT, "yes"); // Set output property to indent the XML
             transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); // Set the number of spaces to use for indentation
             DOMSource source = new DOMSource(xmlDocument);
             StreamResult result = new StreamResult(System.out);
             transformer.transform(source, result);
    }
    
    public static Document generateXml() throws Exception {

    	  // Create the XML document
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        // Create the root element
        Element root = doc.createElement("Order");
        root.setAttribute("DocumentType", "0001");
        root.setAttribute("EnterpriseCode", "Matrix");
        root.setAttribute("ShipNode", "Matrix_WH1");
        root.setAttribute("OrderNo", "");
        doc.appendChild(root);

        // Create the OrderLines element and its child elements
        Element orderLines = doc.createElement("OrderLines");
        Element orderLine = doc.createElement("OrderLine");
        orderLine.setAttribute("OrderedQty", "5");
        Element item = doc.createElement("Item");
        item.setAttribute("ItemID", "100013");
        item.setAttribute("UnitCost", "10.0");
        item.setAttribute("UnitOfMeasure", "");
        orderLine.appendChild(item);
        orderLines.appendChild(orderLine);
        root.appendChild(orderLines);

        // Create the PersonInfoShipTo element and its attributes
        Element personInfoShipTo = doc.createElement("PersonInfoShipTo");
        personInfoShipTo.setAttribute("AddressLine1", "234 Copley Place");
        personInfoShipTo.setAttribute("City", "Boston");
        personInfoShipTo.setAttribute("Country", "US");
        personInfoShipTo.setAttribute("DayPhone", "");
        personInfoShipTo.setAttribute("EMailID", "");
        personInfoShipTo.setAttribute("FirstName", "Abhishek");
        personInfoShipTo.setAttribute("LastName", "MR");
        personInfoShipTo.setAttribute("MobilePhone", "");
        personInfoShipTo.setAttribute("State", "MA");
        personInfoShipTo.setAttribute("ZipCode", "02116");
        root.appendChild(personInfoShipTo);

        // Create the PersonInfoBillTo element and its attributes
        Element personInfoBillTo = doc.createElement("PersonInfoBillTo");
        personInfoBillTo.setAttribute("AddressLine1", "234 Copley Place");
        personInfoBillTo.setAttribute("City", "Boston");
        personInfoBillTo.setAttribute("Country", "US");
        personInfoBillTo.setAttribute("DayPhone", "");
        personInfoBillTo.setAttribute("EMailID", "");
        personInfoBillTo.setAttribute("FirstName", "Abhishek");
        personInfoBillTo.setAttribute("LastName", "MR");
        personInfoBillTo.setAttribute("MobilePhone", "");
        personInfoBillTo.setAttribute("State", "MA");
        personInfoBillTo.setAttribute("ZipCode", "02116");
        root.appendChild(personInfoBillTo);

        return doc;
    }
}
