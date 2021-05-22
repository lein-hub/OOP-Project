package com.anjava;

import java.util.Arrays;
import java.util.Date;

import org.json.JSONObject;

public class TestField {
	
	public static void main(String[] args) {
		int[] fst = {3};
		int[] scd = {2,4};
		HttpCaller hc = new HttpCaller();
		System.out.println(hc.postLogIn("test6", "12341234"));
		System.out.println(Arrays.toString(fst));
		Date resetDate = new Date();
		JSONObject roomsData = new JSONObject(hc.getAllRoom());
//		System.out.println(hc.postCreateRoom(202, 6, 8));
//		System.out.println(hc.postCreateRoom(203, 6, 8, fst, null));
//		System.out.println(hc.postCreateRoom(204, 6, 8, null, scd));
//		System.out.println(hc.postCreateRoom(205, 6, 8, fst, scd));
		System.out.println(roomsData.getJSONObject("data").getJSONArray("roomsData").getJSONObject(0).isNull("resetDate"));
		System.out.println(roomsData.getJSONObject("data").getJSONArray("roomsData").getJSONObject(0).getString("resetDate"));
//		System.out.println(roomsData.getJSONArray("roomsData").getJSONObject(0).getString("resetDate"));
		
//		System.out.println(hc.getOneRoom(205));
		for (int i=100; i<120; i++) {
		System.out.println(hc.postCreateRoom(i,7,8));
		}
		System.out.println(hc.deleteRoom(999));
//		System.out.println(hc.getUserDetail());
//		System.out.println(hc.getOneRoom(204));
	}

}
