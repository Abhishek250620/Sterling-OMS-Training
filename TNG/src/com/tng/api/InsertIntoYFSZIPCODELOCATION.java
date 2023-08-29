package com.tng.api;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.yantra.interop.japi.YIFCustomApi;
import com.yantra.yfs.japi.YFSConnectionHolder;
import com.yantra.yfs.japi.YFSEnvironment;

public class InsertIntoYFSZIPCODELOCATION implements YIFCustomApi{

	@Override
	public void setProperties(Properties arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public void insertIntoYFSZIPCODELOCATION(YFSEnvironment env, Document inDoc) throws SQLException {
		
		Element rootEle = inDoc.getDocumentElement();
		
		String ZipCodeLocationKey = rootEle.getAttribute("ZIP_CODE_LOCATION_KEY");
		String Country = rootEle.getAttribute("COUNTRY");
		String State = rootEle.getAttribute("STATE");
		String ZipCode = rootEle.getAttribute("ZIP_CODE");
		String City = rootEle.getAttribute("CITY");
		String Latitude = rootEle.getAttribute("LATITUDE");
		String Longitude = rootEle.getAttribute("LONGITUDE");
		
		String sqlQuery = String.format("INSERT INTO YFS_ZIP_CODE_LOCATION (ZIP_CODE_LOCATION_KEY,COUNTRY,STATE,ZIP_CODE,CITY,LATITUDE,LONGITUDE) VALUES (%s, '%s', '%s', %s, '%s', %s, %s);", ZipCodeLocationKey, Country, State, ZipCode, City, Latitude, Longitude);
		
		System.out.println(sqlQuery);
		
		Statement statement = null;

		Connection connection = null;

		YFSConnectionHolder context = (YFSConnectionHolder) env;

		connection = context.getDBConnection();

		statement = connection.createStatement();

		// statement.setMaxRows(iNumOfRecordsToBuffer);

		statement.executeQuery(sqlQuery);
		
		connection.commit();

	}

}
