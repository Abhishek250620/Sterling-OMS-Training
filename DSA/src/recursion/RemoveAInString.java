package recursion;

public class RemoveAInString {

	static String removeA(String str, String subStr) {
		if(str.isEmpty()) {
			return subStr;
		}else {
			if(str.charAt(0)=='a') {
				return removeA(str.substring(1),subStr);
			}else {
				return removeA(str.substring(1),subStr+str.charAt(0));
			}
		}
	}
	public static void main(String[] args) {
		String str = "baccab";
		System.out.println(removeA(str,""));
//		System.out.println(str.substring(1));
	}
}
