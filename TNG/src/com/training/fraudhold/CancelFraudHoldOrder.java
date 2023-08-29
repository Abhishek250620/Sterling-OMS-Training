package com.training.fraudhold;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.sterlingcommerce.baseutil.SCXmlUtil;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.ycp.japi.util.YCPBaseAgent;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfs.japi.YFSConnectionHolder;
import com.yantra.yfs.japi.YFSEnvironment;

public class CancelFraudHoldOrder extends YCPBaseAgent{

	protected static YIFApi api = null;

	public List<Document> getJobs(YFSEnvironment env, Document inDoc, Document lastMessageCreated) throws Exception {

		List<Document> listDocuments = new ArrayList<Document>();
		Calendar fromDate = java.util.Calendar.getInstance();
		fromDate.add(java.util.Calendar.DATE, -7); 
		SimpleDateFormat sdfdatetime = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String fromDateFormated = sdfdatetime.format(fromDate.getTime());
		System.out.println("fromDateFormated:"+ fromDateFormated);
		
		String fromDateInQuery = "to_date('" + fromDateFormated + "','yyyy-MM-dd')";
		System.out.println("fromDateInQuery:"+ fromDateInQuery);
		
		
		//Date fromDateToCheck = sdfdatetime.parse(fromDateFormated);
		//System.out.println("fromDateToCheck:"+ fromDateToCheck);
		//String sql ="select order_header_key ,createts from yfs_order_hold_type where status='1100' and hold_type='FRAUD_HOLD'";
		
		String sql = "select order_header_key from yfs_order_hold_type where status='1100' and hold_type='FRAUD_HOLD' and createts<="+ fromDateInQuery ;
		System.out.println("sql Query: "+ sql);
		ResultSet rsRecords = fetchRecords(env, sql);

		if (!YFCCommon.isVoid(rsRecords)) {

			while (rsRecords.next()) {

				String strOHKey = rsRecords.getString(1).trim();
				System.out.println("OrderHeaderKey:"+ strOHKey);			
				Document inDocToExecuteJob = CreateDocument(strOHKey);
				listDocuments.add(inDocToExecuteJob);

			}

		}

		return listDocuments;

	}

	private Document CreateDocument(String strOHKey) {

		Document docChangeOrdInput = SCXmlUtil.createDocument("Order");

		Element eleChangeOrdRoot = docChangeOrdInput.getDocumentElement();

		Element eleOrdLineHoldTypes = SCXmlUtil.createChild(eleChangeOrdRoot, "OrderHoldTypes");

		Element eleOrdLineHoldType = SCXmlUtil.createChild(eleOrdLineHoldTypes, "OrderHoldType");

		eleChangeOrdRoot.setAttribute("OrderHeaderKey", strOHKey);

		eleChangeOrdRoot.setAttribute("Override", "Y");

		eleOrdLineHoldType.setAttribute("HoldType", "FRAUD_HOLD");

		eleOrdLineHoldType.setAttribute("Status", "1200");

		return docChangeOrdInput;

	}

	private ResultSet fetchRecords(YFSEnvironment env, String strSqlQuery) throws Exception {

		ResultSet resultSet = null;

		Statement statement = null;

		Connection connection = null;

		YFSConnectionHolder context = (YFSConnectionHolder) env;

		connection = context.getDBConnection();

		statement = connection.createStatement();

		// statement.setMaxRows(iNumOfRecordsToBuffer);
		System.out.println("strSqlQuery: "+ strSqlQuery);
		resultSet = statement.executeQuery(strSqlQuery);
		return resultSet;

	}

	@Override

	public void executeJob(YFSEnvironment env, Document inDoc) throws Exception {

		System.out.println("Input to Execute Job:" + SCXmlUtil.getString(inDoc));

		Document cancelOrder = getApi().cancelOrder(env, inDoc);

		System.out.println("Output of Execute Job:" + SCXmlUtil.getString(cancelOrder));

	}

	protected YIFApi getApi() throws Exception {

		if (api == null) {

			api = YIFClientFactory.getInstance().getLocalApi();

		}

		return api;

	}

}
