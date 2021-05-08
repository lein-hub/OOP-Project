package com.anjava;

public class TestField {
	
	public static void main(String[] args) {
		int[] fst = {3, 6};
		int[] scd = {4, 8};
		
		HttpCaller hc = new HttpCaller();
		System.out.println(hc.postLogIn("test6", "12341234"));
//		System.out.println(hc.getOneRoom(205));
//		System.out.println(hc.postCreateRoom(15, 8, 10, fst, scd));
//		System.out.println(hc.getUserDetail());
//		System.out.println(hc.getOneRoom(204));
	}

}
