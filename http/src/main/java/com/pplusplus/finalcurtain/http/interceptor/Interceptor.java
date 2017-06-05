package com.pplusplus.finalcurtain.http.interceptor;

/**
 * Created by Pat Powell on 02/06/2017.
 */
public interface Interceptor<T> {

    void handle(T object);
}
