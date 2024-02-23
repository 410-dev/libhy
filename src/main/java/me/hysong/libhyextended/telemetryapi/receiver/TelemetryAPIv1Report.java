package me.hysong.libhyextended.telemetryapi.receiver;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import me.hysong.libhycore.CoreBase64;
import me.hysong.libhyextended.objects.dataobj2.DataObject2;
import me.hysong.libhyextended.objects.dataobj2.JSONCodable;
import me.hysong.libhyextended.utils.ReadPOSTBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Getter
@JSONCodable
public class TelemetryAPIv1Report extends DataObject2 {

    private final int version = 1;
    private boolean silent;
    private long epoch;
    private String requestFrom;
    private String requestTo;
    private String timestamp;
    private String namespace;
    private String uniqueness;
    private String bodyRaw;
    private HashMap<String, String> body;

    public TelemetryAPIv1Report(HttpServletRequest request) {
        int version = TelemetryReportAnalyzer.getVersionOfReport(request);
        if (version != 1) {
            return;
        }

        String jsonEncoded = request.getParameter("params");
        jsonEncoded = CoreBase64.decode(jsonEncoded);
        JsonObject json = JsonParser.parseString(jsonEncoded).getAsJsonObject();
        silent = json.get("silent").getAsBoolean();
        epoch = json.get("epoch").getAsLong();
        requestFrom = request.getRemoteAddr();
        requestTo = request.getRequestURL().toString();
        timestamp = json.get("timestamp").getAsString();
        namespace = json.get("namespace").getAsString();
        uniqueness = json.get("uniqueness").getAsString();

        try {
            bodyRaw = ReadPOSTBody.readFromHttpServletRequestAsRawString(request);
            bodyRaw = bodyRaw.replaceAll("\\\\u(....)", "\\\\u$1");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            body = ReadPOSTBody.readFromHttpServletRequest(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
