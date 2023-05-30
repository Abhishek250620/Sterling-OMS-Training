package demo;
import java.util.*;
public class Grid {
	 public static  List<Integer> spiralOrder(int[][] arr) {
	        int i=0;
	        int j=0;
	        List l=new ArrayList();
	        while(true){
	            l.add(arr[i][j]);
	            if(i==arr.length/2 && j==arr[1].length-2){
	                break;
	            }
	            if(i==0 && j!=arr[1].length-1){
	                j++;
	            }
	            else if(j==arr[1].length-1 && i<=arr.length-2){
	                i++;
	            }
	            else if(i==arr.length-1 && j>0){
	                j--;
	            }
	            else if(i==arr.length-1 && i>0 && j==0){
	                i--;
	            }
	            else{
	                j++;
	            }
	        }
	        return l;
	    }
	
	public static void main(String[] args) {
		int[][] arr= {{1,2,3},{4,5,6},{7,8,9}};
		spiralOrder(arr);
		
	}
}
