package com.pplusplus.finalcurtain.http.image;

import android.content.Context;
import android.graphics.Bitmap;

import com.pplusplus.finalcurtain.http.interceptor.RequestInterceptor;
import com.pplusplus.finalcurtain.http.interceptor.ResponseInterceptor;
import com.pplusplus.finalcurtain.http.request.HttpError;
import com.pplusplus.finalcurtain.http.request.Request;
import com.pplusplus.finalcurtain.http.response.BitmapCallback;
import com.pplusplus.finalcurtain.http.response.Response;
import com.pplusplus.finalcurtain.http.util.CryptoUtils;

import java.net.HttpURLConnection;

/**
 * Created by Pat Powell on 04/06/2017.
 */
public class RequestImageCacheInterceptor implements RequestInterceptor {

    private ImageCache imageCache;

    public RequestImageCacheInterceptor(Context context) {
        imageCache = new ImageCache(context);
    }

    private static String generateKey(Request request) {
        return CryptoUtils.md5(request.getUrl());
    }

    @Override
    public boolean intercept(final Request object) {
        if (object.isBitmapRequest() && imageCache.exists(generateKey(object))) {
            imageCache.get(generateKey(object), new BitmapLoadedListener() {
                @Override
                public void success(Bitmap bitmap) {
                    Response response = new Response();
                    response.setStatus(HttpURLConnection.HTTP_OK);
                    response.setBody(bitmap);
                    ((BitmapCallback) object.getCallback()).setFromCache(true);
                    object.getCallback().setResponse(response);
                    object.getCallback().postResponse();
                }

                @Override
                public void failed() {
                    // TODO: Change mechanism to request image instead
                    object.getCallback().onError(HttpError.FAILED);
                }
            });
            return true;
        }
        return false;
    }

    public ResponseInterceptor getResponseInterceptor() {
        return new ResponseImageCacher();
    }

    private class ResponseImageCacher implements ResponseInterceptor {

        @Override
        public boolean intercept(Request object) {
            if (object.isBitmapRequest()) {
                imageCache.put(generateKey(object), (Bitmap) object.getCallback().getResponse().get());
            }
            return false;
        }
    }
}