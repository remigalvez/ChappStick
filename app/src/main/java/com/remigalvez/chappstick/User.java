package com.remigalvez.chappstick;

import com.remigalvez.chappstick.activity.HomescreenActivity;
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
    public static final String TAG = "User";

    public static User user;

    public List<App> apps;
    private String userId;

    private User(String userId) {
        user = this;
        apps = new ArrayList<>();
    }

    public void getUserApps() {
        // Get list of user app IDs
        Utils.getUserApps(, new QueryServerAsyncTask.QueryCompletionListener() {
            @Override
            public void responseReceived(JSONObject data) {
                try {
                    // Get list of app ids
                    JSONArray appNames = data.getJSONArray("apps");
                    // For each app id, get app data and create App object
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
        // Get app data
        Utils.getApp(appId, new QueryServerAsyncTask.QueryCompletionListener() {
            @Override
            public void responseReceived(JSONObject data) {
                // Create App object with data
                App app = App.createFromJSON(data);
                // Add app to list of apps
                apps.add(app);
                // Add app to homescreen
                HomescreenActivity hs = HomescreenActivity.getInstance();
                hs.addItemToList(app);
            }

            @Override
            public void noResponseReceived() {

            }
        });
    }

    public List<App> getApps() {
        return apps;
    }

    public static User getInstance() {
        return user;
    }

    public static void instantiateUser(String userId) {
        user = new User(userId);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
