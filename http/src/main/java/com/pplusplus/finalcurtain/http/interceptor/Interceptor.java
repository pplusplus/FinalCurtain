package com.pplusplus.finalcurtain.http.interceptor;

import java.util.Collection;
import java.util.Set;

/**
 * Created by Pat Powell on 02/06/2017.
 */
public interface Interceptor<T> {

    boolean intercept(T object);

}
