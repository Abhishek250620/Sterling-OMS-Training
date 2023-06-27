package com.DSA.logivalproblems;

public class SumOfNNaturalNumbers {
	
	public static int nSum(int n) {
		int sum = (n*(n+1))/2;
		return sum;
	}
	public static void main(String[] args) {
		System.out.println(nSum(10));
	}
}
