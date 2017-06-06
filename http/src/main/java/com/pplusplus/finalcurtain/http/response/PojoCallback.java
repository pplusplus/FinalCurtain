package com.pplusplus.finalcurtain.http.response;

import com.pplusplus.finalcurtain.http.json.parser.GsonParser;
import com.pplusplus.finalcurtain.http.json.parser.JsonParser;
import com.pplusplus.finalcurtain.http.manager.RequestManager;

import java.io.UnsupportedEncodingException;

/**
 * Created by Pat Powell on 03/06/2017.
 */

public abstract class PojoCallback<POJO> extends ResponseCallback<POJO> {

    private Class<POJO> clazz;

    public PojoCallback(Class<POJO> clazz) {
        super();
        this.clazz = clazz;
    }

    @Override
    public POJO parseResponse(byte[] data) {
        try {
            return RequestManager.getJsonParser().jsonToPojo(new String(data, "UTF-8"), clazz);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}