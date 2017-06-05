package com.pplusplus.finalcurtain.http.image;

import android.graphics.Bitmap;

/**
 * Created by Pat Powell on 04/06/2017.
 */

public interface BitmapLoadedListener {

    void success(Bitmap bitmap);

    void failed();
}
