package com.example.guestbook;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Salman on 3/12/2016.
 */
public class RequestHandlerUtil {

    public static String executeRequest(String targetURL) throws IOException {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(targetURL);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoOutput(true);
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+
            String line;
            while((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();

        }  catch (Exception e) {
        e.printStackTrace();
        return null;
    } finally {
        if(connection != null) {
            connection.disconnect();
        }
    }


    }

}
