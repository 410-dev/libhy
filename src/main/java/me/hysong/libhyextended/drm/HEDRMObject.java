package me.hysong.libhyextended.drm;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.Setter;
import me.hysong.libhyextended.UnsupportedOperatingSystemException;
import me.hysong.libhycore.CoreAES;
import me.hysong.libhycore.CoreDate;
import me.hysong.libhycore.CoreSHA;

import java.io.*;
import java.util.Objects;

@Setter
@Getter
public class HEDRMObject {

    private static String sharedSecret = "";

    private long seed;
    private long diff;
    private final String deviceUniqueKey;
    private String hashedPassword;

    public HEDRMObject(String deviceUniqueKey, long seed, long diff) {
        this.deviceUniqueKey = deviceUniqueKey;
        this.seed = getIgnoredUnixEpochTime(seed);
        this.diff = diff;
    }

    public HEDRMObject(String seedLocation) throws IOException, UnsupportedOperatingSystemException, InterruptedException {
        deviceUniqueKey = HEMachineIdentification.getMachineUnique();
        BufferedReader reader = new BufferedReader(new FileReader(seedLocation));
        String line = reader.readLine();
        reader.close();
        seed = Long.parseLong(line);
        diff = getIgnoredUnixEpochTime(CoreDate.secondsSince1970()) - seed;
        updateHashedPassword();
    }

    public HEDRMObject(long seed) throws UnsupportedOperatingSystemException, InterruptedException, IOException {
        deviceUniqueKey = HEMachineIdentification.getMachineUnique();
        this.seed = getIgnoredUnixEpochTime(seed);
        this.diff = getIgnoredUnixEpochTime(CoreDate.secondsSince1970()) - this.seed;
        updateHashedPassword();
    }

    public HEDRMObject(String deviceUniqueKey, String hashedPassword) {
        this.deviceUniqueKey = deviceUniqueKey;
        this.hashedPassword = hashedPassword;
    }

    public HEDRMObject(String deviceUniqueKey, long seed) {
        this(
                deviceUniqueKey,
                seed,
                getIgnoredUnixEpochTime(CoreDate.secondsSince1970()) - seed);
        updateHashedPassword();
    }

    public HEDRMObject() {
        try {
            deviceUniqueKey = HEMachineIdentification.getMachineUnique();
            seed = getIgnoredUnixEpochTime(CoreDate.secondsSince1970());
            diff = 0;
            updateHashedPassword();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void updateHashedPassword() {
        if (diff == 0) return;
        hashedPassword = CoreSHA.hash512(deviceUniqueKey + seed + diff, deviceUniqueKey);
        diff = 0;
    }

    private JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("machine", this.deviceUniqueKey);
        jsonObject.addProperty("pass", this.hashedPassword);
        return jsonObject;
    }

    public static HEDRMObject fromToken(String token) {
        JsonObject object = null;
        try {
            object = JsonParser.parseString(CoreAES.decrypt(token, sharedSecret)).getAsJsonObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        return new HEDRMObject(
                object.get("machine").getAsString(),
                object.get("pass").getAsString()
        );
    }

    public String toToken() {
        if (sharedSecret.equals("")) {
            throw new SharedSecretNotSetException();
        }

        try {
            return CoreAES.encrypt(this.toJson().toString(), sharedSecret);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean check(HEDRMObject object) {
        if (object == null) {
            return false;
        }
        if (!this.deviceUniqueKey.equals(object.deviceUniqueKey)) {
            return false;
        }
        return Objects.equals(this.hashedPassword, object.hashedPassword);
    }

    private static long getIgnoredUnixEpochTime(long seed) {
        String epochTime = String.valueOf(seed);
        if (epochTime.length() < 10) {
            epochTime = "1665755813";
        }
        return Long.parseLong(epochTime.substring(0, epochTime.length() - 2) + "00");
    }

    public static void setSharedSecret(String sharedSecret) {
        HEDRMObject.sharedSecret = sharedSecret;
    }

    public boolean saveSeed(String at) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(at));
        bw.write(String.valueOf(this.seed));
        bw.close();
        return new File(at).isFile();
    }
}
