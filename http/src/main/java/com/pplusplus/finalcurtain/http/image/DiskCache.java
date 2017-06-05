package com.pplusplus.finalcurtain.http.image;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by Pat Powell on 04/06/2017.
 */
public class DiskCache {

    public static final String DISK_CACHE_DIR = "blah/blah/blah";

    public boolean exists(String key) {
        return new File(key).exists();
    }

    public void read(String key, BitmapLoadedListener listener) {

    }

    public void write(String key, Bitmap bitmap) {

    }
}
