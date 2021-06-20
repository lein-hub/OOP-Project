package com.anjava;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.json.JSONArray;
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
	private String name = null;  // 사용자의 이름이 저장됩니다.
	private boolean isAdmin = false;  // 사용자의 관리자 여부가 저장됩니다.
	private int yjuNum = 0;  // 사용자의 학번이 저장됩니다.
	private String email = null;  // 사용자의 이메일이 저장됩니다.
	private JSONArray reservedRooms = null;  // 사용자가 예약한 방 목록이 저장됩니다.
	
	private boolean isSuccessful = false;  // 요청이 수행될 때 마다 성공적으로 응답을 수신하였는지 저장됩니다.
	
	private String request(String type, String requestURL, String jsonMessage) {
		try{
			OkHttpClient client = new OkHttpClient();  // OkHttpClient 객체를 생성합니다.+
			
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
	}
	/**
	 * 현재 사용자의 정보를 요청합니다.<br>
	 * @return 현재 사용자의 정보가 담긴 JSON 객체에 대한 String 데이터를 반환합니다.
	 * <pre>
	 * {
  "message": "{userId}님의 정보 입니다.",
  "data": {
    "isAdmin": boolean,
    "userId": "userId",
    "name": "userName",
    "email": "test@email.com",
    "yjuNum": 1234567,
    "reservedRooms": [
      {
        "sitNum": 1,
        "roomNum": 1,
        "reserveDate": "ISO 8601형식의 String입니다. Timezone은 UTC 0인 Z입니다."
      },
      {
        "sitNum": 99,
        "roomNum": 33,
        "reserveDate": "YYYY-MM-DDThh:mm:ss.sssZ"
      }
    ]
  }
}
	 * </pre>
	 */
	public String getUserDetail() {  // 현재 사용자의 상세정보를 요청
		return this.request("GET", url+"users/"+this.id, null);
	}
	/**
	 * 특정 사용자의 정보를 조회합니다.<br>
	 * 관리자만 사용 가능합니다.
	 * @param userId 는 조회할 사용자의 ID입니다.
	 * @return 매개변수 userID 라는 ID의 사용자 정보가 담긴 JSON 객체에 대한 String 데이터를 반환합니다.
	 * <pre>
	 * {
  "message": "{userId}님의 정보 입니다.",
  "data": {
    "isAdmin": boolean,
    "userId": "userId",
    "name": "userName",
    "email": "test@email.com",
    "yjuNum": 1234567,
    "reservedRooms": [
      {
        "sitNum": 1,
        "roomNum": 1,
        "reserveDate": "ISO 8601형식의 String입니다. Timezone은 UTC 0인 Z입니다."
      },
      {
        "sitNum": 99,
        "roomNum": 33,
        "reserveDate": "YYYY-MM-DDThh:mm:ss.sssZ"
      }
    ]
  }
}
	 * </pre>
	 */
	public String getUserDetail(String userId) {  // 특정 사용자의 상세정보를 요청
		return this.request("GET", url+"users/"+userId, null);
	}
	/**
	 * 특정 방의 상세한 정보를 조회합니다.<br>
	 * @param roomNum 은 조회할 방의 호수입니다.
	 * @return 매개변수 roomNum 이라는 호수의 방 정보가 담긴 JSON 객체에 대한 String 데이터를 반환합니다.
	 * <pre>
	 * {
  "message": "{roomNum} 방의 정보",
  "data": {
    "roomData": {
      "row": 10,
      "column": 8,
      "rowBlankLine": [
        3,
        6
      ],
      "columnBlankLine": [
        4,
        8
      ],
      "resetDate": "",
      "reservedData": { // sitNum 오름차순으로 정렬합니다.
        "16": "aio",
        "19": "forbidden" // 예약이 금지된 좌석입니다.
      }
    }
  }
}
	 * </pre>
	 */
	public String getOneRoom(int roomNum) {  // 특정 방의 정보를 요청
		return this.request("GET", url+"room/"+String.valueOf(roomNum), null);
	}
	/**
	 * 모든 방에 대한 정보를 조회합니다.<br>
	 * @return 모든 방의 대략적인 정보가 담긴 JSON 객체에 대한 String 데이터를 반환합니다.
	 * <pre>{
  "message": "모든 방 리스트입니다.",
  "data": {
    "roomsData": [
      {
        "roomNum": 202,
        "maxSit": 80,
        "resetDate": "ISO 8601 // 존재하지 않다면 요청 결과에 표기되지 않습니다."
      },
      {
        "roomNum": 203,
        "maxSit": 80
      }
    ]
  }
}</pre>
	 */
	public String getAllRoom() {  // 전체 방의 정보를 요청
		return this.request("GET", url+"room/", null);
	}
	/**
	 * 새로운 유저를 등록합니다.<br>
	 * @param id 는 등록시 사용할 ID입니다.
	 * @param pw 는 등록시 사용할 비밀번호입니다.
	 * @param name 는 등록시 사용할 이름입니다.
	 * @param yNum 는 등록시 사용할 학번입니다.
	 * @param email 는 등록시 사용할 이메일 주소입니다.
	 * @return 회원 가입시 기입한 정보들이 담긴 JSON 객체에 대한 String 데이터를 반환합니다. 오류 발생시 오류 메시지를 반환합니다.
	 * <pre>{
    "userId":"user의 아이디" // uniuqe ,
    "password":"비밀번호" // min 8,
    "name":"한글 성함" ,
    "yjuNum":"학생번호" // unique 7자리의 숫자형식,
    "email":"이메일" // unique  email 형식이여야 합니다.
}</pre>
	 */
	public String postSign(String id, char[] pw, String name, int yNum, String email) {  // 회원가입을 요청
		JSONObject jo = new JSONObject();
		String password = "";
		for (char a : pw) password += a;
		jo.put("userId", id);
		jo.put("password", password);
		jo.put("name", name);
		jo.put("yjuNum", yNum);
		jo.put("email", email);
		return this.request("POST", url+"users/sign/", jo.toString());
	}
	/**
	 * 로그인 합니다.<br>
	 * @param id 는 로그인할 아이디입니다.
	 * @param pw 는 로그인할 계정에 대한 비밀번호입니다.
	 * @return 요청 결과 메시지와 함께 토큰과 관리자 여부가 정보가 담긴 JSON 객체에 대한 String 데이터를 반환합니다.
	 * <pre>{
  "message": "userId님 로그인 성공",
  "data": {
    "token": "jwt string입니다.",
    "isAdmin": boolean
  }
}</pre>
	 */
	public String postLogIn(String id, char[] pw) {  // 로그인을 요청 (로그인 성공시 사용자 정보를 필드변수에 저장)
		String password = "";
		for (char a : pw) password += a;
		String result = this.request("POST", url+"users/", "{\"userId\":\""+id+"\",\"password\":\""+password+"\"}");
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
			this.reservedRooms = jo.getJSONArray("reservedRooms");
		}
		return result;
	}
	/**
	 * 새로운 Room을 만듭니다.<br>
	 * 관리자만 사용 가능합니다.
	 * @param roomNum 은 새로 만들 방 호수입니다.
	 * @param col 은 좌석 열갯수 입니다.
	 * @param row 는 좌석 행갯수 입니다.
	 * @param colBlank 은 띄울 열번호입니다. 띄우지 않으려면 null을 입력하세요.
	 * @param rowBlank 은 띄울 행번호입니다. 띄우지 않으려면 null을 입력하세요.
	 * @return <pre>{
    "roomNum": 방의 번호 int,
    "column": 세로줄 int,
    "row": 가로줄 int,
    "rowBlankLine": [
        1
    ],
    "columnBlankLine": [
        3,
        4,
    8 ...
    ]
}</pre>
	 */
	public String postCreateRoom(int roomNum, int col, int row, int[] colBlank, int[] rowBlank) {  // 방을 생성하는 요청
		JSONObject jo = new JSONObject();
		jo.put("roomNum", roomNum);
		jo.put("column", col);
		jo.put("row", row);
		if (rowBlank != null)
		jo.put("rowBlankLine", rowBlank);
		if (colBlank != null)
		jo.put("columnBlankLine", colBlank);
		System.out.println(jo.toString());
		return this.request("POST", url+"room/", jo.toString());
	}
	
	public String postCreateRoom(RoomCreator rc) {  // 방을 생성하는 요청
		JSONObject jo = new JSONObject();
	      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
	      
	      
	      if (rc.getRoomNum() != 99999)
	      jo.put("roomNum", rc.getRoomNum());
	      if (rc.getCol() != 99)
	      jo.put("column", rc.getCol());
	      if (rc.getRow() != 99)
	      jo.put("row", rc.getRow());
	      if (rc.getRowBlank() != null)
	         jo.put("rowBlankLine", rc.getRowBlank());
	      if (rc.getColBlank() != null)
	         jo.put("columnBlankLine", rc.getColBlank());
	      if (rc.getResetDate() != null)
	         jo.put("resetDate", df.format(rc.getResetDate()));
	      if (rc.getAcceptDate() != null)
	         jo.put("acceptDate", df.format(rc.getAcceptDate()));
	      if (rc.getMeasure() == 0) {
	         jo.put("measure", rc.getMeasure());
	         jo.put("weekendInterval", rc.getWeekendInterval());
	      }
	      if (rc.getMeasure() == 1) {
	         jo.put("measure", rc.getMeasure());
	         jo.put("day", rc.getDay());
	         jo.put("weekNth", rc.getWeekNth());
	      }
	      if (rc.getOpenDeffer() != 9999)
	      jo.put("openDeffer", rc.getOpenDeffer());
	      
	      jo.put("isShuffle", rc.isShuffle());
	      System.out.println(jo.toString());
	      return this.request("POST", url+"room/", jo.toString());
	}
	/**
	 * 새로운 Room을 만듭니다.<br>
	 * 관리자만 사용 가능합니다.
	 * @param roomNum 은 새로 만들 방 호수입니다.
	 * @param col 은 좌석 열갯수 입니다.
	 * @param row 는 좌석 행갯수 입니다.
	 * @return <pre>{
    "roomNum": 방의 번호 int,
    "column": 세로줄 int,
    "row": 가로줄 int,
    "rowBlankLine": [
        1
    ],
    "columnBlankLine": [
        3,
        4,
    8 ...
    ]
}</pre>
	 */
	public String postCreateRoom(int roomNum, int col, int row) {  // 방을 생성하는 요청
		JSONObject jo = new JSONObject();
		
		jo.put("roomNum", roomNum);
		jo.put("column", col);
		jo.put("row", row);
		return this.request("POST", url+"room/", jo.toString());
	}
	/**
	 * roomNum번 방의 특정 좌석을 예약합니다.<br>
	 * @param roomNum 은 좌석을 예약할 방의 호수입니다.
	 * @param sitNum 은 예약할 좌석 번호입니다.
	 * @return 요청 결과 메시지가 반환됩니다.
	 */
	public String postReserveRoom(int roomNum, int sitNum) {  // 자리를 예약하는 요청
		JSONObject jo = new JSONObject();
		
		jo.put("sitNum", sitNum);
		
		return this.request("POST", url+"room/"+roomNum+"/reserve", jo.toString());
	}
	/**
	 * 특정 사용자를 roomNum번 방의 특정 좌석에 예약시킵니다.<br>
	 * 관리자만 사용 가능합니다.
	 * @param userId 는 예약시킬 사용자 아이디입니다.
	 * @param roomNum 은 좌석을 예약할 방의 호수입니다.
	 * @param sitNum 은 예약할 좌석 번호입니다.
	 * @return 요청 결과 메시지가 반환됩니다.
	 */
	public String postReserveRoom(String userId, int roomNum, int sitNum) {  // 자리를 예약하는 요청
		JSONObject jo = new JSONObject();
		jo.put("userId", userId);
		jo.put("sitNum", sitNum);
		
		return this.request("POST", url+"room/"+roomNum+"/reserve", jo.toString());
	}
	
	public String patchResetRoomIntervalWeek(int roomNum, int weekendInterval, int openDeffer) {  // 특정 방의 예약이 가능해지는 시간을 설정하는 요청
		JSONObject jo = new JSONObject();
		jo.put("measure", 0);
		jo.put("weekendInterval", weekendInterval);
		jo.put("openDeffer", openDeffer);
		return this.request("PATCH", url+"room/"+roomNum, jo.toString());
	}
	
	public String patchOneRoom(RoomCreator rc) {
		JSONObject jo = new JSONObject();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		if (rc.getCol() != 99)
			jo.put("column", rc.getCol());
		if (rc.getRow() != 99)
			jo.put("row", rc.getRow());
		if (rc.getRowBlank() != null)
			jo.put("rowBlankLine", rc.getRowBlank());
		if (rc.getColBlank() != null)
			jo.put("columnBlankLine", rc.getColBlank());
		
		jo.put("resetDate", df.format(rc.getResetDate()));
		jo.put("acceptDate", df.format(rc.getAcceptDate()));
		
		if (rc.getMeasure() == 0) {
			jo.put("measure", rc.getMeasure());
			jo.put("weekendInterval", rc.getWeekendInterval());
		}
		if (rc.getMeasure() == 1) {
			jo.put("measure", rc.getMeasure());
			jo.put("day", rc.getDay());
			jo.put("weekNth", rc.getWeekNth());
		}
		if (rc.getMeasure() == -1) {
			jo.put("measure", rc.getMeasure());
		}
	    if (rc.getOpenDeffer() != 9999) {
	    	jo.put("openDeffer", rc.getOpenDeffer());
	    }
		jo.put("isShuffle", rc.isShuffle());

		return this.request("PATCH", url+"room/"+rc.getRoomNum(), jo.toString());
	}
	
	public String patchResetRoomIntervalDay(int roomNum, int day, int weekNth, int openDeffer) {  // 특정 방의 예약이 가능해지는 시간을 설정하는 요청
		JSONObject jo = new JSONObject();
		jo.put("measure", 1);
		jo.put("day", day);
		jo.put("weekNth", weekNth);
		jo.put("openDeffer", openDeffer);
		return this.request("PATCH", url+"room/"+roomNum, jo.toString());
	}
	/**
	 * roomNum방에 acceptDate를 등록하거나 갱신합니다.<br>
	 * acceptDate없이 보내면 acceptDate가 삭제됩니다.<br>
	 * 관리자만 사용 가능합니다.
	 * @param roomNum 은 리셋할 날짜를 수정할 방 호수입니다.
	 * @param resetDate 리셋할 날짜 객체입니다.
	 * @return 요청 결과 메시지가 반환됩니다.
	 */
	public String patchAcceptDateRoom(int roomNum, Date acceptDate) {  // 특정 방의 예약이 가능해지는 시간을 설정하는 요청
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		JSONObject jo = new JSONObject();
		
		if (acceptDate != null)
		jo.put("acceptDate", df.format(acceptDate));
		System.out.println(jo.toString());
		
		return this.request("PATCH", url+"room/"+roomNum+"/accept", jo.toString());
	}
	/**
	 * roomNum방에 resetDate를 등록하거나 갱신합니다.<br>
	 * resetDate없이 보내면 resetDate가 삭제됩니다.<br>
	 * 관리자만 사용 가능합니다.
	 * @param roomNum 은 리셋할 날짜를 수정할 방 호수입니다.
	 * @param resetDate 리셋할 날짜 객체입니다.
	 * @return 요청 결과 메시지가 반환됩니다.
	 */
	public String patchResetDateRoom(int roomNum, Date resetDate) {  // 특정 방의 자리가 리셋되는 시간을 설정하는 요청
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		JSONObject jo = new JSONObject();
		
		if (resetDate != null)
		jo.put("resetDate", df.format(resetDate));
		System.out.println(jo.toString());
		
		return this.request("PATCH", url+"room/"+roomNum+"/reset", jo.toString());
	}
	
	public String patchAcceptDateAfterResetRoom(int roomNum, Date resetDate, Date ADAR) {  // 특정 방의 자리가 리셋되는 시간을 설정하는 요청
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		JSONObject jo = new JSONObject();
		
		if (resetDate != null)
		jo.put("resetDate", df.format(resetDate));
		jo.put("acceptDateAfterReset", df.format(ADAR));
		System.out.println(jo.toString());
		
		return this.request("PATCH", url+"room/"+roomNum, jo.toString());
	}
	/**
	 * 현재 사용자의 roomNum방의 특정 좌석에 대한 예약을 취소합니다.
	 * @param roomNum 은 예약을 취소할 좌석이 있는 방 호수입니다.
	 * @param sitNum 은 예약을 취소할 좌석 번호입니다.
	 * @return 요청 결과 메시지가 반환됩니다.
	 */
	public String deleteReserveRoom(int roomNum, int sitNum) {  // 특정 방에 대해서 특정 자리의 예약을 위소하는 요청
		JSONObject jo = new JSONObject();
		
		
		jo.put("sitNum", sitNum);
		
		return this.request("DELETE", url+"room/"+roomNum+"/reserve", jo.toString());
	}
	/**
	 * 특정 유저의 roomNum방의 특정 좌석에 대한 예약을 취소합니다.<br>
	 * 관리자만 사용 가능합니다.
	 * @param userId 는 예약을 취소할 사용자 아이디입니다.
	 * @param roomNum 은 예약을 취소할 좌석이 있는 방 호수입니다.
	 * @param sitNum 은 예약을 취소할 좌석 번호입니다.
	 * @return 요청 결과 메시지가 반환됩니다.
	 */
	public String deleteReserveRoom(String userId, int roomNum, int sitNum) {  // 특정 방에 대해서 특정 유저의 예약을 취소하는 요청
		JSONObject jo = new JSONObject();
		
		jo.put("userId", userId);
		jo.put("sitNum", sitNum);
		System.out.println(isAdmin);
		System.out.println(token);
		System.out.println(jo.toString());
		return this.request("DELETE", url+"room/"+roomNum+"/reserve", jo.toString());
	}
	/**
	 * 현재 사용자의 roomNum방에 대한 예약을 취소합니다.
	 * @param roomNum 은 예약을 취소할 좌석이 있는 방 호수입니다.
	 * @param sitNum 은 예약을 취소할 좌석 번호입니다.
	 * @return 요청 결과 메시지가 반환됩니다.
	 */
	public String deleteReserveRoom(int roomNum) {  // 특정 방에 대해서 현재 사용자의 예약을 취소하는 요청
		return this.request("DELETE", url+"room/"+roomNum+"/reserve", "");
	}
	/**
	 * 방을 삭제하면서, 이 방과 관련된 유저들의 예약들도 삭제 됩니다.<br>
	 * 관리자만 사용 가능합니다.
	 * @param roomNum 는 삭제할 방 호수입니다.
	 * @return 요청 결과 메시지가 반환됩니다.
	 */
	public String deleteRoom(int roomNum) {
		
		return this.request("DELETE", url+"room/"+roomNum, "");
	}
	
	
	public String patchGrantAdmin(String userId) {
		JSONObject jo = new JSONObject();
		
		jo.put("userId", userId);
		return this.request("PATCH", url+"users/grantAdmin", jo.toString());
	}
	/**
	 * 현재 이 객체에서 저장하고 있는 사용자 정보를 전부 삭제합니다.
	 */
	public void clearData() {  // 현재 저장하고 있는 사용자 정보를 삭제한다.
		this.id = "";
		this.token = "";
		this.email = null;
		this.name = null;
		this.isAdmin = false;
		this.reservedRooms = null;
		this.yjuNum = 0;
	}
	/**
	 * 현재 로그인 여부를 반환합니다.
	 * @return 로그인 되어 있을 시 true, 아닐 시 false
	 */
	public boolean isLoggedIn() {
		if (token.isEmpty()) return false;
		return true;
	}
	/**
	 * 현재 로그인 되어 있는 유저의 ID를 반환합니다.
	 * @return 로그인 되어 있지 않으면 공백을 반환합니다.
	 */
	public String getId() {
		return id;
	}
	/**
	 * 현재 로그인 되어 있는 유저의 이름을 반환합니다.
	 * @return 로그인 되어 있지 않으면 null을 반환합니다.
	 */
	public String getName() {
		return name;
	}
	/**
	 * 현재 로그인 되어 있는 유저의 관리자 여부를 반환합니다.
	 * @return 관리자일 시 true, 아니거나 로그인 되어있지 않을 시 false
	 */
	public boolean isAdmin() {
		return isAdmin;
	}
	/**
	 * 현재 로그인 되어 있는 유저의 학번을 반환합니다.
	 * @return 로그인 되어 있지 않으면 0을 반환합니다.
	 */
	public int getYjuNum() {
		return yjuNum;
	}
	/**
	 * 현재 로그인 되어 있는 유저의 이메일을 반환합니다.
	 * @return 로그인 되어 있지 않으면 null을 반환합니다.
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * 현재 로그인 되어 있는 유저가 예약한 방 목록이 반환됩니다.
	 * @return 로그인 되어 있지 않으면 null을 반환합니다.
	 */
	public JSONArray getReservedRooms() {
		return reservedRooms;
	}
	
}
