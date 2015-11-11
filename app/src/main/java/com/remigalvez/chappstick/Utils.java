package com.remigalvez.chappstick;

import android.util.Log;

import com.remigalvez.chappstick.asynctask.QueryServerAsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created by Remi on 11/10/15.
 */
public class Utils {
    private static final String TAG = "Utils";

    /**
     * Request service from server (ex: Uber)
     * @param service
     * @return JSON Object
     */
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

    public static String queryServer(String queryUrl) {

        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        String data = "";

        try {
            HttpGet request = new HttpGet(queryUrl);
            response = client.execute(request);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                data = EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            Log.d(TAG, "Unable to get data. Returning empty string.");
        }
        Log.d(TAG, "Data: " + data);
        return data;
    }

}
