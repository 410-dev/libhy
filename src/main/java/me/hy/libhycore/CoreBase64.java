package me.hy.libhycore;

import java.nio.charset.StandardCharsets;
import java.util.Base64;


/**
 * Simple base64 encoding and decoding.
 */
public class CoreBase64 {


    /**
     * @param toEncode: String to encode
     * @return Base64 encoded string
     */
    public static String encode(String toEncode) {
        byte[] utf8StringBuffer = toEncode.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(utf8StringBuffer);
    }


    /**
     * @param toDecode: Base64 encoded string
     * @return Decoded string
     */
    public static String decode(String toDecode) {
        byte[] utf8StringBuffer = Base64.getDecoder().decode(toDecode.getBytes(StandardCharsets.UTF_8));
        return new String(utf8StringBuffer, StandardCharsets.UTF_8);
    }

    /**
     * @deprecated
     * Do not use this constructor. It does not have any functionality.
     */
    @Deprecated
    public CoreBase64() {}
}