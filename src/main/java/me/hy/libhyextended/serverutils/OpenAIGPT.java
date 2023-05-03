package me.hy.libhyextended.serverutils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.Setter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.util.ArrayList;
import java.util.Arrays;

public class OpenAIGPT {
    private final String API_KEY;
    @Setter private String API_URL = "https://api.openai.com/v1/chat/completions";
    @Setter private String MODEL = "gpt-3.5-turbo";
    @Setter private String initialRolePrompt = "You are a helpful assistant.";
    @Getter private String lastGPTResponse = "";
    @Getter private ArrayList<Message> messages = new ArrayList<>();

    @Getter
    @Setter
    public static class Message {
        public String role;
        public String content;
        public Message() {}
        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }
    public OpenAIGPT(String APIKey, String model, String initialRolePrompt) {
        this.API_KEY = APIKey;
        if (model != null) this.MODEL = model;
        if (initialRolePrompt != null) this.initialRolePrompt = initialRolePrompt;
    }

    public OpenAIGPT(String APIKey) {
        this(APIKey, null, null);
    }

    public String request(String ... question) {
        Message[] messages = new Message[question.length];
        for (int i = 0; i < question.length; i++) {
            question[i] = question[i].replace("\"", "\\\"");
            Message message = new Message();
            message.role = "user";
            message.content = question[i];
            messages[i] = message;
        }
        return request(messages);
    }
    public String request(Message[] messagesArray) {
        OkHttpClient client = new OkHttpClient();
        JsonObject body = new JsonObject();
        body.addProperty("model", MODEL);
        JsonArray messages = new JsonArray();
        JsonObject message1 = new JsonObject();
        message1.addProperty("role", "system");
        message1.addProperty("content", initialRolePrompt);
        messages.add(message1);
        for (Message message : messagesArray) {
            JsonObject messageObject = new JsonObject();
            messageObject.addProperty("role", message.role);
            messageObject.addProperty("content", message.content);
            messages.add(messageObject);
        }
        body.add("messages", messages);
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), body.toString());
        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + API_KEY)
                .post(requestBody)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                return null;
            }
            String s = response.body().string();
            JsonObject jsonResponse = JsonParser.parseString(s).getAsJsonObject();
            lastGPTResponse = jsonResponse.get("choices").getAsJsonArray().get(0).getAsJsonObject().get("message").getAsJsonObject().get("content").getAsString();
            this.messages.addAll(Arrays.asList(messagesArray));
            Message message = new Message();
            message.role = "assistant";
            message.content = lastGPTResponse;
            this.messages.add(message);
            return lastGPTResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}