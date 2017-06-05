package com.pplusplus.finalcurtain.http.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Pat Powell on 04/06/2017.
 */
public class CryptoUtils {

    public static String md5(String string) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] digestOutput = digest.digest(string.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : digestOutput) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
