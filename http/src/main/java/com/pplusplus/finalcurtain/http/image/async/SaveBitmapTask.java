package com.pplusplus.finalcurtain.http.image.async;

import android.graphics.Bitmap;
import android.os.AsyncTask;

/**
 * Created by Pat Powell on 04/06/2017.
 */
public class SaveBitmapTask extends AsyncTask<SaveBitmapTask.DiskBitmap, Void, Void> {

    public static class DiskBitmap {
        private String path;
        private Bitmap bitmap;

        public DiskBitmap(String path, Bitmap bitmap) {

        }
    }

    @Override
    protected Void doInBackground(DiskBitmap... params) {

        return null;
    }
}
