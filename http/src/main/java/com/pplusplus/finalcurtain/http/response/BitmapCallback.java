package com.pplusplus.finalcurtain.http.response;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Pat Powell on 03/06/2017.
 */
public abstract class BitmapCallback extends ResponseCallback<Bitmap> {

    private boolean isFromCache = false;

    @Override
    protected Bitmap parseResponse(byte[] data) {
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }
}
