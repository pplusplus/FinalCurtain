package com.pplusplus.finalcurtain.http.image;

import android.content.Context;
import android.graphics.Bitmap;

import com.pplusplus.finalcurtain.http.image.async.ImageCacheConcurrencyManager;
import com.pplusplus.finalcurtain.http.image.async.LoadBitmapTask;
import com.pplusplus.finalcurtain.http.image.async.SaveBitmapTask;

import java.io.File;

/**
 * Created by Pat Powell on 04/06/2017.
 */
public class DiskCache {

    public static final Bitmap.CompressFormat IMAGE_FORMAT = Bitmap.CompressFormat.PNG;
    private static final String CACHE_DIR_SUFFIX = "/FinalCurtain/Images/";

    private File path;

    private ImageCacheConcurrencyManager concurrencyManager;

    private DiskCache() {
        concurrencyManager = new ImageCacheConcurrencyManager();
    }

    public DiskCache(Context context) {
        this();
        path = new File(context.getCacheDir().getAbsolutePath() + CACHE_DIR_SUFFIX);
        createCacheDirIfNotExists();
    }

    public DiskCache(String cachePath) {
        this();
        this.path = new File(cachePath + CACHE_DIR_SUFFIX);
        createCacheDirIfNotExists();
    }

    public boolean exists(String key) {
        return new File(path + key).exists();
    }

    private void createCacheDirIfNotExists() {
        if (!path.exists()) {
            path.mkdirs();
        }
    }

    public void read(String key, BitmapLoadedListener listener) {
        new LoadBitmapTask(listener).executeOnExecutor(concurrencyManager.getExecutor(), path + key);
    }

    public void write(String key, Bitmap bitmap) {
        new SaveBitmapTask().executeOnExecutor(concurrencyManager.getExecutor(), new SaveBitmapTask.DiskBitmap(path.getAbsolutePath() + key, bitmap));
    }
}
