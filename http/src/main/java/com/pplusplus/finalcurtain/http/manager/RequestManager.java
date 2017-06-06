package com.pplusplus.finalcurtain.http.manager;

import android.content.Context;

import com.pplusplus.finalcurtain.http.image.RequestImageCacheInterceptor;
import com.pplusplus.finalcurtain.http.interceptor.RequestInterceptor;
import com.pplusplus.finalcurtain.http.interceptor.ResponseInterceptor;
import com.pplusplus.finalcurtain.http.json.parser.GsonParser;
import com.pplusplus.finalcurtain.http.json.parser.JsonParser;
import com.pplusplus.finalcurtain.http.request.Request;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Pat Powell on 02/06/2017.
 */

public class RequestManager {

    private static RequestManager instance;

    private static JsonParser jsonParser = new GsonParser();

    private RequestQueue requestQueue;

    private Set<RequestInterceptor> requestInterceptors = new HashSet<>();
    private Set<ResponseInterceptor> responseInterceptors = new HashSet<>();

    public static RequestManager initialize(Context context) {
        if (instance == null) {
            instance = new RequestManager(context);
        }
        return instance;
    }

    public static RequestManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("RequestManager has not been initialized");
        }
        return instance;
    }

    private RequestManager(Context context) {
        requestQueue = new RequestQueue();
        RequestImageCacheInterceptor imageCacheManager = new RequestImageCacheInterceptor(context);
        requestInterceptors.add(imageCacheManager);
        responseInterceptors.add(imageCacheManager.getResponseInterceptor());
    }

    public void addToQueue(final Request request) {
        request.setRequestInterceptors(requestInterceptors);
        request.setResponseInterceptors(responseInterceptors);
        requestQueue.add(request);
    }

    public static JsonParser getJsonParser() {
        return jsonParser;
    }

    public void setJsonParser(JsonParser jsonParser) {
        this.jsonParser = jsonParser;
    }

    public void addRequestInterceptor(RequestInterceptor requestInterceptor) {
        requestInterceptors.add(requestInterceptor);
    }

    public void addResponseInterceptor(ResponseInterceptor responseInterceptor) {
        responseInterceptors.add(responseInterceptor);
    }

    public void cancel(String tag) {

    }
}
