package com.anjava;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		System.out.println(hc.postLogIn("test7", pw));
//		System.out.println(Arrays.toString(fst));
//		Date resetDate = new Date();
//		JSONObject roomsData = new JSONObject(hc.getAllRoom());
//		
//		System.out.println(roomsData.getJSONObject("data").getJSONArray("roomsData").getJSONObject(0).isNull("resetDate"));
//		System.out.println(roomsData.getJSONObject("data").getJSONArray("roomsData").getJSONObject(0).getString("resetDate"));
//		Date date = new Date(122, 7, 20, 13, 0);
		System.out.println(hc.deleteRoom(9));
		int a;
		
		
	}

}
