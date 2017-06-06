package com.pplusplus.finalcurtain.http.response;

import android.os.Handler;
import android.os.Looper;

import com.pplusplus.finalcurtain.http.interceptor.RawResponseInterceptor;
import com.pplusplus.finalcurtain.http.interceptor.ResponseInterceptor;
import com.pplusplus.finalcurtain.http.request.HttpError;
import com.pplusplus.finalcurtain.http.util.UrlConnectionUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Created by Pat Powell on 03/06/2017.
 */

public abstract class ResponseCallback<T> {

    private Looper callbackLooper = Looper.getMainLooper();

    private Response<T> response = new Response<>();

    public ResponseCallback() {
    }

    public Response<T> parseResponse(HttpURLConnection connection) throws IOException {
        response.setStatus(connection.getResponseCode());
        if (response.getStatus() >= 200 && response.getStatus() <= 299) {
            response.setBody(parseResponse(UrlConnectionUtils.parseResponseToByteArray(connection.getInputStream())));
        } else {
            response.setBody(parseResponse(UrlConnectionUtils.parseResponseToByteArray(connection.getErrorStream())));
        }
        response.setHeaders(UrlConnectionUtils.parseResponseHeaders(connection));
        return response;
    }

    public Response<T> getResponse() {
        return response;
    }

    public void setResponse(Response<T> response) {
        this.response = response;
    }

    /**
     * Posts response back to the main thread
     */
    public void postResponse() {
        new Handler(callbackLooper).post(new Runnable() {
            @Override
            public void run() {
                onResponse(response);
            }
        });
    }

    /**
     * Posts error back to the main thread
     */
    public void postError(final HttpError error) {
        new Handler(callbackLooper).post(new Runnable() {
            @Override
            public void run() {
                onError(error);
            }
        });
    }

    protected abstract T parseResponse(byte[] data);

    public abstract void onResponse(Response<T> response);

    public abstract void onError(HttpError error);
}
