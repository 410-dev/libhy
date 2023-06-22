package me.hysong.libhyextended.request;

import me.hysong.libhycore.CoreLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * Send HTTP requests.
 */
public class Request {

    private static String baseURL = "";

    /**
     * Send a request to the specified URL.
     * @param requestURL The URL to send the request to.
     * @param method The HTTP method to use.
     * @param body The body of the request.
     * @param parameters The parameters to append to the URL.
     * @return The response from the server.
     * @throws IOException If an error occurs while sending the request.
     */
    public static String request(String requestURL, String method, String body, RequestParameter[] parameters) throws IOException {
        return request(requestURL, method, new HashMap<>(0), body, parameters);
    }

    /**
     * Send a request to the specified URL.
     * @param requestURL The URL to send the request to.
     * @param method The HTTP method to use.
     * @param requestProperty The request properties to set.
     * @param body The body of the request.
     * @param parameters The parameters to append to the URL.
     * @return The response from the server.
     * @throws IOException If an error occurs while sending the request.
     */
    public static String request(String requestURL, String method, HashMap<String, String> requestProperty, String body, RequestParameter[] parameters) throws IOException {

        StringBuilder requestURLBuilder = new StringBuilder(requestURL);
        for (RequestParameter parameter : parameters) {
            requestURLBuilder.append(requestURLBuilder.toString().contains("?") ? "&" : "?").append(parameter.toString());
        }
        requestURL = baseURL + requestURLBuilder.toString();

        CoreLogger.debug(CoreLogger.EventType.INFO, "Request URL: " + requestURL);

        URL url = new URL(requestURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);
        for (String key : requestProperty.keySet()) {
            connection.setRequestProperty(key, requestProperty.get(key));
        }
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = body.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        StringBuilder response = new StringBuilder();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        }

        CoreLogger.debug(CoreLogger.EventType.INFO, "Response: " + response.toString());

        return response.toString();
    }

    public static void setBaseURL(String baseURL) {
        Request.baseURL = baseURL;
    }

    public static String getBaseURL() {
        return Request.baseURL;
    }
}
