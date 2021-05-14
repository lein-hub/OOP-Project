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
	
	private String id = "";  // 사용자의 id가 저장됩니다.
	private String token = "";  // 사용자의 토큰(식별자)이 저장됩니다.
	private String name;  // 사용자의 이름이 저장됩니다.
	private boolean isAdmin;  // 사용자의 관리자 여부가 저장됩니다.
	private int yjuNum;  // 사용자의 학번이 저장됩니다.
	private String email;  // 사용자의 이메일이 저장됩니다.
	
	private boolean isSuccessful = false;  // 요청이 수행될 때 마다 성공적으로 응답을 수신하였는지 저장됩니다.
	
	private String request(String type, String requestURL, String jsonMessage) {
		try{
			OkHttpClient client = new OkHttpClient();  // OkHttpClient 객체를 생성합니다.
			
			Request request;  // 요청 객체를 선언합니다.
			switch (type) {  // 인자로 받은 type 변수의 값에 따라 get, post, delete, patch의 형식별로 요청 객체를 정의합니다.
			case "POST":
				request = new Request.Builder()
				.addHeader("Authorization", token)  // 헤더를 추가합니다. (헤더이름, 값)
				.url(requestURL)  // 요청 url을 추가합니다.
				.post(RequestBody.create(jsonMessage, MediaType.parse("application/json; charset=utf-8"))) //POST로 요청합니다.
				.build();  // 요청을 작성합니다.
				break;
			case "DELETE":
				request = new Request.Builder()
				.addHeader("Authorization", token)
				.url(requestURL)
				.delete(RequestBody.create(jsonMessage, MediaType.parse("application/json; charset=utf-8"))) //DELETE로 요청합니다.
				.build();
				break;
			case "PATCH":
				request = new Request.Builder()
				.addHeader("Authorization", token)
				.url(requestURL)
				.patch(RequestBody.create(jsonMessage, MediaType.parse("application/json; charset=utf-8"))) //PATCH로 요청합니다.
				.build();
				break;
			default: //GET로 요청합니다.
				request = new Request.Builder()
				.addHeader("Authorization", token)
				.url(requestURL)
				.build();
				break;
			}
                        //동기 처리시 execute함수 사용
			Response response = client.newCall(request).execute();  // OkhttpClient 객체로 작성한 요청객체를 동기식으로 서버에 보낸 뒤 돌아온 응답을 저장합니다.
			
			this.isSuccessful = response.isSuccessful();  // 응답이 성공적으로 수신되었는지를 필드 변수에 저장합니다.

			//출력
			String message = response.body().string();  // json형식으로 돌아온 응답을 string형식으로 저장합니다.
			
			return message;  // string 형식으로 저장한 응답을 반환합니다.

		} catch (Exception e) {
			System.err.println(e.toString());
			this.isSuccessful = false;
			return "API request and response failed";
		}
	}public String getUserDetail() {  // 현재 사용자의 상세정보를 요청
		return this.request("GET", url+"users/"+this.id, null);
	}
	
	public String getOneRoom(int roomNum) {  // 특정 방의 정보를 요청
		return this.request("GET", url+"room/"+String.valueOf(roomNum), null);
	}
	
	public String getAllRoom(int roomNum) {  // 전체 방의 정보를 요청
		return this.request("GET", url+"room/", null);
	}
	
	public String postSign(String id, String pw, String name, int yNum, String email) {  // 회원가입을 요청
		JSONObject jo = new JSONObject();
		
		jo.put("userId", id);
		jo.put("password", pw);
		jo.put("name", name);
		jo.put("yjuNum", yNum);
		jo.put("email", email);
		return this.request("POST", url+"users/sign/", jo.toString());
	}
	
	public String postLogIn(String id, String pw) {  // 로그인을 요청 (로그인 성공시 사용자 정보를 필드변수에 저장)
		String result = this.request("POST", url+"users/", "{\"userId\":\""+id+"\",\"password\":\""+pw+"\"}");
		if (isSuccessful) {
			JSONObject jo = new JSONObject(result).getJSONObject("data");
			this.id = id;
			this.token = jo.getString("token");
			result = getUserDetail();
			jo = new JSONObject(result).getJSONObject("data");
			this.name = jo.getString("name");
			this.isAdmin = jo.getBoolean("isAdmin");
			this.yjuNum = jo.getInt("yjuNum");
			this.email = jo.getString("email");
		}
		return result;
	}
	
	public String postCreateRoom(int roomNum, int col, int row, int[] rowBlank, int[] colBlank) {  // 방을 생성하는 요청
		JSONObject jo = new JSONObject();
		
		jo.put("roomNum", roomNum);
		jo.put("column", col);
		jo.put("row", row);
		jo.put("rowBlankLine", Arrays.toString(rowBlank));
		jo.put("colBlankLine", Arrays.toString(colBlank));
		return this.request("POST", url+"room/", jo.toString());
	}
	
	public String postReserveRoom(int roomNum, int sitNum) {  // 자리를 예약하는 요청
		JSONObject jo = new JSONObject();
		
		jo.put("sitNum", sitNum);
		
		return this.request("POST", url+"room/"+roomNum+"/reserve", jo.toString());
	}
	
	public String patchResetDateRoom(int roomNum, Date resetDate) {  // 특정 방의 자리가 리셋되는 시간을 설정하는 요청
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS:SZ");
		JSONObject jo = new JSONObject();
		
		jo.put("roomNum", roomNum);
		jo.put("resetDate", df.format(resetDate));
		
		return this.request("PATCH", url+"room/"+roomNum+"/reset", jo.toString());
	}
	
	public String deleteReserveRoom(int roomNum, int sitNum) {  // 특정 방에 대해서 특정 자리의 예약을 위소하는 요청
		JSONObject jo = new JSONObject();
		
		
		jo.put("sitNum", sitNum);
		
		return this.request("DELETE", url+"room/"+roomNum+"/reserve", jo.toString());
	}
	
	public String deleteReserveRoom(int roomNum, String userId) {  // 특정 방에 대해서 특정 유저의 예약을 취소하는 요청
		JSONObject jo = new JSONObject();
		
		
		jo.put("userId", userId);
		
		return this.request("DELETE", url+"room/"+roomNum+"/reserve", jo.toString());
	}
	
	public String deleteReserveRoom(int roomNum) {  // 특정 방에 대해서 현재 사용자의 예약을 취소하는 요청
		return this.request("DELETE", url+"room/"+roomNum+"/reserve", null);
	}
	
	public void clearData() {  // 현재 저장하고 있는 사용자 정보를 삭제한다.
		this.id = "";
		this.token = "";
		this.email = null;
		this.name = null;
		this.isAdmin = false;
	}
	
	public boolean isLoggedIn() {
		if (token.isEmpty()) return false;
		return true;
	}
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public int getYjuNum() {
		return yjuNum;
	}
	public String getEmail() {
		return email;
	}
	
}
