package com.trainings.api;

import java.util.Properties;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sterlingcommerce.baseutil.SCXmlUtil;
import com.yantra.interop.japi.YIFCustomApi;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfs.japi.YFSEnvironment;

public class ResolveACRemorseHold implements YIFCustomApi {
	
	Properties mProperties = new Properties();

	/**
	 * Logger instance
	 */
	private static YFCLogCategory mLogger = YFCLogCategory.instance(TNGCustomAPI.class);
	

	/**
	 * boolean to check whether debug is enabled
	 */
	private static boolean mIsDebugEnabled;
	
	public static  Document createDocument(YFSEnvironment env, Document doc) {
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
	
	/**
	 * This method is overridden from interface.
	 * 
	 * @param properties
	 *            Properties set
	 */
	public void setProperties(Properties properties) {
		if (properties != null) {
			this.mProperties = properties;
		}
	}

	/**
	 * This method is overridden from interface.
	 * 
	 * @return Properties
	 */
	public Properties getProperties() {
		return this.mProperties;
	}

	/**
	 * Logs the debug message
	 * 
	 * @param msg
	 */
	private void debug(String aMsg) {
		if (ResolveACRemorseHold.mIsDebugEnabled) {
			ResolveACRemorseHold.mLogger.debug(aMsg);
		}
	}

}
