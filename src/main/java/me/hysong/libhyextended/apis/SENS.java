package me.hysong.libhyextended.apis;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.NonNull;
import lombok.Setter;
import me.hysong.libhyextended.UnpreparedCodeExecutionException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Setter
public class SENS {

    @NonNull
    private String accessKey;

    @NonNull
    private String secretKey;

    @NonNull
    private String serviceId;

    @NonNull
    private String from;

    public SENS(String accessKey, String secretKey, String serviceId, String from) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.serviceId = serviceId;
        this.from = from;
    }

    private void checkParameters() {
        if (accessKey == null) throw new UnpreparedCodeExecutionException("SENS.sendSMS()", "SENS.setAccessKey()");
        if (secretKey == null) throw new UnpreparedCodeExecutionException("SENS.sendSMS()", "SENS.setSecretKey()");
        if (serviceId == null) throw new UnpreparedCodeExecutionException("SENS.sendSMS()", "SENS.setServiceId()");
        if (from == null) throw new UnpreparedCodeExecutionException("SENS.sendSMS()", "SENS.setFrom()");
    }

    public void sendSMS(String to, String subject, String content, String countryCode) throws Exception {
        checkParameters();
        String hostNameUrl = "https://sens.apigw.ntruss.com";
        String requestUrl = "/sms/v2/services/";
        String requestUrlType = "/messages";
        String method = "POST";                                          // 요청 method
        String timestamp = Long.toString(System.currentTimeMillis());    // current timestamp (epoch)
        requestUrl += serviceId + requestUrlType;
        String apiUrl = hostNameUrl + requestUrl;

        // JSON 을 활용한 body data 생성

        JsonObject bodyJson = new JsonObject();
        JsonObject toJson = new JsonObject();
        JsonArray toArr = new JsonArray();

        toJson.addProperty("subject", subject);                // 메시지 제목 * LMS Type에서만 사용할 수 있습니다.
        toJson.addProperty("content", content);                // 메시지 내용 * Type별로 최대 byte 제한이 다릅니다.* SMS: 80byte / LMS: 2000byte
        toJson.addProperty("to", to);                        // 수신번호 목록  * 최대 50개까지 한번에 전송할 수 있습니다.
        toArr.add(toJson);

        bodyJson.addProperty("type", "sms");                // 메시지 Type (sms | lms)
        bodyJson.addProperty("contentType", "COMM");        // 메시지 내용 Type (AD | COMM) * AD: 광고용, COMM: 일반용 (default: COMM) * 광고용 메시지 발송 시 불법 스팸 방지를 위한 정보통신망법 (제 50조)가 적용됩니다.
        bodyJson.addProperty("countryCode", countryCode);        // 국가 전화번호
        bodyJson.addProperty("from", from);            // 발신번호 * 사전에 인증/등록된 번호만 사용할 수 있습니다.
        bodyJson.addProperty("subject", "");                // 메시지 제목 * LMS Type에서만 사용할 수 있습니다.
        bodyJson.addProperty("content", content);                // 메시지 내용 * Type별로 최대 byte 제한이 다릅니다.* SMS: 80byte / LMS: 2000byte
        bodyJson.add("messages", toArr);


        String body = bodyJson.toString();

        URL url = new URL(apiUrl);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setUseCaches(false);
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestProperty("content-type", "application/json");
        con.setRequestProperty("x-ncp-apigw-timestamp", timestamp);
        con.setRequestProperty("x-ncp-iam-access-key", accessKey);
        con.setRequestProperty("x-ncp-apigw-signature-v2", makeSignature(requestUrl, timestamp, method, accessKey, secretKey));
        con.setRequestMethod(method);
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());

        wr.write(body.getBytes());
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        BufferedReader br;
        if (responseCode == 202) { // 정상 호출
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } else {  // 에러 발생
            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        }

        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine);
        }
        br.close();

        if (responseCode != 202) {
            throw new Exception(response.toString());
        }
    }

    private String makeSignature(String url, String timestamp, String method, String accessKey, String secretKey) throws NoSuchAlgorithmException, InvalidKeyException {
        String space = " ";                    // one space
        String newLine = "\n";                 // new line


        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(timestamp)
                .append(newLine)
                .append(accessKey)
                .toString();

        SecretKeySpec signingKey;
        String encodeBase64String;

        signingKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));
        encodeBase64String = Base64.getEncoder().encodeToString(rawHmac);


        return encodeBase64String;
    }
}
