package recursion;

import java.util.Arrays;

public class SellectionSort {
	
	static void sellectionSort(int[] arr, int row, int col, int max) {
		if(row == 0) {
			return;
		}
		if (col<row) {
			if(arr[col]>arr[max]) {
				sellectionSort(arr,row,col+1,col);
			}
			else {
				sellectionSort(arr,row,col+1,max);
			}
		} else {
			int temp = arr[max];
			arr[max]=arr[row-1];
			arr[row-1]=temp;
			sellectionSort(arr, row-1, 0, 0);
		}
	}
	
	public static void main(String[] args) {
		int[] arr= {98,67,45,67,78,23,3,12,24};
		sellectionSort(arr, arr.length, 0, 0);
		System.out.println(Arrays.toString(arr));
	}
	
}
