package com.pplusplus.finalcurtain.http.request;

import android.content.Context;
import android.support.annotation.WorkerThread;

import com.pplusplus.finalcurtain.http.interceptor.Interceptor;
import com.pplusplus.finalcurtain.http.interceptor.RequestInterceptor;
import com.pplusplus.finalcurtain.http.interceptor.ResponseInterceptor;
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
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by Pat Powell on 02/06/2017.
 */

public class Request {

    public static class Defaults {
        public static final int TIMEOUT = (int) TimeUnit.SECONDS.toMillis(10);
        public static final Method METHOD = Method.GET;
    }

    public enum Method {
        GET, POST, PUT, DELETE;
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

    private URL url;
    private Method method;
    private Map<String, String> headers = new HashMap<>();
    private int timeout;
    private HttpBody body;
    private ResponseCallback callback;

    private boolean isCancelled = false;
    private boolean isBitmapRequest = false;
    private boolean useCache = true;

    private Set<RequestInterceptor> requestInterceptors = new HashSet<>();
    private Set<ResponseInterceptor> responseInterceptors = new HashSet<>();

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

    public void cancel() {
        this.isCancelled = true;
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

    public void setRequestInterceptors(Set<RequestInterceptor> requestInterceptors) {
        this.requestInterceptors = requestInterceptors;
    }

    public void setResponseInterceptors(Set<ResponseInterceptor> responseInterceptors) {
        this.responseInterceptors = responseInterceptors;
    }

    /**
     * Makes the http connection and delivers the response synchronously
     * If you invoke this on the MainThread "you're gonna have a bad time"
     */
    @WorkerThread
    public void syncGo() {
        HttpURLConnection urlConnection = null;
        try {
            if (hasRequestBeenIntercepted()) {
                return;
            }
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
            callback.getResponse().addAllInterceptors(responseInterceptors);
            if (callback.getResponse().hasBeenIntercepted()) {
                return;
            }
            callback.postResponse();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            callback.postError(HttpError.CONNECTION_ERROR);
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            callback.postError(HttpError.TIMEOUT_ERROR);
        } catch (IOException e) {
            e.printStackTrace();
            callback.postError(HttpError.FAILED);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    private boolean hasRequestBeenIntercepted() {
        boolean intercepted = false;
        for (RequestInterceptor i : requestInterceptors) {
            if (i.intercept(this)) {
                intercepted = true;
            }
        }
        return intercepted;
    }

    public void asyncGo() {
        RequestManager.getInstance().addToQueue(this);
    }

}