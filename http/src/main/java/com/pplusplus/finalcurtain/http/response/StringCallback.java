package com.pplusplus.finalcurtain.http.response;

import android.util.Log;

import com.pplusplus.finalcurtain.http.util.ByteArrayUtils;

import java.io.UnsupportedEncodingException;

/**
 * Created by Pat Powell on 03/06/2017.
 */
public abstract class StringCallback extends ResponseCallback<String> {

    private String encoding = "UTF-8";

    public StringCallback() {
    }

    public StringCallback(String encoding) {
        this.encoding = encoding;
    }

    @Override
    public String parseResponse(byte[] data) {
        return ByteArrayUtils.byteArrayToString(data);
    }
}
