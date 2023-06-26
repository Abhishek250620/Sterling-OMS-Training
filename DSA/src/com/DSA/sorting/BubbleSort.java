package com.DSA.sorting;

import java.util.Arrays;

public class BubbleSort {
	
	static void bubbleSort(int[] arr) {
		for(int i=0;i<arr.length-1;i++) {
			boolean flag = false;
			for(int j=0;j<arr.length-1-i;j++) {
				if(arr[j]>arr[j+1]) {
					int temp = arr[j];
					arr[j] = arr[j+1];
					arr[j+1] = temp;
					flag = true;
				}
			}
			if(!flag) {
				break;
			}
		}
	}
	
	public static void main(String[] args) {
		int[] arr = {1,34,20};
		bubbleSort(arr);
		System.out.println(Arrays.toString(arr));
	}
}
