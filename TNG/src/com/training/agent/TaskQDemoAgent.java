package com.training.agent;


import java.rmi.RemoteException;

import org.w3c.dom.Document;

//import com.oms10.util.XMLUtil;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.ycp.japi.util.YCPBaseTaskAgent;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;

public class TaskQDemoAgent extends YCPBaseTaskAgent  {

	protected static YIFApi api = null;
	@Override
	public Document executeTask(YFSEnvironment env, Document doc)
			throws Exception {
//		System.out.println("Input executeTask: " + XMLUtil.getStringFromDocument(doc));
		Document deleteOrderResult = getApi().deleteOrder(env, createDeleteOrderInput(doc).getDocument());
//		System.out.println("deleteOrderResult:" + XMLUtil.getStringFromDocument(deleteOrderResult));
		return doc;
	}
	
	private YFCDocument createDeleteOrderInput(Document doc) {
		String orderHeaderKey = doc.getDocumentElement().getAttribute("DataKey");
		YFCDocument  document = YFCDocument.createDocument("Order");
		document.getDocumentElement().setAttribute("OrderHeaderKey", orderHeaderKey);
		document.getDocumentElement().setAttribute("Action", "DELETE");
		return document;
	}
	
	protected YIFApi getApi() throws Exception {
		if(api == null) {
			api = YIFClientFactory.getInstance().getLocalApi();
		}
    	return api;
    }
	
}
