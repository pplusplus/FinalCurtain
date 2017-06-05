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

    private RawResponseInterceptor rawResponseInterceptor;
    private ResponseInterceptor responseInterceptor;

    public ResponseCallback() {
    }

    public void parseResponse(HttpURLConnection connection) {
        final Response<T> response = new Response<T>();
        try {
            response.setStatus(connection.getResponseCode());
            if (response.getStatus() >= 200 && response.getStatus() <= 299) {
                response.setBody(parseResponse(UrlConnectionUtils.parseResponseToByteArray(connection.getInputStream())));
            } else {
                response.setBody(parseResponse(UrlConnectionUtils.parseResponseToByteArray(connection.getErrorStream())));
            }
            response.setHeaders(UrlConnectionUtils.parseResponseHeaders(connection));
            postResponse(response);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            postError(HttpError.CONNECTION_ERROR);
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            postError(HttpError.TIMEOUT_ERROR);
        } catch (IOException e) {
            e.printStackTrace();
            postError(HttpError.FAILED);
        }
    }

    public void setResponseInterceptor(ResponseInterceptor<T> interceptor) {
        this.responseInterceptor = interceptor;
    }

    public void setRawInterceptor(RawResponseInterceptor interceptor) {
        this.rawResponseInterceptor = interceptor;
    }

    protected void postResponse(final Response<T> response) {
        new Handler(callbackLooper).post(new Runnable() {
            @Override
            public void run() {
                onResponse(response);
            }
        });
    }

    protected void postError(final HttpError error) {
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
