package com.DSA.bitwise;

public class AndOrXor {
	public static void main(String[] args) {
		System.out.println(5&9);
		System.out.println(5|9);
		System.out.println(5^9);
		String sqlQuery = String.format("INSERT INTO YFS_ZIP_CODE_LOCATION (ZIP_CODE_LOCATION_KEY,COUNTRY,STATE,ZIP_CODE,CITY,LATITUDE,LONGITUDE) VALUES (%s, '%s', '%s', %s, '%s', %s, %s);", "ZipCodeLocationKey", "Country", "State", "ZipCode", "City", "Latitude", "Longitude");

		System.out.println(sqlQuery);
	}
}
