package com.trainings.api;

import java.util.Properties;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.sterlingcommerce.baseutil.SCXmlUtil;
import com.yantra.interop.japi.YIFCustomApi;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSUserExitException;

public class ConfirmShipmentApi implements YIFCustomApi {

	@Override
	public void setProperties(Properties arg0) throws Exception {
	}

	public Document beforeCreateShipment(YFSEnvironment arg0, Document doc) throws YFSUserExitException {
		Document outputDoc = SCXmlUtil.createDocument("Shipment");
		Element shipmentElement = outputDoc.getDocumentElement();

		Element rootElement = doc.getDocumentElement();

		shipmentElement.setAttribute("EnterpriseCode", rootElement.getAttribute("EnterpriseCode"));
		shipmentElement.setAttribute("Action", "Create-Modify");

		Element extnIndoc = SCXmlUtil.getChildElement(rootElement, "Extn");
		Element extn = SCXmlUtil.createChild(shipmentElement, "Extn");
		extn.setAttribute("CustomerNo", extnIndoc.getAttribute("CustomerNo"));
		extn.setAttribute("HoldStatus", extnIndoc.getAttribute("HoldStatus"));

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
		System.out.println("Output of Execute Job:" + SCXmlUtil.getString(outputDoc));
		return outputDoc;
	}

}
