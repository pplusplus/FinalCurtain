package com.pplusplus.finalcurtain.http.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pat Powell on 03/06/2017.
 */
public class UrlConnectionUtils {

    private UrlConnectionUtils() {
        // Private Constructor
    }

    public static void addHeaders(HttpURLConnection httpURLConnection, Map<String, String> map) {
        for (String k : map.keySet()) {
            httpURLConnection.setRequestProperty(k, map.get(k));
        }
    }

    public static Map<String, String> parseResponseHeaders(HttpURLConnection httpURLConnection) {
        Map<String, String> map = new HashMap<>();
        for (String k : httpURLConnection.getHeaderFields().keySet()) {
            map.put(k, httpURLConnection.getHeaderField(k));
        }
        return map;
    }

    public static byte[] parseResponseToByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int readCount;
        byte[] buffer = new byte[1024];
        while ((readCount = inputStream.read(buffer, 0, buffer.length)) != -1) {
            baos.write(buffer, 0, readCount);
        }
        byte[] data = baos.toByteArray();
        baos.flush();
        baos.close();
        return data;
    }
}
