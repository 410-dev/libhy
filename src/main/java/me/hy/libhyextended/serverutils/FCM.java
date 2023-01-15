package me.hy.libhyextended.serverutils;

import com.google.gson.JsonObject;
import me.hy.libhyextended.request.HERequest;
import me.hy.libhyextended.request.HERequestParameter;

import java.util.HashMap;

public class FCM {
    public static String push(String to, JsonObject data, String apiKey) {
        try {
            HashMap<String, String> properties = new HashMap<>();
            properties.put("Content-Type", "application/json");
            properties.put("Authorization", "key=" + apiKey);

            JsonObject body = new JsonObject();
            body.addProperty("to", to);
            body.add("data", data);
            body.add("notification", data);

            return HERequest.request(
                    "https://fcm.googleapis.com/fcm/send",
                    "POST",
                    properties,
                    body.toString(),
                    new HERequestParameter[]{});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
