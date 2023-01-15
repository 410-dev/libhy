package me.hy.libhyextended.drm;

import me.hy.libhyextended.request.HERequest;
import me.hy.libhyextended.request.HERequestParameter;

import java.io.IOException;

public class HEDRMClient {
    public static String verify(String serverURL, HEDRMObject object) throws IOException {
        return HERequest.request(serverURL, "POST", object.toToken(), new HERequestParameter[]{});
    }
}
