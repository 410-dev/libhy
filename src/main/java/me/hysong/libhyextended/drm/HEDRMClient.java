package me.hysong.libhyextended.drm;

import me.hysong.libhyextended.request.Request;
import me.hysong.libhyextended.request.RequestParameter;

import java.io.IOException;

public class HEDRMClient {
    public static String verify(String serverURL, HEDRMObject object) throws IOException {
        return Request.request(serverURL, "POST", object.toToken(), new RequestParameter[]{});
    }
}
