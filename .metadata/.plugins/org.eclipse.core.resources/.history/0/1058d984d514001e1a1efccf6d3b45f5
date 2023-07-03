package com.DSA.logivalproblems;

public class DeciToBin {
	
	public static Long decToBin(int deci) {
		Long bin =0l;
		int place = 1;
		while(deci>0) {
			bin = bin+(deci % 2) * place;
			place *= 10;
			deci/=2;
		}
		return bin;
	}
	
	public static Long binToDec(Long bin) {
		int c =0;
		int res = (int) (bin%10);
		Long dec = 0l;
		while(bin>0) {
			if(res==1) {
			dec+=(int)Math.pow(2, c);
			}
			c++;
			bin/=10;
		}
		return dec;
	}

	public static void main(String[] args) {
		System.out.println(decToBin(7));
		System.out.println(binToDec(111l));
	}
}
