package com.anjava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TestField {
	
	public static void main(String[] args) {
		int[] fst = {3};
		int[] scd = {2,4};
		char[] pw = {'1','2','3','4','1','2','3','4'};
		HttpCaller hc = new HttpCaller();
		System.out.println(hc.postLogIn("test6", pw));
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
		
		
	    //I assume that we need to create a JSONArray object from the following string
	    String jsonArrStr = "[ { \"ID\": \"135\", \"Name\": \"Fargo Chan\" },{ \"ID\": \"432\", \"Name\": \"Aaron Luke\" },{ \"ID\": \"252\", \"Name\": \"Dilip Singh\" }]";

	    
	}

}
