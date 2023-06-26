package com.training.ue;

import java.util.Random;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSUserExitException;
import com.yantra.yfs.japi.ue.YFSBeforeCreateOrderUE;

public class BeforeCreateOrderUE implements YFSBeforeCreateOrderUE {

	@Override
	public String beforeCreateOrder(YFSEnvironment arg0, String arg1) throws YFSUserExitException {
		return null;
	}
	@Override
	public Document beforeCreateOrder(YFSEnvironment env, Document inputDoc) throws YFSUserExitException {
		NodeList orderLineList = inputDoc.getElementsByTagName("OrderLine");

		for(int i=0;i<orderLineList.getLength();i++) {
			Element orderLine = (Element) orderLineList.item(i);
			Element eleItem = (Element) orderLine.getElementsByTagName("Item").item(0);
			String itemid = eleItem.getAttribute("ItemID");

			if (itemid.isEmpty()) {
				throw new YFSUserExitException("ItemID is empty in OrderLine :" +(i+1));
			}

			Element extn = inputDoc.createElement("Extn");
			extn.setAttribute("SubItemId", "V-"+itemid);
			orderLine.appendChild(extn);
		}

		Element rootElement = inputDoc.getDocumentElement();

		Random random = new Random();
		String randomNum = ""+random.nextInt(10000);

		Element extn = inputDoc.createElement("Extn");
		extn.setAttribute("CustomerNo",randomNum);
		rootElement.appendChild(extn);
		return inputDoc;
	}

}
