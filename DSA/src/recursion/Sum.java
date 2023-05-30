package recursion;

public class Sum {
	
	public Sum() {
		super();
	}

	public static int sum(int n) {
		if(n==0) {
			return 0;
		}
		return n%10+sum(n/10);
	}
	
	public static int mul(int n) {
		if(n%10==n) {
			return n;
		}
		return n%10*mul(n/10);
	}
	public static void main(String[] args) {
		System.out.println(sum(1231));
		System.out.println(mul(1231));
	}
}
