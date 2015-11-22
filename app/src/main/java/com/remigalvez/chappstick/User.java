package com.remigalvez.chappstick;

import com.remigalvez.chappstick.asynctask.QueryServerAsyncTask;
import com.remigalvez.chappstick.objects.App;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Remi on 11/21/15.
 */
public class User {

    public List<App> apps;
    private String userId;

    public User(String userId) {
        apps = new ArrayList<>();
        getUserApps();
    }

    public void getUserApps() {
        // Get list of user app IDs
        Utils.getUserApps(Constants.USER_ID, new QueryServerAsyncTask.QueryCompletionListener() {
            @Override
            public void responseReceived(JSONObject data) {
                try {
                    JSONArray appNames = data.getJSONArray("apps");
                    for (int i = 0; i < appNames.length(); i++) {
                        createAppObject(appNames.get(i).toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void noResponseReceived() {
                System.out.println("Uh oh...");
            }
        });
    }

    private void createAppObject(String appId) {
        Utils.getApp(appId, new QueryServerAsyncTask.QueryCompletionListener() {
            @Override
            public void responseReceived(JSONObject data) {
                App app = new App();
                try {
                    app.setId(data.getString("objectId"));
                    app.setName(data.getString("name"));
                    app.setAppImgUrl(data.getString("iconUrl"));
                    apps.add(app);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void noResponseReceived() {

            }
        });
    }

    public List<App> getApps() {
        return apps;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
