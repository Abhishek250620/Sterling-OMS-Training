package com.DSA.sorting;

import java.util.Arrays;

public class BubbleSort {
	
	static void bubbleSort(int[] arr) {
		for(int i=0;i<arr.length-1;i++) {
			for(int j=0;j<arr.length-1-i;j++) {
				if(arr[j]>arr[j+1]) {
					int temp = arr[j];
					arr[j] = arr[j+1];
					arr[j+1] = temp;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		int[] arr = {34,12,45,23,13,56,78,28};
		bubbleSort(arr);
		System.out.println(Arrays.toString(arr));
	}
}
