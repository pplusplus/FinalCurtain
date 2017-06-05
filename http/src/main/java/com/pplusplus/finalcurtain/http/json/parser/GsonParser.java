package com.pplusplus.finalcurtain.http.json.parser;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by Pat Powell on 02/06/2017.
 */
public class GsonParser implements JsonParser {

    private Gson gson;

    public GsonParser() {
        gson = new Gson();
    }

    @Override
    public String pojoToJson(Object pojo) {
        return gson.toJson(pojo);
    }

    @Override
    public <T> T jsonToPojo(String json, Class<T> clazz) {
        return (T) gson.fromJson(json, clazz);
    }
}
