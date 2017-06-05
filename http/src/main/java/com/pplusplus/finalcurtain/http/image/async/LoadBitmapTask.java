package com.pplusplus.finalcurtain.http.image.async;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.pplusplus.finalcurtain.http.image.BitmapLoadedListener;

/**
 * Created by Pat Powell on 04/06/2017.
 */
public class LoadBitmapTask extends AsyncTask<String, Void, Bitmap> {

    private BitmapLoadedListener listener;

    public LoadBitmapTask(BitmapLoadedListener listener) {
        this.listener = listener;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        return BitmapFactory.decodeFile(params[0]);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null) {
            listener.success(bitmap);
        } else {
            listener.failed();
        }
    }
}
