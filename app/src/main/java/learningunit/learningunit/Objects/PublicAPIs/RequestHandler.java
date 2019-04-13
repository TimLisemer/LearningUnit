package learningunit.learningunit.Objects.PublicAPIs;

import android.os.StrictMode;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;


public class RequestHandler {


    public String sendPostRequest(String requestURL, LinkedHashMap<String, String> postDataParams) {

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);


            Set set = postDataParams.entrySet();
            Iterator iterator = set.iterator();

            //Creating a URL
            String url1 = requestURL;
            URL url;

            while(iterator.hasNext()) {
                Map.Entry mentry = (Map.Entry) iterator.next();
                url1 = url1 + "&" + mentry.getKey() + "=" + mentry.getValue();
            }

            //StringBuilder object to store the message retrieved from the server
            StringBuilder sb = new StringBuilder();
            try {
                //Initializing Url
                url = new URL(url1);

                //Creating an httmlurl connection
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                //Configuring connection properties
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    sb = new StringBuilder();
                    String response;
                    //Reading server response
                    while ((response = br.readLine()) != null) {
                        sb.append(response);
                        Log.d("sendGetRequest", url + "   ----------------------------------------------------------------------------");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return sb.toString();

        }else{
            return "RequestHandler higher Android SDK needed";
        }


    }

    public String sendGetRequest(String requestURL) {
        Log.d("sendGetRequest", requestURL + "   ----------------------------------------------------------------------------");
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            StringBuilder sb = new StringBuilder();
            try {
                URL url = new URL(requestURL);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                String s;
                while ((s = bufferedReader.readLine()) != null) {
                    sb.append(s + "\n");
                }
            } catch (Exception e) {
            }
            return sb.toString();

        }else{
            return "RequestHandler higher Android SDK needed";
        }
    }




}