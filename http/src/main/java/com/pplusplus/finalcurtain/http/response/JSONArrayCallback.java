package com.pplusplus.finalcurtain.http.response;

import com.pplusplus.finalcurtain.http.util.ByteArrayUtils;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Pat Powell on 04/06/2017.
 */
public abstract class JSONArrayCallback extends ResponseCallback<JSONArray> {

    @Override
    protected JSONArray parseResponse(byte[] data) {
        try {
            return new JSONArray(ByteArrayUtils.byteArrayToString(data));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
