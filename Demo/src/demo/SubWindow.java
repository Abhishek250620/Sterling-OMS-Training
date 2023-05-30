package demo;

import java.util.*;

public class SubWindow {
	public String minWindow(String s, String t) {
        if(t.length()>s.length()){
            return "";
        }
        else {
            List s1=Arrays.asList(s.split(""));
            List t1=Arrays.asList(t.split(""));
            for(int i=0;i<t1.size();i++) {
            	for(int j=0;j<s1.size();j++) {
            		if(t1.get(i)==s1.get(j)) {
            			
            		}
            	}
            }
        }
    }
	public static void main(String[] args) {

	}
}
