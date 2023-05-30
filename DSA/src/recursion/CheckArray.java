package recursion;

public class CheckArray {
	// Checking the whether array is sorted or not
	static boolean check(int n, int[] arr) {
		if(n>=arr.length-1) {
			return true;
		}
		return arr[n]<arr[n+1] && check(n+1,arr);
	}
	
	// Searching the target in array
	static int search(int[] arr,int index,int target) {
		if(index>=arr.length) {
			return -1;
		}
		if(arr[index]==target) {
			return index;
		}
		return search(arr,index+1,target);
	}
	public static void main(String[] args) {
		int[] arr= {1,2,3};
		System.out.println(check(0,arr));
		int res=search(arr,0,3);
		System.out.println(res==-1 ? "Element not found" : "Element found at "+res+" position");
	}
}