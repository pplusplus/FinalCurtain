package com.pplusplus.finalcurtain.http.request;

import android.support.annotation.WorkerThread;

import com.pplusplus.finalcurtain.http.manager.RequestManager;
import com.pplusplus.finalcurtain.http.request.body.HttpBody;
import com.pplusplus.finalcurtain.http.response.BitmapCallback;
import com.pplusplus.finalcurtain.http.response.Response;
import com.pplusplus.finalcurtain.http.response.ResponseCallback;
import com.pplusplus.finalcurtain.http.util.UrlConnectionUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Pat Powell on 02/06/2017.
 */

public class Request {

    private URL url;

    private ResponseCallback callback;
    private int timeout;
    private HttpBody body;
    private Method method;
    private Map<String, String> headers = new HashMap<>();

    private boolean isCancelled = false;
    private boolean isBitmapRequest = false;
    private boolean useCache = true;

    private Request() {
    }

    private Request(Builder builder) {
        this.url = builder.url;
        this.callback = builder.callback;
        isBitmapRequest = callback instanceof BitmapCallback;
        this.timeout = builder.timeout;
        this.body = builder.body;
        this.method = builder.method;
        this.headers = builder.headers;
    }

    public String getUrl() {
        return url.toString();
    }

    public int getTimeout() {
        return timeout;
    }

    public HttpBody getBody() {
        return body;
    }

    public Method getMethod() {
        return method;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public boolean isBitmapRequest() {
        return isBitmapRequest;
    }

    public boolean useCache() {
        return useCache;
    }

    public ResponseCallback getCallback() {
        return callback;
    }

    /**
     * Makes the http connection and delivers the response synchronously
     * If you invoke this on the MainThread "you're gonna have a bad time"
     */
    @WorkerThread
    public void syncGo() {
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(method.name());
            urlConnection.setConnectTimeout(timeout);
            urlConnection.setReadTimeout(timeout);
            urlConnection.setDoInput(true);
            UrlConnectionUtils.addHeaders(urlConnection, headers);
            if (method == Method.POST || method == Method.PUT) {
                // Add http body
                byte[] bytes = body.toByteArray();
                urlConnection.setDoOutput(true);
                urlConnection.setFixedLengthStreamingMode(bytes.length);
                DataOutputStream dataOutputStream = new DataOutputStream(urlConnection.getOutputStream());
                dataOutputStream.write(bytes);
            }
            callback.parseResponse(urlConnection);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    public void asyncGo() {
        RequestManager.getInstance().addToQueue(this);
    }

    public void cancel() {
        this.isCancelled = true;
    }

    public interface RequestCoordinator {

        void preRequest(Request request);

        void postRequest(Request request, Response response);
    }

    public enum Method {
        GET, POST, PUT, DELETE;
    }

    public static class Defaults {
        public static final int TIMEOUT = (int) TimeUnit.SECONDS.toMillis(10);
        public static final Method METHOD = Method.GET;
    }

    public static class Builder<T> {
        private URL url;
        private Method method = Defaults.METHOD;
        private HttpBody body;
        private int timeout = Defaults.TIMEOUT;
        private ResponseCallback<T> callback;
        private Map<String, String> headers = new HashMap<>();

        public Builder() {
        }

        public Builder url(String url) {
            try {
                this.url = new URL(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return this;
        }

        public Builder method(Method method) {
            this.method = method;
            return this;
        }

        public Builder body(HttpBody body) {
            this.body = body;
            return this;
        }

        public Builder timeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        public Builder callback(ResponseCallback callback) {
            this.callback = callback;
            return this;
        }

        public Builder header(String key, String value) {
            headers.put(key, value);
            return this;
        }

        public Request build() {
            return new Request(this);
        }
    }

}