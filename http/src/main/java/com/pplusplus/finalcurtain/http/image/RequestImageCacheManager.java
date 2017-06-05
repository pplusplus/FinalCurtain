package com.pplusplus.finalcurtain.http.image;

import android.graphics.Bitmap;

import com.pplusplus.finalcurtain.http.request.HttpError;
import com.pplusplus.finalcurtain.http.request.Request;
import com.pplusplus.finalcurtain.http.response.BitmapCallback;
import com.pplusplus.finalcurtain.http.response.Response;
import com.pplusplus.finalcurtain.http.util.CryptoUtils;

/**
 * Created by Pat Powell on 04/06/2017.
 */
public class RequestImageCacheManager {

    private static final Bitmap.CompressFormat IMAGE_FORMAT = Bitmap.CompressFormat.PNG;

    private ImageCache imageCache = new ImageCache();

    public RequestImageCacheManager() {

    }

    /**
     * Checks the ImageCache and makes a callback on behalf of the request if the image was found
     *
     * @return true if request was handled
     */
    public void getBitmap(final Request request, final BitmapLoadedListener listener) {
        if (!(request.getCallback() instanceof BitmapCallback)) {
            return;
        }
        String key = CryptoUtils.md5(request.getUrl());
        imageCache.get(key, new BitmapLoadedListener() {
            @Override
            public void success(Bitmap bitmap) {
                Response<Bitmap> bitmapResponse = new Response<Bitmap>();
                bitmapResponse.setBody(bitmap);
                ((BitmapCallback) request.getCallback()).onResponse(bitmapResponse);
            }

            @Override
            public void failed() {
                listener.failed();
            }
        });
    }

}
