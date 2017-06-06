package com.pplusplus.finalcurtain.http.image.async;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.pplusplus.finalcurtain.http.image.DiskCache;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by Pat Powell on 04/06/2017.
 */
public class SaveBitmapTask extends AsyncTask<SaveBitmapTask.DiskBitmap, Void, Void> {

    public static class DiskBitmap {
        private String path;
        private Bitmap bitmap;

        public DiskBitmap(String path, Bitmap bitmap) {
            this.path = path;
            this.bitmap = bitmap;
        }
    }

    @Override
    protected Void doInBackground(DiskBitmap... params) {
        try {
            params[0].bitmap.compress(DiskCache.IMAGE_FORMAT, 80, new FileOutputStream(new File(params[0].path)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
