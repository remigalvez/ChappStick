package com.remigalvez.chappstick.util;

import android.util.Log;

import com.remigalvez.chappstick.constant.Constants;
import com.remigalvez.chappstick.asynctask.QueryServerAsyncTask;

/**
 * Created by Remi on 11/10/15.
 */
public class ServerUtils {
    private static final String TAG = "ServerUtils";

    public static void request(String service,
                               QueryServerAsyncTask.QueryCompletionListener completionListener) {
        String url = parseQueryToUrl(service);
        Log.d(TAG, url);
        QueryServerAsyncTask query = new QueryServerAsyncTask(completionListener);
        query.execute(url);
    }

    private static String parseQueryToUrl(String service) {
        String url = Constants.HEROKU_SERVER_URL + service;
        return url;
    }
}
