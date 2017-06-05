package com.pplusplus.finalcurtain.http.manager;

import com.pplusplus.finalcurtain.http.util.Constants;

/**
 * Created by Pat Powell on 04/06/2017.
 */

public class Settings {

    public static class Queue {
        private int threadCount = 1;
    }

    public static class Request {
        private String encoding = "UTF-8";
    }

    public static class Image {
        private boolean calculatedCacheSize = true;
        private int memoryCacheSize;
        private int diskCacheSize = Constants.BYTES_IN_MB * 60;
    }

}
