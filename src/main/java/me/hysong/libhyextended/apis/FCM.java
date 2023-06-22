package me.hysong.libhyextended.apis;

import com.google.gson.JsonObject;
import me.hysong.libhyextended.request.Request;
import me.hysong.libhyextended.request.RequestParameter;

import java.util.HashMap;

/**
 * Google Firebase Cloud Messaging API for push notification
 */
public class FCM {

    /**
     * Push message to a device using FCM
     * @param to Device token
     * @param data Data to send
     * @param apiKey API key
     * @return Response from FCM
     */
    public static String push(String to, JsonObject data, String apiKey) {
        try {
            HashMap<String, String> properties = new HashMap<>();
            properties.put("Content-Type", "application/json");
            properties.put("Authorization", "key=" + apiKey);

            JsonObject body = new JsonObject();
            body.addProperty("to", to);
            body.add("data", data);
            body.add("notification", data);

            return Request.request(
                    "https://fcm.googleapis.com/fcm/send",
                    "POST",
                    properties,
                    body.toString(),
                    new RequestParameter[]{});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
