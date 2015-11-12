package com.remigalvez.chappstick.asynctask;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class QueryServerAsyncTask extends AsyncTask<String, Void, JSONObject> {

    private QueryCompletionListener mQueryCompletionListener;

    public interface QueryCompletionListener {
        public void responseReceived(JSONObject data);
        public void noResponseReceived();
    }

    public QueryServerAsyncTask(QueryCompletionListener completionListener) {
        mQueryCompletionListener = completionListener;
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        String stringUrl = params[0];
        URL url;
        HttpsURLConnection con = null;
        JSONObject jsonData = null;
        try {
            url = new URL(stringUrl);
            con = (HttpsURLConnection)url.openConnection();
            jsonData = parseToJsonObject(con);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonData;
    }

    @Override
    protected void onPostExecute(JSONObject data) {
        if (data != null)
            mQueryCompletionListener.responseReceived(data);
        else
            mQueryCompletionListener.noResponseReceived();
    }

    private static JSONObject parseToJsonObject(HttpsURLConnection con) throws IOException, JSONException {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        return new JSONObject(builder.toString());
    }
}
