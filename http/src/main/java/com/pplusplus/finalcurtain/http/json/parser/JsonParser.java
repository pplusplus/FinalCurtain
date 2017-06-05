package com.pplusplus.finalcurtain.http.json.parser;

/**
 * Created by Pat Powell on 02/06/2017.
 */

/**
 * @interface JsonParser
 */
public interface JsonParser {

    /**
     * Parses a POJO to a json string
     *
     * @param pojo to be parsed to string
     */
    String pojoToJson(Object pojo);

    /**
     * Parses json string to a pojo class
     *
     * @param json string to be parsed to pojo
     */
    <T> T jsonToPojo(String json, Class<T> clazz);
}
