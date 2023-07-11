package com.leetcode.dsa;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class LongestSubString {
	
	public static int longestSubString(String str) {
		int start = 0;
		int end = 0;
		int count = 0;
		int length = str.length();
		Set<Character> set = new TreeSet<>();
		while(start<length && end<length) {
			char ch = str.charAt(end);
			if(!set.contains(ch)) {
				set.add(ch);
				end++;
				count = Math.max(end-start, count);
			}else {
				set.remove(ch);
				start++;
			}
		}
		return count;
	}
	
	public static int maxSubString(String str) {
		int start=0;
		int count=0;
		Map<Character,Integer> map = new HashMap<>();
		for(start=0;start<str.length();start++) {
			char ch = str.charAt(start);
			if(map.containsKey(ch)) {
				start=map.get(ch);
				map.clear();
			}else {
				map.put(ch, start);
			}
			
			count = Math.max(map.size(), count);
		}
		return count;
	}
	
	public static void main(String[] args) {
		String str = "aaabdasdfwewewq";
		System.out.println(longestSubString(str));
		System.out.println(maxSubString(str));
	}
}
