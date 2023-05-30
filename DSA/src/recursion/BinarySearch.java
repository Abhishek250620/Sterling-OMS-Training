package recursion;

public class BinarySearch {
	public static void main(String[] args) {
		int[] arr= {12,23,34,45,56,67,78,89,90};
		int key=90;
		System.out.println(binSearch(arr, key, 0, arr.length-1));
	}
	public static int binSearch(int[] arr,int key,int start,int end) {
		if(start>end) {
			return -1;
		}
		int mid=start+(end-start)/2;
		if(arr[mid]==key) {
			return mid;
		}
		else if(key>arr[mid]) {
			return binSearch(arr, key, mid+1, end);
		}
		else {
			return binSearch(arr, key, start, mid-1);
		}
	}
}
