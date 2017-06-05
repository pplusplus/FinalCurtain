package com.pplusplus.finalcurtain.http.response;

import com.pplusplus.finalcurtain.http.request.HttpError;
import com.pplusplus.finalcurtain.http.util.ByteArrayUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Pat Powell on 04/06/2017.
 */
public abstract class JSONObjectCallback extends ResponseCallback<JSONObject> {

    public JSONObjectCallback() {
    }

    @Override
    protected JSONObject parseResponse(byte[] data) {
        try {
            return new JSONObject(ByteArrayUtils.byteArrayToString(data));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
