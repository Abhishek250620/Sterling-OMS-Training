package com.training.ue;

import java.util.Random;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSUserExitException;
import com.yantra.yfs.japi.ue.YFSBeforeCreateOrderUE;

public class CheckItemNumberUE implements YFSBeforeCreateOrderUE {

	@Override
	public String beforeCreateOrder(YFSEnvironment arg0, String arg1) throws YFSUserExitException {
		return null;
	}
	@Override
	public Document beforeCreateOrder(YFSEnvironment env, Document inputDoc) throws YFSUserExitException {
		Element eleOrderLine = (Element) inputDoc.getElementsByTagName("OrderLine").item(0);
		Element eleItem = (Element) eleOrderLine.getElementsByTagName("Item").item(0);
		String itemid = eleItem.getAttribute("ItemID");

		if (itemid.isEmpty()) {
			throw new YFSUserExitException("ItemID is empty");
		}

		Element rootElement = inputDoc.getDocumentElement();

		NodeList itemNodes = inputDoc.getElementsByTagName("Item");
		Element itemElement = (Element) itemNodes.item(0);
		String itemID = itemElement.getAttribute("ItemID");
		
		Random random = new Random();
		String randomNum = ""+random.nextInt(10000);
		
		Element extn = inputDoc.createElement("Extn");
		extn.setAttribute("SubItemId", "V-"+itemID);
		extn.setAttribute("CustomerNo",randomNum);
		rootElement.appendChild(extn);
		return inputDoc;
	}

}
