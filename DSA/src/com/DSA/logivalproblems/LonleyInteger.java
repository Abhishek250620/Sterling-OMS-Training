package com.DSA.logivalproblems;

import java.util.*;

public class LonleyInteger {
	public static int lonelyinteger(List<Integer> a) {
		HashSet<Integer> hashSet = new HashSet();
		for(int ele : a) {
			if(hashSet.contains(ele)) {
				hashSet.remove(ele);
			}
			else {
				hashSet.add(ele);
			}
		}
		System.out.println(hashSet);
		for(int ele : hashSet) {
			return ele;
		}
		return 0;
	}
	public static void main(String[] args) {
		System.out.println(Math.pow(2, 2));
		List<Integer> list = new ArrayList<Integer>();
		list.addAll(Arrays.asList(2,1,2,1,3,4,2,5,6,4));
		System.out.println(lonelyinteger(list));
	}
}
