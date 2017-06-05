package com.pplusplus.finalcurtain.http.response.parsers;

/**
 * Created by Pat Powell on 04/06/2017.
 */
public interface ResponseParser<T> {
    T parse(byte[] data);
}
