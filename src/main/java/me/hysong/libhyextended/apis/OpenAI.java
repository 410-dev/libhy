package me.hysong.libhyextended.apis;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class OpenAI {

    public static class OpenAIGPTResponse {

        @Getter private JsonObject response;
        @Getter private String text;

        protected OpenAIGPTResponse(JsonObject response) {
            this.response = response;
            this.text = response.get("choices").getAsJsonArray().get(0).getAsJsonObject().get("message").getAsJsonObject().get("content").getAsString();
        }

    }

    private String apiKey;
    private String url;

    public OpenAI(String apiKey) {
        this(apiKey, "https://api.openai.com/v1/chat/completions");
    }

    public OpenAI(String apiKey, String url) {
        this.apiKey = apiKey;
        this.url = url;
    }

    public OpenAIGPTResponse generate(String prompt) throws Exception {
        return generate(prompt, "gpt-3.5-turbo");
    }

    public OpenAIGPTResponse generate(String prompt, String model) throws Exception {
        return generate(prompt, model, 0.7f);
    }

    public OpenAIGPTResponse generate(String prompt, String model, float temperature) throws IOException {
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", "Bearer " + apiKey);

        JsonObject message = new JsonObject();
        JsonArray history = new JsonArray();

        message.addProperty("role", "user");
        message.addProperty("content", prompt);
        history.add(message);

        JsonObject payload = new JsonObject();
        payload.addProperty("model", model);
        payload.add("messages", history);
        payload.addProperty("temperature", temperature);

        con.setDoOutput(true);
        con.getOutputStream().write(payload.toString().getBytes());

        String output = new BufferedReader(new InputStreamReader(con.getInputStream())).lines().reduce((a, b) -> a + b).get();
        System.out.println(output);

        return new OpenAIGPTResponse(JsonParser.parseString(output).getAsJsonObject());
    }

}
