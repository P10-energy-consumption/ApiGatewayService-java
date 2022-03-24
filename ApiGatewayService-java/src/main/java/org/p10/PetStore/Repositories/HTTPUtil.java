package org.p10.PetStore.Repositories;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HTTPUtil {
    public static HttpURLConnection getConnection(String requestUrl, String requestMethod) {
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(requestMethod);
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            return con;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void sendHTTPRequest(HttpURLConnection con, String request) {
        if (con != null) {
            try(OutputStream os = con.getOutputStream()) {
                byte[] input = request.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                con.disconnect();
            }
        } else {
            throw new RuntimeException();
        }
    }

    public static String getHTTPResponse(HttpURLConnection con) {
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
