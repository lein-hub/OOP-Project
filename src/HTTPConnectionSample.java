import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import java.io.FileWriter;
import java.io.IOException;

public class HTTPConnectionSample {
	public static String POST(String url, Person person){

        InputStream is = null;

        String result = "";

        try {

            URL urlCon = new URL(url);

            HttpURLConnection httpCon = (HttpURLConnection)urlCon.openConnection();



            String json = "";



            // build jsonObject

            JSONObject jsonObject = new JSONObject();

            jsonObject.accumulate("name", person.getName());

            jsonObject.accumulate("country", person.getCountry());

            jsonObject.accumulate("twitter", person.getTwitter());



            // convert JSONObject to JSON to String

            json = jsonObject.toString();



            // ** Alternative way to convert Person object to JSON string usin Jackson Lib

            // ObjectMapper mapper = new ObjectMapper();

            // json = mapper.writeValueAsString(person);



            // Set some headers to inform server about the type of the content

            httpCon.setRequestProperty("Accept", "application/json");

            httpCon.setRequestProperty("Content-type", "application/json");



            // OutputStream으로 POST 데이터를 넘겨주겠다는 옵션.

            httpCon.setDoOutput(true);

            // InputStream으로 서버로 부터 응답을 받겠다는 옵션.

            httpCon.setDoInput(true);



            OutputStream os = httpCon.getOutputStream();

            os.write(json.getBytes("euc-kr"));

            os.flush();

            // receive response as inputStream

            try {

                is = httpCon.getInputStream();

                // convert inputstream to string

                if(is != null)

                    result = convertInputStreamToString(is);

                else

                    result = "Did not work!";

            }

            catch (IOException e) {

                e.printStackTrace();

            }

            finally {

                httpCon.disconnect();

            }

        }

        catch (IOException e) {

            e.printStackTrace();

        }

        catch (Exception e) {

            Log.d("InputStream", e.getLocalizedMessage());

        }



        return result;

 

    }
}
