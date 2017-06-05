package com.pplusplus.finalcurtain;

import android.graphics.Bitmap;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.pplusplus.finalcurtain.http.request.HttpError;
import com.pplusplus.finalcurtain.http.request.Request;
import com.pplusplus.finalcurtain.http.request.body.JsonBody;
import com.pplusplus.finalcurtain.http.response.BitmapCallback;
import com.pplusplus.finalcurtain.http.response.PojoCallback;
import com.pplusplus.finalcurtain.http.response.Response;
import com.pplusplus.finalcurtain.http.response.StringCallback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request();
            }
        });

        final ImageView imageView = (ImageView) findViewById(R.id.img_test);
        Request request = new Request.Builder()
                .url("http://www.hakodate.travel/en/wp-content/themes/thakodate/more-about-hakodate/our-suggestions-in-hakodate/nightview/img/en_main.jpg")
                .callback(new BitmapCallback() {
                    @Override
                    public void onResponse(Response<Bitmap> response) {
                        imageView.setImageBitmap(response.get());
                    }

                    @Override
                    public void onError(HttpError error) {
                        Log.d("MainActivity", "Error");
                        Log.d("MainActivity", error.getMessage());
                    }
                }).build();

        request.asyncGo();
    }

    private void request() {
        new Request.Builder()
                .url("http://demo3635569.mockable.io/")
                .callback(new PojoCallback<Person>(Person.class) {
                    @Override
                    public void onResponse(Response<Person> response) {
                        Log.d("Response", response.get().toString());
                        Log.d("Response Headers", response.getHeaders().toString());
                    }

                    @Override
                    public void onError(HttpError error) {

                    }
                }).body(new JsonBody("{}"))
                .build()
                .asyncGo();
    }
}
