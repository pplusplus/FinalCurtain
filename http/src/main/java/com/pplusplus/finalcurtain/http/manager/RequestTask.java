package com.pplusplus.finalcurtain.http.manager;

import android.os.AsyncTask;
import android.util.Log;

import com.pplusplus.finalcurtain.http.request.Request;

/**
 * Created by Pat Powell on 02/06/2017.
 */

public class RequestTask extends AsyncTask<Request, Void, Void> {

    @Override
    protected Void doInBackground(Request... requests) {
        requests[0].syncGo();
        return null;
    }
}
