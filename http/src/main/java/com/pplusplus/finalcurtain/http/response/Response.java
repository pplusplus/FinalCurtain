package com.pplusplus.finalcurtain.http.response;

import com.pplusplus.finalcurtain.http.interceptor.ResponseInterceptor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Pat Powell on 02/06/2017.
 */

public class Response<T> {

    public T body;
    public int status;
    public Map<String, String> headers = new HashMap<String, String>();

    public Response() {
    }

    public Response(T body) {
        this.body = body;
    }

    public T get() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

}
