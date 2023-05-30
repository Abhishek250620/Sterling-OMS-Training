package recursion;

import java.util.*;

public class ReturnList {
	static List<Integer> search(int[] arr,int index,int target, List<Integer> list) {
		if(index==arr.length) {
			return list;
		}
		if(arr[index]==target) {
			list.add(index);
		}
		return search(arr,index+1,target,list);
	}
	public static void main(String[] args) {
		int[] arr= {1,23,23,4,4};
		System.out.println(search(arr,0,4,new ArrayList<Integer>()));
	}
}
