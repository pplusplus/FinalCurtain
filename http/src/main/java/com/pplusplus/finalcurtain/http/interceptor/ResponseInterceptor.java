package com.pplusplus.finalcurtain.http.interceptor;

import com.pplusplus.finalcurtain.http.response.Response;

/**
 * Created by Pat Powell on 02/06/2017.
 */

/**
 * @class ResponseInterceptor
 */
public interface ResponseInterceptor<T> extends Interceptor<Response<T>> {
}
