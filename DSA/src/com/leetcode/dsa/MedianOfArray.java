package com.leetcode.dsa;

import java.util.*;

public class MedianOfArray {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
       List<Integer> set = new ArrayList<Integer>();
		for(int i=0;i<nums1.length;i++){
			set.add(nums1[i]);
		}
		for(int i=0;i<nums2.length;i++){
			set.add(nums2[i]);
		}
		Collections.sort(set);
		int median = set.size()/2;
		if(set.size()%2==0) {
			return (double)(set.get(median)+set.get(median-1))/2;
		}
		return set.get(median);
    }
}
