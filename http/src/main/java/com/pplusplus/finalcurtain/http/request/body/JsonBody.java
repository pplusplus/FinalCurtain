package com.pplusplus.finalcurtain.http.request.body;

import com.pplusplus.finalcurtain.http.json.parser.GsonParser;
import com.pplusplus.finalcurtain.http.json.parser.JsonParser;

import java.io.UnsupportedEncodingException;

/**
 * Created by Pat Powell on 02/06/2017.
 */
public class JsonBody implements HttpBody {

    private String json;
    private JsonParser jsonParser;

    public JsonBody(Object pojo) {
        jsonParser = new GsonParser();
        json = jsonParser.pojoToJson(pojo);
    }

    public JsonBody(String json) {
        this.json = json;
    }

    @Override
    public byte[] toByteArray() {
        try {
            return json.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
