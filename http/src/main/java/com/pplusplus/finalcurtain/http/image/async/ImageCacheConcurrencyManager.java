package com.pplusplus.finalcurtain.http.image.async;

import com.pplusplus.finalcurtain.http.util.ThreadPoolHelper;

/**
 * Created by Pat Powell on 05/06/2017.
 */
public class ImageCacheConcurrencyManager extends ThreadPoolHelper {

    private static final int THREAD_COUNT = 1;

    public ImageCacheConcurrencyManager() {
        super(THREAD_COUNT);
    }

}
