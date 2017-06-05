package com.pplusplus.finalcurtain.http.util;

import java.io.UnsupportedEncodingException;

/**
 * Created by Pat Powell on 04/06/2017.
 */
public class ByteArrayUtils {

    private ByteArrayUtils() {
    }

    public static String byteArrayToString(byte[] data) {
        try {
            return new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
