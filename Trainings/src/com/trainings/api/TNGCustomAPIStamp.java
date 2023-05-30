package com.trainings.api;

import java.io.*;
import java.util.*;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.interop.japi.YIFCustomApi;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.dom.YFCNode;
import com.yantra.yfc.log.YFCLogCategory;

public class TNGCustomAPIStamp implements YIFCustomApi {
	public TNGCustomAPIStamp(){}
    private Properties _properties = null;
    
	/**
	 * Logger instance
	 */
	private static YFCLogCategory mLogger = YFCLogCategory.instance(TNGCustomAPI.class);
	

	/**
	 * boolean to check whether debug is enabled
	 */
	private static boolean mIsDebugEnabled;
	
 
	public Document createOrderAPICall(YFSEnvironment env, Document inDoc) throws Exception {
		this.debug("TNGCustomAPIStamp: createOrderInDoc : " + YFCDocument.getDocumentFor(inDoc));
		String pick= "PICK";
		String deliveryMethod = null;
		Document outDoc = invokeAPI(env, null, "createOrder", inDoc);
		toString(outDoc," createOrder API OUTPUT :" );
		YFCDocument getOrderValueInDoc = null;
		Document orderLineDoc = invokeAPI(env, "global/template/api/stampPersonInfoShipTo.xml", "getOrderDetails", outDoc);
		toString(orderLineDoc," getOrderDetails API Output :" );	
		YFCDocument getOrderLineValueOutDoc = YFCDocument.getDocumentFor(orderLineDoc);
		YFCElement getOrderLineValueOutEle = getOrderLineValueOutDoc.getDocumentElement();
		YFCElement orderLinesEle = getOrderLineValueOutEle.getChildElement("OrderLines");
		YFCElement orderLineEle = orderLinesEle.getChildElement("OrderLine");
	
		if (orderLineEle != null) {
			deliveryMethod = orderLineEle.getAttribute("DeliveryMethod");
	
		    if(deliveryMethod.equalsIgnoreCase(pick)) {
			   YFCElement personInfoShipToEle = getOrderLineValueOutEle.getChildElement("PersonInfoShipTo");
			   getOrderValueInDoc = YFCDocument.createDocument("Order");
			   YFCElement getOrderValueInEle = getOrderValueInDoc.getDocumentElement();
			   getOrderValueInEle.setAttribute("DocumentType", getOrderLineValueOutEle.getAttribute("DocumentType"));
			   getOrderValueInEle.setAttribute("EnterpriseCode", getOrderLineValueOutEle.getAttribute("EnterpriseCode"));
			   getOrderValueInEle.setAttribute("OrderDate", getOrderLineValueOutEle.getAttribute("OrderDate"));
			   getOrderValueInEle.setAttribute("OrderHeaderKey",getOrderLineValueOutEle.getAttribute("OrderHeaderKey"));
			   getOrderValueInEle.setAttribute("OrderNo", getOrderLineValueOutEle.getAttribute("OrderNo"));
		   
			   YFCElement getOrderLinesValueInEle = getOrderValueInDoc.createElement("OrderLines");
			   YFCElement getOrderLineValueInEle = getOrderValueInDoc.createElement("OrderLine");
			   getOrderLineValueInEle.setAttribute("DeliveryMethod", orderLineEle.getAttribute("DeliveryMethod"));
			   getOrderLineValueInEle.setAttribute("OrderHeaderKey", orderLineEle.getAttribute("OrderHeaderKey"));
			   getOrderLineValueInEle.setAttribute("OrderLineKey", orderLineEle.getAttribute("OrderLineKey"));
			   getOrderLineValueInEle.setAttribute("OrderedQty",orderLineEle.getAttribute("OrderedQty"));
			   getOrderLineValueInEle.setAttribute("ShipNode", orderLineEle.getAttribute("ShipNode"));
			   getOrderLineValueInEle.setAttribute("Status", orderLineEle.getAttribute("Status"));

			   YFCElement getpersonInfoShipValueInEle = getOrderValueInDoc.createElement("PersonInfoShipTo");
			   getpersonInfoShipValueInEle.setAttribute("AddressLine1", personInfoShipToEle.getAttribute("AddressLine1"));
			   getpersonInfoShipValueInEle.setAttribute("City", personInfoShipToEle.getAttribute("City"));
			   getpersonInfoShipValueInEle.setAttribute("FirstName", personInfoShipToEle.getAttribute("FirstName"));
			   getpersonInfoShipValueInEle.setAttribute("LastName",personInfoShipToEle.getAttribute("LastName"));
			   getpersonInfoShipValueInEle.setAttribute("PersonInfoKey", personInfoShipToEle.getAttribute("PersonInfoKey"));
			   getpersonInfoShipValueInEle.setAttribute("State", personInfoShipToEle.getAttribute("State"));
			   getpersonInfoShipValueInEle.setAttribute("ZipCode", personInfoShipToEle.getAttribute("ZipCode"));
			   
			   getOrderLinesValueInEle.appendChild(getOrderLineValueInEle);
			   getOrderValueInEle.appendChild(getOrderLinesValueInEle);
			   getOrderValueInEle.appendChild(getpersonInfoShipValueInEle);
					 		   
		    }
		}
		toString(getOrderValueInDoc.getDocument(),"createOrderAPICall Output******:");
		return getOrderValueInDoc.getDocument();
	}
	
	private static void toString(Document newDoc , String Type) throws Exception{
	    DOMSource domSource = new DOMSource(newDoc);
	    Transformer transformer = TransformerFactory.newInstance().newTransformer();
	    StringWriter sw = new StringWriter();
	    StreamResult sr = new StreamResult(sw);
	    transformer.transform(domSource, sr);
	    System.out.println("TNGCustomAPIStamp: service " + Type + sw.toString());  
	  }
	
	public static Document invokeAPI(YFSEnvironment env, String templateName, String apiName, Document inDoc)
			throws Exception {
		YIFApi api = YIFClientFactory.getInstance().getApi();
		if (templateName != null)
			env.setApiTemplate(apiName, templateName);
		Document outDoc = api.invoke(env, apiName, inDoc);
		env.clearApiTemplate(apiName);
		return outDoc;
	}
	
    public void setProperties(Properties prop) throws Exception {
        _properties = prop;
    }
    
    /**
	 * Logs the debug message
	 * 
	 * @param msg
	 */
	private void debug(String aMsg) {
		if (TNGCustomAPIStamp.mIsDebugEnabled) {
			TNGCustomAPIStamp.mLogger.debug(aMsg);
		}
	}
}
