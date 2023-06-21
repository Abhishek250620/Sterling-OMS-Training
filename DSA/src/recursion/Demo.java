package recursion;

import java.util.Scanner;
public class Demo {
	public static String MinWindowSubstring(String[] strArr) {
	    return strArr[0];
	  }

	  public static void main (String[] args) {  
	    Scanner s = new Scanner(System.in);
	    System.out.print(MinWindowSubstring(new String[] {"ahffaksfajeeubsne", "jefaa"})); 
	  }
}

//Input: new String[] {"ahffaksfajeeubsne", "jefaa"}
//Output: aksfaje