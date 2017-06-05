package com.pplusplus.finalcurtain.http.manager;

import android.graphics.Bitmap;

import com.pplusplus.finalcurtain.http.image.BitmapLoadedListener;
import com.pplusplus.finalcurtain.http.image.RequestImageCacheManager;
import com.pplusplus.finalcurtain.http.interceptor.RequestInterceptor;
import com.pplusplus.finalcurtain.http.interceptor.ResponseInterceptor;
import com.pplusplus.finalcurtain.http.json.parser.GsonParser;
import com.pplusplus.finalcurtain.http.json.parser.JsonParser;
import com.pplusplus.finalcurtain.http.request.Request;

/**
 * Created by Pat Powell on 02/06/2017.
 */

public class RequestManager {

    private static RequestManager instance;

    private JsonParser jsonParser = new GsonParser();

    private RequestInterceptor requestInterceptor;
    private ResponseInterceptor responseInterceptor;

    private RequestQueue requestQueue;
    private RequestImageCacheManager imageCacheManager;

    public static RequestManager getInstance() {
        if (instance == null) {
            instance = new RequestManager();
        }
        return instance;
    }

    private RequestManager() {
        requestQueue = new RequestQueue();
    }

    public void addToQueue(Request request) {
        if (request.isBitmapRequest()) {
            imageCacheManager.getBitmap(request, new BitmapLoadedListener() {
                @Override
                public void success(Bitmap bitmap) {

                }

                @Override
                public void failed() {

                }
            });
        }
        requestQueue.add(request);
    }

    public JsonParser getJsonParser() {
        return jsonParser;
    }

    public void setJsonParser(JsonParser jsonParser) {
        this.jsonParser = jsonParser;
    }

    public void cancel(String tag) {

    }
}
