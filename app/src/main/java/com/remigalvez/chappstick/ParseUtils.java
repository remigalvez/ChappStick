package com.remigalvez.chappstick;

import android.content.Context;
import android.util.Log;

import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.remigalvez.chappstick.objects.App;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Remi on 11/26/15.
 */
public class ParseUtils {
    private static final String TAG = "ParseUtils";


    public static void getUser(String username, String password, final User.CompletionListener completionListener) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null) {
                    User u = parseUser(user);
                    completionListener.responseReceived(u);
                } else {
                    // TODO: Handle error
                    completionListener.noResponseReceived();
                }
            }
        });
    }

    public static void getApp(String appId, final App.CompletionListener completionListener) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("App");
        query.getInBackground(appId, new GetCallback<ParseObject>() {
            public void done(ParseObject app, ParseException e) {
                if (e == null) {
                    App a = parseApp(app);
                    completionListener.responseReceived(a);
                } else {
                    // TODO: Handle error
                    completionListener.noResponseReceived();
                }
            }
        });
    }

    // Parse ``ParseUser'' object to ``User'' object
    public static User parseUser(ParseUser user) {
        User u = new User();
        u.setId(user.getObjectId());

        // Get list of app ids
        JSONArray appIds = user.getJSONArray("apps");
        // Parse app IDs to list of App objects
        addAppsList(appIds);

        return u;
    }

    public static void addAppsList(JSONArray appIds) {
        try {
            // For each app id, get app data and create App object
            for (int i = 0; i < appIds.length(); i++) {
                getApp(appIds.get(i).toString(), new App.CompletionListener() {
                    @Override
                    public void responseReceived(App app) {
                        Log.d(TAG, "Adding app");
                        User us = User.getInstance();
                        us.addApp(app);
                    }
                    @Override
                    public void noResponseReceived() {
                        // TODO: Handle error
                        Log.d(TAG, "No response received");
                    }
                });
            }
        } catch (JSONException e) {
            // TODO: Handle exception
            e.printStackTrace();
        }
    }

    // Parse ``ParseObject'' object to ``App'' object
    public static App parseApp(ParseObject app) {
        App a = new App();
        a.setId(app.getObjectId());
        a.setName(app.getString("name"));
        a.setWelcomeMessage(app.getString("welcome_message"));
        a.setAppImgUrl(app.getParseFile("icon").getUrl());
        return a;
    }

    public static void initParse(Context context) {
        // Enable Local Datastore.
        Parse.enableLocalDatastore(context);
        Parse.initialize(context, "1A5G2YPnZmvYoDII7wkZYoMPk0NKm3JKUAiXPsD2", "jFusc2WaHUGIDscXCjsIWiJQ00yv9i62jzsyIwjq");
    }
}
