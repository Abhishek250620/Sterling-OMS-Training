package demo;

public class Odd {
	public static void odd(int n) {
		int a=1;
		for(int i=1;i<=n;i++) {
			for(int w=i;w<n;w++) {
				System.out.print(" ");
			}
			for(int j=1;j<=a;j++) {
				System.out.print(a);
			}
			a+=2;
			System.out.println();
		}
	}
	
	public static void all(int[] arr) {
		int sum=arr[0];
		int max=arr[0];
		for(int i=1;i<arr.length;i++) {
			sum+=arr[i];
			
			if(max<arr[i]) {
				max=arr[i];
			}
		}
		System.out.println(sum+" "+sum/arr.length+" "+max);
	}
	public static void main(String[] args) {
		odd(5);
		int[] arr= {1,2,3,4,5,6};
		all(arr);
	}
}
