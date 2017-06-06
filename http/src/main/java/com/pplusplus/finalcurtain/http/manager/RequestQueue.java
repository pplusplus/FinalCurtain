package com.pplusplus.finalcurtain.http.manager;

import com.pplusplus.finalcurtain.http.request.Request;
import com.pplusplus.finalcurtain.http.util.ThreadPoolHelper;

/**
 * Created by Pat Powell on 02/06/2017.
 */
public class RequestQueue extends ThreadPoolHelper {

    private static final int THREAD_COUNT = 1;

    RequestQueue() {
        super(THREAD_COUNT);
    }

    public void add(Request request) {
        new RequestTask().executeOnExecutor(getExecutor(), request);
    }
}
