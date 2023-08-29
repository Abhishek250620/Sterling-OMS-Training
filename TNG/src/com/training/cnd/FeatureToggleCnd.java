package com.training.cnd;

import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.yantra.ycp.japi.YCPDynamicConditionEx;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfs.japi.YFSEnvironment;

public class FeatureToggleCnd implements YCPDynamicConditionEx {


	/**
	 * Logger instance
	 */
	private static YFCLogCategory mLogger = YFCLogCategory.instance(FeatureToggleCnd.class);

	/**
	 * boolean to check whether debug is enabled
	 */
	private static boolean mIsDebugEnabled;

	static {
		FeatureToggleCnd.mIsDebugEnabled = FeatureToggleCnd.mLogger.isDebugEnabled();
	}

	/*
	 * Checks if Code Short Description is Y
	 * com.yantra.ycp.japi.YCPDynamicConditionEx#evaluateCondition(com.yantra
	 * .yfs.japi.YFSEnvironment, java.lang.String,
	 * java.util.Map,org.w3c.dom.Document)
	 */

	public boolean evaluateCondition(YFSEnvironment aEnvironment, String cndName, Map aMapData, Document aInputDoc) {

		this.debug("Input to FeatureToggleCnd : " + YFCDocument.getDocumentFor(aInputDoc));

		// Get the root element of input doc
		YFCDocument getCommonCodeValueOutDoc = YFCDocument.getDocumentFor(aInputDoc);
		YFCElement getCommonCodeValueOutEle = getCommonCodeValueOutDoc.getDocumentElement();
		String codeType = getCommonCodeValueOutEle.getAttribute("CodeType");
		String codeName = getCommonCodeValueOutEle.getAttribute("CodeName");
		String codeShortDescription = getCommonCodeValueOutEle.getAttribute("CodeShortDescription");
		System.out.println("codeType: " +codeType);
		System.out.println("CodeName: " +codeName);
		System.out.println("codeShortDescription" + codeShortDescription);
		//Checking the Condition:
		if (codeShortDescription.equals("Y")) {
			return true;
		}	
		return false;
	}

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
		if (FeatureToggleCnd.mIsDebugEnabled) {
			FeatureToggleCnd.mLogger.debug(aMsg);
		}
	}

}