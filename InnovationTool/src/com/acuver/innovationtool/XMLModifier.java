package com.acuver.innovationtool;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;

public class XMLModifier {

	public static void main(String[] args) {
		String filePath = "D:\\InnovationTool\\input.xml";
		xmlModification(filePath);

	}

	public static void xmlModification(String filePath) {

		try {

			// Load the XML file into a Document object
			File inputFile = new File(filePath);
			if (inputFile.exists()) {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(inputFile);

				// Get the root Order element
				Element rootElement = document.getDocumentElement();
				
				if(!rootElement.getNodeName().equals("Order")) {
					System.out.println("Order Tag does not exist");
					return;
				}

				Element extn = document.createElement("Extn");

				extn.setAttribute("BrandName", "ABC");

				rootElement.appendChild(extn);

				// Get all the OrderLine elements
				NodeList orderLines = document.getElementsByTagName("OrderLine");
				if(orderLines.getLength()==0) {
					System.out.println("OrderLine Tag does not exist");
					return;
				}

				for (int i = 0; i < orderLines.getLength(); i++) {
					Element orderLine = (Element) orderLines.item(i);
					String orderLineNo = orderLine.getAttribute("OrderLineNo");
					orderLine.setAttribute("PrimeLineNo",orderLineNo);
					orderLine.setAttribute("SubLineNo", orderLineNo);

					orderLine.removeAttribute("OrderLineNo");

					extn = document.createElement("Extn");

					// Create <EXTNOrderLineInfoList> element
					Element extnOrderLineInfoList = document.createElement("EXTNOrderLineInfoList");

					// Create <EXTNOrderLineInfo> element
					Element extnOrderLineInfo = document.createElement("EXTNOrderLineInfo");
					extnOrderLineInfo.setAttribute("ColorCode", "");
					extnOrderLineInfo.setAttribute("SizeCode", "");

					extnOrderLineInfoList.appendChild(extnOrderLineInfo);

					extn.appendChild(extnOrderLineInfoList);

					orderLine.appendChild(extn);
				}

				// Save the modified Document back to the same file
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");

				FileOutputStream fos = new FileOutputStream("D:\\InnovationTool\\output.xml");
				transformer.transform(new DOMSource(document), new StreamResult(fos));
				System.out.println("XML file has been modified and saved.");
			}
			else {
				System.out.println("File does not exist.");
			}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
