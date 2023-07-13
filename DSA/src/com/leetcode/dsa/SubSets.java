package com.leetcode.dsa;

import java.util.ArrayList;
import java.util.List;

public class SubSets {
	
	static void subSet(String str,String sub) {
		if(str.isEmpty()) {
			System.out.println(sub);
			return;
		}
		
		char ch = str.charAt(0);
		subSet(str.substring(1),sub+ch);
		subSet(str.substring(1),sub);
	}
	
	static List<String> subSet(String str) {
		List<String> list = new ArrayList<String>();
		list.add(" ");
		for(int i =0;i<str.length();i++) {
			String sub="";
			for(int j=i;j<str.length();j++) {
				sub+=str.charAt(j);
				list.add(sub);
			}
		}
		return list;
	}
	
	public static void main(String[] args) {
		String str="abc";
		subSet(str,"");
		System.out.println(subSet(str));
	}
}
