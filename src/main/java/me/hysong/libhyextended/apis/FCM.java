package me.hysong.libhyextended.apis;

import com.google.gson.JsonObject;
import me.hysong.libhyextended.request.HERequest;
import me.hysong.libhyextended.request.HERequestParameter;

import java.util.HashMap;

public class FCM {

    private String apiKey;

    public FCM(String apiKey) {
        this.apiKey = apiKey;
    }

    public String push(String to, JsonObject data) {
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
