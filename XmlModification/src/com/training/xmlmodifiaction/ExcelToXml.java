package com.training.xmlmodifiaction;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ExcelToXml {
	public static void main(String[] args) throws Exception {
		String filePath = "D:\\workspace\\input.xlsx";
		Document xmlDocument = genrateXML(filePath);
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes"); // Set output property to indent the XML
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); // Set the number of spaces to use for indentation
		DOMSource source = new DOMSource(xmlDocument);
		StreamResult result = new StreamResult(System.out);
		transformer.transform(source, result);
	}

	public static Document genrateXML(String filePath) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument();

		Element root = doc.createElement("MultiApi");
		doc.appendChild(root);
		Map<String,String> properties= readXlsx(filePath);
//		System.out.println(properties);

		Set<String> keySet = properties.keySet();
		for(String key:keySet) {
			Element Api = doc.createElement("API");
			root.appendChild(Api);
			Api.setAttribute("Name", "manageProperty");
			Element input = doc.createElement("Input");
			Api.appendChild(input);

			Element property = doc.createElement("Property");
			String value = (String) properties.get(key);
			property.setAttribute("BasePropertyName", key);
			property.setAttribute("Category", "yfs");
			property.setAttribute("PropertyOverrideName", "");
			property.setAttribute("PropertyOverride", "BASE");
			property.setAttribute("PropertyValue", value);
			input.appendChild(property);

			Element propertyMetadata = doc.createElement("PropertyMetadata");
			propertyMetadata.setAttribute("BasePropertyName", key);
			propertyMetadata.setAttribute("Category", "yfs");
			propertyMetadata.setAttribute("Description", "");
			propertyMetadata.setAttribute("JvmType", "APPSERVER");
			propertyMetadata.setAttribute("Modifiable", "Y");
			propertyMetadata.setAttribute("ModifiableAtRuntime", "Y");
			propertyMetadata.setAttribute("PropertyType", "CUSTOM");
			propertyMetadata.setAttribute("Scope", "GLOBAL");
			propertyMetadata.setAttribute("ServerOverride", "N");
			propertyMetadata.setAttribute("UserOverride", "N");
			property.appendChild(propertyMetadata);

		}
		return doc;
	}

	static Map<String,String> readXlsx(String filePath) throws Exception {
		FileInputStream fis = new FileInputStream(filePath);
		Workbook workbook = WorkbookFactory.create(fis) ;

		Sheet sheet = workbook.getSheetAt(0); 

		int lastRowNum = sheet.getLastRowNum();
//		System.out.println(lastRowNum);
		Map<String,String> property = new LinkedHashMap<String,String>();
		for (int i = 1; i <= lastRowNum; i++) {
			Row row = sheet.getRow(i);
			if (row != null) {
				int lastCellNum = row.getLastCellNum();
				String key="";
				String Value="";
				for (int j = 0; j < lastCellNum; j++) {
					Cell cell = row.getCell(j);
					String cellValue = cell.getStringCellValue();
					if(j==0) {
						key=cellValue;
					}else {
						Value=cellValue;
					}
				}
				property.put(key, Value);
			}
		}
		return property;
	}
}

