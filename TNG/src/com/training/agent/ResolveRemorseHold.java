package com.training.agent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
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

public class ResolveRemorseHold extends YCPBaseAgent {

	protected static YIFApi api = null;

	public List<Document> getJobs(YFSEnvironment env, Document inDoc, Document lastMessageCreated) throws Exception {

		List<Document> listDocuments = new ArrayList<Document>();

		String sql = "select order_header_key from yfs_order_hold_type where status='1100' and hold_type='REMORSE_HOLD'";

		ResultSet rsRecords = fetchRecords(env, sql);

		if (!YFCCommon.isVoid(rsRecords)) {

			while (rsRecords.next()) {

				String strOHKey = rsRecords.getString(1).trim();

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

		eleOrdLineHoldType.setAttribute("HoldType", "REMORSE_HOLD");

		eleOrdLineHoldType.setAttribute("Status", "1300");

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

		resultSet = statement.executeQuery(strSqlQuery);

		return resultSet;

	}

	@Override
	public void executeJob(YFSEnvironment env, Document inDoc) throws Exception {

		System.out.println("Input to Execute Job:" + SCXmlUtil.getString(inDoc));

		Document deleteOrderResult = getApi().changeOrder(env, inDoc);

		System.out.println("Output of Execute Job:" + SCXmlUtil.getString(deleteOrderResult));

	}

	protected YIFApi getApi() throws Exception {

		if (api == null) {

			api = YIFClientFactory.getInstance().getLocalApi();

		}

		return api;

	}

}
