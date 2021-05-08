package com.anjava;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.json.JSONObject;


//import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//import java.io.IOException;

public class HttpCaller {
	
	private String url = "https://anjava-api-server.herokuapp.com/";
	// sign = url+"users/sign/"
	// logIn = url+"users/"+{userId}
	// room = url+"room/"
	
	private String id = "";
	private String token = "";
	private boolean isSuccessful = false;
	
	private String request(String type, String requestURL, String jsonMessage) {
		try{
			OkHttpClient client = new OkHttpClient();
			Request request;
			switch (type) {
			case "POST":
				request = new Request.Builder()
				.addHeader("Authorization", token)
				.url(requestURL)
				.post(RequestBody.create(jsonMessage, MediaType.parse("application/json; charset=utf-8"))) //POST로 요청
				.build();
				break;
			case "DELETE":
				request = new Request.Builder()
				.addHeader("Authorization", token)
				.url(requestURL)
				.delete(RequestBody.create(jsonMessage, MediaType.parse("application/json; charset=utf-8"))) //DELETE로 요청
				.build();
				break;
			case "PATCH":
				request = new Request.Builder()
				.addHeader("Authorization", token)
				.url(requestURL)
				.patch(RequestBody.create(jsonMessage, MediaType.parse("application/json; charset=utf-8"))) //PATCH로 요청
				.build();
				break;
			default:
				request = new Request.Builder()
				.addHeader("Authorization", token)
				.url(requestURL) //GET로 요청
				.build();
				break;
			}
                        //동기 처리시 execute함수 사용
			Response response = client.newCall(request).execute();
			
			this.isSuccessful = response.isSuccessful();

			//출력
			String message = response.body().string();
			
			return message;

		} catch (Exception e) {
			System.err.println(e.toString());
			return "API request and response failed";
		}
	}
	
	public String getUserDetail() {
		return this.request("GET", url+"users/"+this.id, null);
	}
	
	public String getOneRoom(int roomNum) {
		return this.request("GET", url+"room/"+String.valueOf(roomNum), null);
	}
	
	public String getAllRoom(int roomNum) {
		return this.request("GET", url+"room/", null);
	}
	
	public String postSign(String id, String pw, String name, int yNum, String email) {
		JSONObject jo = new JSONObject();
		
		jo.put("userId", id);
		jo.put("password", pw);
		jo.put("name", name);
		jo.put("yjuNum", yNum);
		jo.put("email", email);
		return this.request("POST", url+"users/sign/", jo.toString());
	}
	
	public String postLogIn(String id, String pw) {
		String result = this.request("POST", url+"users/", "{\"userId\":\""+id+"\",\"password\":\""+pw+"\"}");
		if (isSuccessful) {
			JSONObject jo = new JSONObject(result).getJSONObject("data");
			this.id = id;
			this.token = jo.getString("token");
		}
		return result;
	}
	
	public String postCreateRoom(int roomNum, int col, int row, int[] rowBlank, int[] colBlank) {
		JSONObject jo = new JSONObject();
		
		jo.put("roomNum", roomNum);
		jo.put("column", col);
		jo.put("row", row);
		jo.put("rowBlankLine", Arrays.toString(rowBlank));
		jo.put("colBlankLine", Arrays.toString(colBlank));
		return this.request("POST", url+"room/", jo.toString());
	}
	
	public String postReserveRoom(int roomNum, int sitNum) {
		JSONObject jo = new JSONObject();
		
		jo.put("sitNum", sitNum);
		
		return this.request("POST", url+"room/"+roomNum+"/reserve", jo.toString());
	}
	
	public String patchResetDateRoom(int roomNum, Date resetDate) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS:SZ");
		JSONObject jo = new JSONObject();
		
		jo.put("roomNum", roomNum);
		jo.put("resetDate", df.format(resetDate));
		
		return this.request("PATCH", url+"room/"+roomNum+"/reset", jo.toString());
	}
	
	public String deleteReserveRoom(int roomNum, int sitNum) {
		JSONObject jo = new JSONObject();
		
		
		jo.put("sitNum", sitNum);
		
		return this.request("DELETE", url+"room/"+roomNum+"/reserve", jo.toString());
	}
	
	public String deleteReserveRoom(int roomNum, String userId) {
		JSONObject jo = new JSONObject();
		
		
		jo.put("userId", userId);
		
		return this.request("DELETE", url+"room/"+roomNum+"/reserve", jo.toString());
	}
	
	public String deleteReserveRoom(int roomNum) {
		return this.request("DELETE", url+"room/"+roomNum+"/reserve", null);
	}
	
	public boolean isLoggedIn() {
		if (token.isEmpty()) return false;
		return true;
	}
	
}
