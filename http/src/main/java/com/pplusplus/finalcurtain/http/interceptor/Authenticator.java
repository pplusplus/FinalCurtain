package com.pplusplus.finalcurtain.http.interceptor;

import com.pplusplus.finalcurtain.http.request.Request;
import com.pplusplus.finalcurtain.http.response.Response;

/**
 * Created by Pat Powell on 02/06/2017.
 */
public class Authenticator implements RequestInterceptor {

    @Override
    public boolean intercept(Request object) {
        return false;
    }
}
