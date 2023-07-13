package com.leetcode.dsa;

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
	
	public static void main(String[] args) {
		String str="abc";
		subSet(str,"");
	}
}
