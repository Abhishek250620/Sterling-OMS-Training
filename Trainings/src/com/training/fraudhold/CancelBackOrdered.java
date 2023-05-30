package com.training.fraudhold;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sterlingcommerce.baseutil.SCXmlUtil;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.interop.japi.YIFCustomApi;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSConnectionHolder;
import com.yantra.yfs.japi.YFSEnvironment;

public class CancelBackOrdered implements YIFCustomApi {

	protected static YIFApi api = null;
	String codeValue = null;
	String backorderedCount = null;
	
	public Document cancelBackorderedAPICall(YFSEnvironment env, Document inDoc) throws Exception {

		System.out.println("Input of cancelBackorderedAPICall:" + SCXmlUtil.getString(inDoc));
		YFCDocument getOrderValueInDoc = YFCDocument.getDocumentFor(inDoc);
		YFCElement getOrderValueInEle = getOrderValueInDoc.getDocumentElement();
		String OrderNo = getOrderValueInEle.getAttribute("OrderNo");
		String strOHKey = getOrderValueInEle.getAttribute("OrderHeaderKey");
		System.out.println("OHKey from input:"+ strOHKey);
		System.out.println("OrderNo from input:"+ OrderNo);
		if (isBackOrderedThrice(env, strOHKey)) {
					Document inDocToExecuteJob = CreateDocument(strOHKey);
					executeJob(env,inDocToExecuteJob);
			}
			else {
					Document inDocToBackorderedExecuteJob = CreateDocumentForBackorderedCount(strOHKey , backorderedCount);
					incrementBackorderedCount(env,inDocToBackorderedExecuteJob);
			}
								
		return inDoc;	
	}
	
	
	private boolean isBackOrderedThrice(YFSEnvironment env , String strOHKey) throws Exception {

		String sql = "select code_value from yfs_common_code where code_type='Backordered' and common_code_key='2023042406185645772'";
		ResultSet rsRecords = fetchRecords(env, sql);
		if (!YFCCommon.isVoid(rsRecords)) {
			while (rsRecords.next()) {
				codeValue = rsRecords.getString(1).trim();
				System.out.println("codeValue:"+ codeValue);			
			}
		}
		
		String countsql = "select extn_backordered_count from yfs_order_header where ORDER_HEADER_KEY=" + "'" + strOHKey + "'";
		ResultSet rsCountRecords = fetchRecords(env, countsql);
		if (!YFCCommon.isVoid(rsCountRecords)) {
			while (rsCountRecords.next()) {
				if (rsCountRecords.getString(1) != null) {
					backorderedCount = rsCountRecords.getString(1).trim();
					System.out.println("backorderedCount in DB:"+ backorderedCount);
				}
			}
		}
		if(backorderedCount != null) {
			if (backorderedCount.equalsIgnoreCase(codeValue)) {
				return true;
			}
			else {
				//int count = Integer.parseInt(backorderedCount);
				//System.out.println("count:"+ count);
				//int incrementedcount = count++;
				//String incrementedcountStr = Integer.toString(incrementedcount);
				//backorderedCount = incrementedcountStr;
				//System.out.println("backorderedCount after increment:"+ backorderedCount);
				//System.out.println("incrementedcount:"+ incrementedcount);
				return false;
			}
		}
		
		return false;
		
	}
	
	private ResultSet fetchRecords(YFSEnvironment env, String strSqlQuery) throws Exception {

		ResultSet resultSet = null;

		Statement statement = null;

		Connection connection = null;

		YFSConnectionHolder context = (YFSConnectionHolder) env;

		connection = context.getDBConnection();

		statement = connection.createStatement();

		// statement.setMaxRows(iNumOfRecordsToBuffer);

		resultSet = statement.executeQuery(strSqlQuery);

		return resultSet;

	}
	
	private Document CreateDocumentForBackorderedCount(String strOHKey , String backorderedCount) {

		Document docChangeOrdInput = SCXmlUtil.createDocument("Order");

		Element eleChangeOrdRoot = docChangeOrdInput.getDocumentElement();

		Element eleExtn = SCXmlUtil.createChild(eleChangeOrdRoot, "Extn");
		
		Element eleOrdLineHoldTypes = SCXmlUtil.createChild(eleChangeOrdRoot, "OrderHoldTypes");

		Element eleOrdLineHoldType = SCXmlUtil.createChild(eleOrdLineHoldTypes, "OrderHoldType");

		eleChangeOrdRoot.setAttribute("OrderHeaderKey", strOHKey);

		eleChangeOrdRoot.setAttribute("Override", "Y");
		
		eleOrdLineHoldType.setAttribute("HoldType", "FRAUD_HOLD");

		eleOrdLineHoldType.setAttribute("Status", "1300");
		
		if (backorderedCount != null) {
			if (backorderedCount.equalsIgnoreCase("0")){
				backorderedCount = "1";
			}else if (backorderedCount.equalsIgnoreCase("1")) {
				backorderedCount = "2";
			}else if (backorderedCount.equalsIgnoreCase("2")) {
				backorderedCount = "3";
			}
			System.out.println("backorderedCount in CreateDocumentForBackorderedCount:"+ backorderedCount);
			eleExtn.setAttribute("BackorderedCount", backorderedCount);
		}
		else {
			eleExtn.setAttribute("BackorderedCount", "1");
		}
		
		return docChangeOrdInput;

	}
	
	
	private Document CreateDocument(String strOHKey) {

		Document docChangeOrdInput = SCXmlUtil.createDocument("Order");

		Element eleChangeOrdRoot = docChangeOrdInput.getDocumentElement();

		//Element eleOrdLineHoldTypes = SCXmlUtil.createChild(eleChangeOrdRoot, "OrderHoldTypes");

		//Element eleOrdLineHoldType = SCXmlUtil.createChild(eleOrdLineHoldTypes, "OrderHoldType");

		eleChangeOrdRoot.setAttribute("OrderHeaderKey", strOHKey);

		eleChangeOrdRoot.setAttribute("Override", "Y");

		//eleOrdLineHoldType.setAttribute("HoldType", "FRAUD_HOLD");

		//eleOrdLineHoldType.setAttribute("Status", "9000");

		return docChangeOrdInput;

	}
	
	public void executeJob(YFSEnvironment env, Document inDoc) throws Exception {

		System.out.println("Input to Execute Job:" + SCXmlUtil.getString(inDoc));

		Document cancelOrder = getApi().cancelOrder(env, inDoc);

		System.out.println("Output of Execute Job:" + SCXmlUtil.getString(cancelOrder));

	}
	
	public void incrementBackorderedCount(YFSEnvironment env, Document inDoc) throws Exception {

		System.out.println("Input to incrementBackorderedCount Execute Job:" + SCXmlUtil.getString(inDoc));

		Document changeOrder = getApi().changeOrder(env, inDoc);

		System.out.println("Output of incrementBackorderedCount Execute Job:" + SCXmlUtil.getString(changeOrder));

	}
	
	protected YIFApi getApi() throws Exception {

		if (api == null) {

			api = YIFClientFactory.getInstance().getLocalApi();

		}

		return api;

	}
	
	
	@Override
	public void setProperties(Properties arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
