package me.hysong.libhyextended.drm;

import me.hysong.libhyextended.request.HERequest;
import me.hysong.libhyextended.request.HERequestParameter;

import java.io.IOException;

public class HEDRMClient {
    public static String verify(String serverURL, HEDRMObject object) throws IOException {
        return HERequest.request(serverURL, "POST", object.toToken(), new HERequestParameter[]{});
    }
}
