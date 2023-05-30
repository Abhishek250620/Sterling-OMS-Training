package com.training.api;

import java.util.Enumeration;
import java.util.Properties;

import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;

import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.interop.japi.YIFCustomApi;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.core.YFSSystem;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;

public class TNGCustomAPI implements YIFCustomApi {

	Properties mProperties = new Properties();

	/**
	 * Logger instance
	 */
	private static YFCLogCategory mLogger = YFCLogCategory.instance(TNGCustomAPI.class);
	

	/**
	 * boolean to check whether debug is enabled
	 */
	private static boolean mIsDebugEnabled;

	// This is a sample customApi, the method doNothing sleeps for a few seconds
	// and returns the input xml document.
	public Document firstMethod(YFSEnvironment env, Document doc) throws Exception {
		try {
			double rnd = Math.random();
			this.debug("CustomAPI Sleeping for " + (long) (rnd * 1000) + " milli seconds");
			Thread.sleep((long) (rnd * 1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (mProperties != null) {
			Enumeration e = mProperties.propertyNames();
			while (e.hasMoreElements()) {
				String name = (String) e.nextElement();
				this.debug("Propery Name is: " + name + " Value is: " + mProperties.getProperty(name));
			}
		}
		return doc;
	}

	public Document firstAPICall(YFSEnvironment env, Document inDoc) throws Exception {
		this.debug("TNGCustomAPI: getCommonCodeValueInDoc : " + YFCDocument.getDocumentFor(inDoc));

		Document outDoc = invokeAPI(env, null, "getCommonCodeList", inDoc);

		return outDoc;
	}

	public Document firstServiceCall(YFSEnvironment env, Document inDoc) throws Exception {
		this.debug("TNGCustomAPI: service input : " + YFCDocument.getDocumentFor(inDoc));

		YIFApi api = YIFClientFactory.getInstance().getApi();
		// Invoking the service
		Document outDoc = api.executeFlow(env, "TNG_getItemDetailsService", inDoc);
		
		return outDoc;
	}
	
	public Document excpHandle(YFSEnvironment env, Document inDoc) throws Exception {
		this.debug("TNGCustomAPI: service input : " + YFCDocument.getDocumentFor(inDoc));

		YIFApi api = YIFClientFactory.getInstance().getApi();
		Document outDoc = null;
		
		try
		{
			// Invoking the service
			 outDoc = api.executeFlow(env, "TNG_getItemDetailsService", inDoc);
		}
		catch(Exception e)
		{
			
			YFCDocument itemDoc = YFCDocument.createDocument("Item");
			outDoc = itemDoc.getDocument();
		}
		return outDoc;
	}

	public Document xmlManiplulation(YFSEnvironment aEnvironment, Document inDoc) throws Exception {
		this.debug("TNGCustomAPI: xmlManiplulation input : " + YFCDocument.getDocumentFor(inDoc));

		YFCElement inEle = YFCDocument.getDocumentFor(inDoc).getDocumentElement();

		// String codeDesc = getCommonCodeValue(aEnvironment, "ORDER_TYPE", "Exchange");
		String codeType = inEle.getAttribute("CodeType");
		String codeValue = inEle.getAttribute("CodeValue");

		String codeDesc = getCommonCodeValue(aEnvironment,codeType ,
				codeValue);
		
		if(YFCCommon.isVoid(codeDesc))
		{
			 YFSException yfsException = new YFSException();
			 yfsException.setErrorCode("EXTN_10040");
			 yfsException.setErrorDescription("No CommonCode exists for the codeType=" + codeType + " and codeValue=" +codeValue);
			 throw yfsException;
		}

		String updateCommonCode = YFSSystem.getProperty("UPDATE.COMMON_CODE");
		if ("Y".equalsIgnoreCase(updateCommonCode)) {
			// manageCommonCode

		}

		return inDoc;
	}

	public String getCommonCodeValue(YFSEnvironment aEnvironment, String codeType, String codeValue) throws Exception {

		String codeDesc = null;
		YFCDocument getCommonCodeValueInDoc = YFCDocument.createDocument("CommonCode");

		YFCElement getCommonCodeValueInEle = getCommonCodeValueInDoc.getDocumentElement();
		getCommonCodeValueInEle.setAttribute("CodeType", codeType);
		getCommonCodeValueInEle.setAttribute("CodeValue", codeValue);
		getCommonCodeValueInEle.setAttribute("OrganizationCode", "Matrix");

		this.debug("TNGCustomAPI: getCommonCodeValueInDoc : " + getCommonCodeValueInDoc);

		Document outDoc = invokeAPI(aEnvironment, "global/template/api/getCommonCodeList_training.xml", "getCommonCodeList",
				getCommonCodeValueInDoc.getDocument());

		YFCDocument getCommonCodeValueOutDoc = YFCDocument.getDocumentFor(outDoc);
		YFCElement getCommonCodeValueOutEle = getCommonCodeValueOutDoc.getDocumentElement();
		YFCElement commonCodeEle = getCommonCodeValueOutEle.getChildElement("CommonCode");

		if (commonCodeEle != null) {
			codeDesc = commonCodeEle.getAttribute("CodeShortDescription");
		}

		codeDesc = XPathAPI.eval(outDoc.getDocumentElement(),
				"/CommonCodeList/CommonCode[@CodeValue='Exchange']/@CodeShortDescription").toString();

		return codeDesc;
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

	public Document setTxnObj(YFSEnvironment env, Document inDoc) throws Exception {
		env.setTxnObject("TNG_TXN_OBJ", inDoc);
		return inDoc;
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
		if (TNGCustomAPI.mIsDebugEnabled) {
			TNGCustomAPI.mLogger.debug(aMsg);
		}
	}

}
