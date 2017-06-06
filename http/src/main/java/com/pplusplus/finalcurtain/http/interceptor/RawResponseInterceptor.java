package com.pplusplus.finalcurtain.http.interceptor;

/**
 * Created by Pat Powell on 04/06/2017.
 */
public class RawResponseInterceptor implements Interceptor<Byte[]> {

    @Override
    public boolean intercept(Byte[] object) {
        return false;
    }
}
