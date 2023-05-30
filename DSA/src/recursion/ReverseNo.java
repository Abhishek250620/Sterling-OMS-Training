package recursion;

public class ReverseNo {
	int revNum=0;
	int sum=0;
	public static int rev(int number) {
		 if (number < 10) {
	            return number;
	        }
	        
	        int lastDigit = number % 10;
	        int remainingDigits = number / 10;
	        
	        int reversedNumber = rev(remainingDigits);
	        int numDigits = (int) Math.log10(remainingDigits) + 1;
	        
	        return (int) (lastDigit * Math.pow(10, numDigits)) + reversedNumber;
	}
	public static void main(String[] args) {
		System.out.println(rev(23456));
	}
}