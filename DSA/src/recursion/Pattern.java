package recursion;

public class Pattern {
	//*************************************************
	static void print1(int n) {
		if(n==0) {
			return;
		}
		print2(n);
		System.out.println();
		print1(n-1);
	}

	static void print2(int n) {
		if(n==0) {
			return;
		}
		System.out.print("* ");
		print2(n-1);
	}
	//****************************************************
	static void print3(int row,int col) {
		if(row==0) {
			return;
		}
		print4(col);
		System.out.println();
		print3(row-1,col);
	}

	static void print4(int col) {
		if(col==0) {
			return;
		}
		System.out.print("* ");
		print4(col-1);
	}
	//****************************************************
	//****************************************************
	public static void main(String[] args) {
		print1(5);
		print3(5,5);
	}
}