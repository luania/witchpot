package com.luania.witchpot.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by luania on 16/6/27.
 */
public class EnDecoderUtil {

    public static final String UTF_8 = "utf-8";

    public static String encode(String text) {
        try {
            return URLEncoder.encode(text,UTF_8);
        } catch (UnsupportedEncodingException e) {
            throw new EnDeCodeException();
        }
    }

    public static String decode(String text) {
        try {
            return URLDecoder.decode(text,UTF_8);
        } catch (UnsupportedEncodingException e) {
            throw new EnDeCodeException();
        }
    }

    static class EnDeCodeException extends RuntimeException{}
}
