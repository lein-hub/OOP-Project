import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class JsonSimpleWriteExample {

    public static void main(String[] args) {

				//새로운 JSON 객체를 만듭니다.
        JSONObject obj = new JSONObject();
				// JSONObject 객체에 HashMap 클래스의 메소드로 데이터를 넣습니다.
        obj.put("name", "mkyong.com");
        obj.put("age", 100);
				
				// key: value 형식이 필요없다면 array 형식을 사용
        JSONArray list = new JSONArray();
				// array의 경우에는 add로 넣는다.
        list.add("msg 1");
        list.add("msg 2");
        list.add("msg 3");

				// JSON 객체안에 JSON array나 객체를 넣을 수 있다.
        obj.put("messages", list);

        try (FileWriter file = new FileWriter("c:\\projects\\test.json")) {
            file.write(obj.toJSONString());
				// JSONObject 객체의 메소드인 toJSONStirng으로 일반 Sting으로 바꿔줍니다.
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print(obj);
        
// 결과
//{"name":"mkyong.com","messages":["msg 1","msg 2","msg 3"],"age":100}
    }

}