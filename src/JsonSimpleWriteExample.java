import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class JsonSimpleWriteExample {

    public static void main(String[] args) {

				//���ο� JSON ��ü�� ����ϴ�.
        JSONObject obj = new JSONObject();
				// JSONObject ��ü�� HashMap Ŭ������ �޼ҵ�� �����͸� �ֽ��ϴ�.
        obj.put("name", "mkyong.com");
        obj.put("age", 100);
				
				// key: value ������ �ʿ���ٸ� array ������ ���
        JSONArray list = new JSONArray();
				// array�� ��쿡�� add�� �ִ´�.
        list.add("msg 1");
        list.add("msg 2");
        list.add("msg 3");

				// JSON ��ü�ȿ� JSON array�� ��ü�� ���� �� �ִ�.
        obj.put("messages", list);

        try (FileWriter file = new FileWriter("c:\\projects\\test.json")) {
            file.write(obj.toJSONString());
				// JSONObject ��ü�� �޼ҵ��� toJSONStirng���� �Ϲ� Sting���� �ٲ��ݴϴ�.
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print(obj);
        
// ���
//{"name":"mkyong.com","messages":["msg 1","msg 2","msg 3"],"age":100}
    }

}