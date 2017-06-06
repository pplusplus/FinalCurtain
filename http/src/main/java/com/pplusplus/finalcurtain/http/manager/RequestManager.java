package com.pplusplus.finalcurtain.http.manager;

import android.content.Context;
import android.graphics.Bitmap;

import com.pplusplus.finalcurtain.http.image.BitmapLoadedListener;
import com.pplusplus.finalcurtain.http.image.RequestImageCache;
import com.pplusplus.finalcurtain.http.interceptor.RequestInterceptor;
import com.pplusplus.finalcurtain.http.interceptor.ResponseInterceptor;
import com.pplusplus.finalcurtain.http.json.parser.GsonParser;
import com.pplusplus.finalcurtain.http.json.parser.JsonParser;
import com.pplusplus.finalcurtain.http.request.Request;
import com.pplusplus.finalcurtain.http.response.BitmapCallback;
import com.pplusplus.finalcurtain.http.response.Response;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Pat Powell on 02/06/2017.
 */

public class RequestManager {

    private static RequestManager instance;

    private static JsonParser jsonParser = new GsonParser();

    private RequestQueue requestQueue;
    private RequestImageCache imageCacheManager;

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
        imageCacheManager = new RequestImageCache(context);
        requestInterceptors.add(imageCacheManager);
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

    private boolean interceptRequest(Request request) {
        boolean intercept = false;
        for (RequestInterceptor interceptor : requestInterceptors) {
            if (interceptor.intercept(request)) {
                intercept = true;
            }
        }
        return intercept;
    }

    private void retryRequest(Request request, Response response) {
        for (ResponseInterceptor interceptor : responseInterceptors) {
            interceptor.intercept(response);
        }
    }

    private class BitmapLoadedRequestCallback implements BitmapLoadedListener {

        private Request request;

        BitmapLoadedRequestCallback(Request request) {
            this.request = request;
        }

        @Override
        public void success(Bitmap bitmap) {
            Response<Bitmap> bitmapResponse = new Response<Bitmap>();
            bitmapResponse.setBody(bitmap);
            ((BitmapCallback) request.getCallback()).setFromCache(true);
            ((BitmapCallback) request.getCallback()).onResponse(bitmapResponse);
        }

        @Override
        public void failed() {
            requestQueue.add(request);
        }
    }
}
