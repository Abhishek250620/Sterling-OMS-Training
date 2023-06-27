package com.DSA.logivalproblems;

import java.util.*;

public class MaximumStrength {
	public static long maxStrength(int[] nums) {
		Long res = (long)1;
		TreeSet<Long> set = new TreeSet<Long>();
		for(int i=0;i<nums.length;i++){
			res= res*nums[i];
			set.add(res);
		}
		System.out.println(set);
		return set.last();
	}
	public static void main(String[] args) {
		int[] nums = {3,-1,-5,2,5,-9};
		System.out.println(maxStrength(nums));
	}
	
}
