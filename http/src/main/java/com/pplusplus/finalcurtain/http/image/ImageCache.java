package com.pplusplus.finalcurtain.http.image;

import android.graphics.Bitmap;

/**
 * Created by Pat Powell on 02/06/2017.
 */
public class ImageCache {

    final private MemoryCache memoryCache = new MemoryCache();
    final private DiskCache diskCache = new DiskCache();

    public ImageCache() {
    }

    public void get(final String key, BitmapLoadedListener listener) {
        Bitmap bitmap = memoryCache.get(key);
        if (bitmap != null) {
            listener.success(bitmap);
            return;
        }
        if (diskCache.exists(key)) {
            // Load from disk
            diskCache.read(key, new BitmapCacher(key, listener));
            // Add to memory cache
            return;
        }
        listener.failed();
    }

    public void put(String key, Bitmap bitmap) {
        memoryCache.put(key, bitmap);
        if (!diskCache.exists(key)) {
            diskCache.write(key, bitmap);
        }
    }

    private class BitmapCacher implements BitmapLoadedListener {

        private BitmapLoadedListener listener;
        private String key;

        public BitmapCacher(String key, BitmapLoadedListener listener) {
            this.key = key;
            this.listener = listener;
        }

        @Override
        public void success(Bitmap bitmap) {
            memoryCache.put(key, bitmap);
            listener.success(bitmap);
        }

        @Override
        public void failed() {
            listener.failed();
        }
    }
}
