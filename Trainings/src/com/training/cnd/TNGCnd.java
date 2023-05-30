package com.training.cnd;

import java.util.Map;

import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.yantra.ycp.japi.YCPDynamicConditionEx;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfs.japi.YFSEnvironment;

/**
 * This class is the custom condition
 * 
 * @author Sterling Commerce
 */
public class TNGCnd implements YCPDynamicConditionEx {

	/**
	 * Logger instance
	 */
	private static YFCLogCategory mLogger = YFCLogCategory.instance(TNGCnd.class);

	/**
	 * boolean to check whether debug is enabled
	 */
	private static boolean mIsDebugEnabled;

	static {
		TNGCnd.mIsDebugEnabled = TNGCnd.mLogger.isDebugEnabled();
	}

	/*
	 * Checks if any of the orderline is below shipped status
	 * com.yantra.ycp.japi.YCPDynamicConditionEx#evaluateCondition(com.yantra
	 * .yfs.japi.YFSEnvironment, java.lang.String,
	 * java.util.Map,org.w3c.dom.Document)
	 */

	public boolean evaluateCondition(YFSEnvironment aEnvironment, String cndName, Map aMapData, Document aInputDoc) {

		this.debug("Input to TNGCnd : " + YFCDocument.getDocumentFor(aInputDoc));
		try
		{
			String CodeDescription =  XPathAPI.eval(aInputDoc.getDocumentElement(),"/CommonCode/@CodeShortDescription").toString();
			if (CodeDescription.equals("Y"))
			{
				return true;
			}
			else
				return false;
		}
		catch(Exception e)
		{
			return false;
		}
	}

	//	public boolean evaluateCondition(YFSEnvironment aEnvironment, String cndName, Map aMapData, Document aInputDoc) {
	//
	//		this.debug("Input to TNGCnd : " + YFCDocument.getDocumentFor(aInputDoc));
	//
	//		// Get the root element of input doc
	//		Element eleOrder = aInputDoc.getDocumentElement();
	//
	//		// Iterate through all the OrderLines
	//		NodeList nlOrderLineList = eleOrder.getElementsByTagName("OrderLine");
	//		int iOrderLineCount = nlOrderLineList.getLength();
	//
	//		// Check condition for all the order lines
	//		for (int i = 0; i < iOrderLineCount; i++) {
	//			Element eleOrderLine = (Element) nlOrderLineList.item(i);
	//			String strMaxOrderLineStatus = eleOrderLine.getAttribute("MaxLineStatus");
	//			Double dbmaxOrderLineStatus = Double.valueOf(strMaxOrderLineStatus);
	//
	//			if (dbmaxOrderLineStatus >= Double.valueOf("1100") && dbmaxOrderLineStatus < Double.valueOf("3700")) {
	//				return true;
	//			}
	//		}
	//		return false;
	//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yantra.ycp.japi.YCPDynamicConditionEx#setProperties(java.util.Map)
	 */
	@Override
	public void setProperties(Map aMapData) {

	}

	/**
	 * Logs the debug message
	 * 
	 * @param msg
	 */
	private void debug(String aMsg) {
		if (TNGCnd.mIsDebugEnabled) {
			TNGCnd.mLogger.debug(aMsg);
		}
	}

}