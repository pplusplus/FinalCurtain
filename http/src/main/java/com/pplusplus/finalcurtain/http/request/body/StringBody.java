package com.pplusplus.finalcurtain.http.request.body;

import java.io.UnsupportedEncodingException;

/**
 * Created by Pat Powell on 02/06/2017.
 */
public class StringBody implements HttpBody {

    private String payload;

    public StringBody(String payload) {
        this.payload = payload;
    }

    @Override
    public byte[] toByteArray() {
        try {
            return payload.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
