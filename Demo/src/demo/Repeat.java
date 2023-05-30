package demo;
import java.util.*;

public class Repeat {
	public static String repeat(String str1, char ch) {
		List l=new ArrayList(Arrays.asList(str1.split("")));
		for(int i=0;i<l.size();i++) {
			if(l.get(i)==""+ch){
				l.remove(i);
			}
		}
		Object[] str= l.toArray();
		return new String();
	}


	public static void main(String[] args) {
		String str1="computer";
		String str2="cat";
		
		for(int i=0;i<str2.length();i++) {
			str1=repeat(str1,str2.charAt(i));
		}
	}
}
