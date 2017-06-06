package com.pplusplus.finalcurtain.http.util;

import com.pplusplus.finalcurtain.http.manager.RequestTask;
import com.pplusplus.finalcurtain.http.request.Request;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Pat Powell on 05/06/2017.
 */
public class ThreadPoolHelper {

    private static final int KEEP_ALIVE_TIME = 1;
    private static final TimeUnit KEEP_ALIVE_UNIT = TimeUnit.SECONDS;

    private ThreadPoolExecutor threadPool;
    private BlockingQueue queue;

    public ThreadPoolHelper(int noOfThreads) {
        queue = new LinkedBlockingQueue();
        threadPool = new ThreadPoolExecutor(
                noOfThreads,
                noOfThreads,
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_UNIT,
                queue);
    }

    public Executor getExecutor() {
        return threadPool;
    }

}
