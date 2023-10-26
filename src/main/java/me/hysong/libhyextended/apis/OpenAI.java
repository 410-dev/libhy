package me.hysong.libhyextended.apis;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * OpenAI API for GPT models
 */
public class OpenAI {

    /**
     * Response structure from OpenAI API
     */
    public static class OpenAIGPTResponse {

        /**
         * Response object from OpenAI API
         */
        @Getter private JsonObject response;

        /**
         * Actual text generated by GPT
         */
        @Getter private String text;
        protected OpenAIGPTResponse(JsonObject response) {
            this.response = response;
            this.text = response.get("choices").getAsJsonArray().get(0).getAsJsonObject().get("message").getAsJsonObject().get("content").getAsString();
        }

    }

    private String apiKey;
    private String url;
    @Getter private String lastRequest;
    @Getter @Setter private boolean lastRequestEnabled = false;
    @Getter @Setter private boolean enableRequestPrint = false;

    /**
     * Create a new OpenAI API instance with default URL (https://api.openai.com/v1/chat/completions)
     * @param apiKey OpenAI API key
     */
    public OpenAI(String apiKey) {
        this(apiKey, "https://api.openai.com/v1/chat/completions");
    }

    /**
     * Create a new OpenAI API instance
     * @param apiKey OpenAI API key
     * @param url OpenAI API URL
     */
    public OpenAI(String apiKey, String url) {
        this.apiKey = apiKey;
        this.url = url;
    }

    /**
     * Generate text using GPT-3.5-turbo model (Default temperature: 0.7)
     * @param prompt Prompt to generate text from
     * @return Response from OpenAI API
     * @throws IOException If something goes wrong
     */
    public OpenAIGPTResponse generate(String prompt) throws IOException {
        return generate(prompt, "gpt-3.5-turbo");
    }

    /**
     * Generate text using custom model name (Default temperature: 0.7)
     * @param prompt Prompt to generate text from
     * @param model Model to use for generation (Visit OpenAI official website for model names)
     * @return Response from OpenAI API
     * @throws IOException If something goes wrong
     */
    public OpenAIGPTResponse generate(String prompt, String model) throws IOException {
        return generate(prompt, model, 0.7f);
    }

    /**
     * Generate text using custom model name and temperature
     * @param prompt Prompt to generate text from
     * @param model Model to use for generation (Visit OpenAI official website for model names)
     * @param temperature Temperature to use for generation
     * @return Response from OpenAI API
     * @throws IOException If something goes wrong
     */
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

        if (lastRequestEnabled) {
            lastRequest = payload.toString();
        }

        String output = new BufferedReader(new InputStreamReader(con.getInputStream())).lines().reduce((a, b) -> a + b).get();

        if (enableRequestPrint) System.out.println(output);

        return new OpenAIGPTResponse(JsonParser.parseString(output).getAsJsonObject());
    }

}
