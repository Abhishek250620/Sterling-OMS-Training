package com.tng.api;

import java.util.Properties;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sterlingcommerce.baseutil.SCXmlUtil;
import com.yantra.interop.japi.YIFCustomApi;
import com.yantra.yfs.japi.YFSEnvironment;

public class ACReceivedStoreApi implements YIFCustomApi{

	@Override
	public void setProperties(Properties arg0) throws Exception {
	}

	public Document acReceivedStore(YFSEnvironment arg0, Document doc) {
		Document outputDoc = SCXmlUtil.createDocument("Shipment");
		Element shipment = outputDoc.getDocumentElement();

		Element rootElement = doc.getDocumentElement();

		shipment.setAttribute("BaseDropStatus", "1400.400");
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
