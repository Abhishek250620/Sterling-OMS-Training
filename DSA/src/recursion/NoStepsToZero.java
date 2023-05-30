package recursion;

public class NoStepsToZero {
	static int noSteps(int n) {
		if(n==0) {
			return 0;
		}
		if(n%2==0) {
			return 1+noSteps(n/2);
		}
		return 1+noSteps(n-1);
	}
	public static void main(String[] args) {
		System.out.println(noSteps(8));
	}
}
