package com.pplusplus.finalcurtain.http.image;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by Pat Powell on 04/06/2017.
 */
public class MemoryCache extends LruCache<String, Bitmap> {

    private static final int MAX_MEMORY = (int) (Runtime.getRuntime().maxMemory() / 1024);
    private static final int CACHE_SIZE = MAX_MEMORY / 8;

    public MemoryCache() {
        super(CACHE_SIZE);
    }

    @Override
    protected int sizeOf(String key, Bitmap bitmap) {
        return bitmap.getByteCount() / 1024;
    }

    public boolean exists(String key) {
        return get(key) != null;
    }
}
