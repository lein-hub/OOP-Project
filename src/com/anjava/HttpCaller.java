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
	
	private String logIn = "https://anjava-api-server.herokuapp.com/users/";
	private String sign = "https://anjava-api-server.herokuapp.com/users/sign";
	private String createRoom = "https://anjava-api-server.herokuapp.com/room/";
	private String room = "https://anjava-api-server.herokuapp.com/room/";
	
	private String id = "";
	private String token = "";
	private boolean isSuccessful = false;
	
//	    String run(String url) throws IOException {
//	        Request request = new Request.Builder().url(url).build();
//	 
//	        Response response = client.newCall(request).execute();
//	        return response.body().string();
//	    }
//	    
//	    
//	    public static void main(String[] args) throws IOException {
//	        String url = "http://www.google.com";
//	        String responseString = new HttpCaller().run(url);
//	        System.out.println(responseString);
//	    }
	    
//	    public static void whenPostJson_thenCorrect() throws IOException {
//	    	
//	   	 OkHttpClient client = new OkHttpClient();
//	        String json = "{\"userId\":aio,\"password\":\"12341234\"}";
//
//	        RequestBody body = RequestBody.create(
//	          MediaType.parse("application/json"), json);
//
//	        Request request = new Request.Builder()
//	          .url("http://anjava.aio392.com/users")
//	          .post(body)
//	          .build();
//	     
//	        Call call = client.newCall(request);
//	        Response response = call.execute();
//	        
//	        System.out.println(response.body().string());
//	    }
//	    
//	    public static void main(String[] args) {
//	    	try {
//				whenPostJson_thenCorrect();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//	    }
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
	
//	private String post(String requestURL, String jsonMessage) {
//		try{
//			OkHttpClient client = new OkHttpClient();
//			Request request = new Request.Builder()
//					.addHeader("Authorization", token)
//					.url(requestURL)
//					.post(RequestBody.create(jsonMessage, MediaType.parse("application/json; charset=utf-8"))) //POST로 요청
//					.build();
//                        //동기 처리시 execute함수 사용
//			Response response = client.newCall(request).execute();
//			
//			this.isSuccessful = response.isSuccessful();
//
//			//출력
//			String message = response.body().string();
//			
//			return message;
//
//		} catch (Exception e) {
//			System.err.println(e.toString());
//			return "API request and response failed";
//		}
//	}
//	
//	private String delete(String requestURL, String jsonMessage) {
//		try{
//			OkHttpClient client = new OkHttpClient();
//			Request request = new Request.Builder()
//					.addHeader("Authorization", token)
//					.url(requestURL)
//					.delete(RequestBody.create(jsonMessage, MediaType.parse("application/json; charset=utf-8"))) //POST로 요청
//					.build();
//                        //동기 처리시 execute함수 사용
//			Response response = client.newCall(request).execute();
//			
//			this.isSuccessful = response.isSuccessful();
//
//			//출력
//			String message = response.body().string();
//			
//			return message;
//
//		} catch (Exception e) {
//			System.err.println(e.toString());
//			return "API request and response failed";
//		}
//	}
//	
//	private String get(String requestURL) {
//		try{
//			OkHttpClient client = new OkHttpClient();
//			Request request = new Request.Builder()
//					.addHeader("Authorization", token)
//					.url(requestURL)
//					.build();
//                        //동기 처리시 execute함수 사용
//			Response response = client.newCall(request).execute();
//			
//			this.isSuccessful = response.isSuccessful();
//
//			//출력
//			String message = response.body().string();
//			
//			return message;
//
//		} catch (Exception e) {
//			System.err.println(e.toString());
//			return "API request and response failed";
//		}
//	}
	
	public String getUserDetail() {
		return this.request("GET", logIn+this.id, null);
	}
	
	public String getOneRoom(int roomNum) {
		return this.request("GET", room+String.valueOf(roomNum), null);
	}
	
	public String getAllRoom(int roomNum) {
		return this.request("GET", room, null);
	}
	
	public String postSign(String id, String pw, String name, int yNum, String email) {
		JSONObject jo = new JSONObject();
		
		jo.put("userId", id);
		jo.put("password", pw);
		jo.put("name", name);
		jo.put("yjuNum", yNum);
		jo.put("email", email);
		return this.request("POST", sign, jo.toString());
	}
	
	public String postLogIn(String id, String pw) {
		String result = this.request("POST", logIn, "{\"userId\":\""+id+"\",\"password\":\""+pw+"\"}");
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
		return this.request("POST", room, jo.toString());
	}
	
	public String postReserveRoom(int roomNum, int sitNum) {
		JSONObject jo = new JSONObject();
		
		jo.put("sitNum", sitNum);
		
		return this.request("POST", room+roomNum+"/reserve", jo.toString());
	}
	
	public String patchResetDateRoom(int roomNum, Date resetDate) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS:SZ");
		JSONObject jo = new JSONObject();
		
		jo.put("roomNum", roomNum);
		jo.put("resetDate", df.format(resetDate));
		
		return this.request("PATCH", room+roomNum+"/reset", jo.toString());
	}
	
	public String deleteReserveRoom(int roomNum, int sitNum) {
		JSONObject jo = new JSONObject();
		
		jo.put("sitNum", sitNum);
		
		return this.request("DELETE", room+roomNum+"/reserve", jo.toString());
	}
	
	public boolean isLoggedIn() {
		if (token.isEmpty()) return false;
		return true;
	}
	
}
