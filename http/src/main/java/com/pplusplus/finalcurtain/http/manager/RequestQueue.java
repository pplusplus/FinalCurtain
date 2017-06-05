package com.pplusplus.finalcurtain.http.manager;

import com.pplusplus.finalcurtain.http.request.Request;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Pat Powell on 02/06/2017.
 */
public class RequestQueue {

    private static final int THREAD_COUNT = 1;
    private static final int KEEP_ALIVE_TIME = 30;
    private static final TimeUnit KEEP_ALIVE_UNIT = TimeUnit.SECONDS;

    private ThreadPoolExecutor threadPool;
    private BlockingQueue queue;

    RequestQueue() {
        queue = new LinkedBlockingQueue();
        threadPool = new ThreadPoolExecutor(
                THREAD_COUNT,
                THREAD_COUNT,
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_UNIT,
                queue);
    }

    public void add(Request request) {
        new RequestTask().executeOnExecutor(threadPool, request);
    }
}
