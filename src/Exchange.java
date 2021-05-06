import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.IOException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Exchange {
//    private static final String HOST_URL = "http://anjava.aio392.com/users";
//
//    public void get() {
//        HttpURLConnection conn = null;
//        JSONObject responseJson = null;
//
//        try {
//            URL url = new URL(HOST_URL);
//
//            conn = (HttpURLConnection)url.openConnection();
//            conn.setConnectTimeout(5000);
//            conn.setReadTimeout(5000);
//            conn.setRequestMethod("GET");
//            //conn.setDoOutput(true);
//
//            JSONObject commands = new JSONObject();
//
//            int responseCode = conn.getResponseCode();
//            if (responseCode == 400 || responseCode == 401 || responseCode == 500 ) {
//                System.out.println(responseCode + " Error!");
//            } else {
//                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                StringBuilder sb = new StringBuilder();
//                String line = "";
//                while ((line = br.readLine()) != null) {
//                    sb.append(line);
//                }
//                responseJson = new JSONObject(sb.toString());
//                System.out.println(responseJson);
//            }
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            System.out.println("not JSON Format response");
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
	
	public static void sendPostJson() throws Exception {
		//JSON 데이터 받을 URL 객체 생성
		URL url = new URL ("http://anjava.aio392.com/users/");
		//HttpURLConnection 객체를 생성해 openConnection 메소드로 url연결
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		//전송방식 (POST)
		con.setRequestMethod("POST");
		//application/json 형식으로 전송, Request body를 JSON으로 던져줌.
		con.setRequestProperty("Content-Type", "application/json; utf-8");
		//Response data를 JSON으로 받도록 설정
		con.setRequestProperty("Accept", "application/json");
		//Output Stream을 POST 데이터로 전송
		con.setDoOutput(true);
		//json data
		String jsonInputString = "{" + "userId" + ":" + "aio" + "," + "password" + ":" + "12341234" + "}";
		
		//JSON 보내는 Output stream
		try (OutputStream os = con.getOutputStream()) {
			byte[] input = jsonInputString.getBytes("utf-8");
			os.write(input, 0, input.length);
		}
		
		//Response data 받는 부분
		try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
			StringBuilder response = new StringBuilder();
			String responseLine = null;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}
			System.out.println(response.toString());
		}
	}
	
	public static void sendGetJson() throws Exception {
		URL url = new URL ("https://webhook.site/f24be93b-80c6-4012-8e37-fde19f81f585");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Content-Type", "application/json; utf-8");
		con.setRequestProperty("Accept", "application/json");
		
		con.setRequestProperty("userId", "aio");
		con.setRequestProperty("password", "12341234");
		
		try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
			StringBuilder response = new StringBuilder();
			String responseLine = null;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}
			System.out.println(response.toString());
		}
	}
	
	public static void main(String[] args) {
		try {
			sendPostJson();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}