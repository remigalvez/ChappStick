package com.remigalvez.chappstick.util;

import android.content.Context;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.remigalvez.chappstick.activity.AddAppsActivity;
import com.remigalvez.chappstick.constant.Constants;
import com.remigalvez.chappstick.constant.ParseKey;
import com.remigalvez.chappstick.objects.App;
import com.remigalvez.chappstick.objects.User;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Remi on 11/26/15.
 */
public class DatabaseUtils {
    private static final String TAG = "DatabaseUtils";
    private static ParseUser sParseUser;

    // For empty responses
    public interface CompletionListener {
        void responseReceived();

        void noResponseReceived();
    }

    public static void appendAppsList(List<App> apps) {
        List<String> appIds = new ArrayList<>();
        for (int i = 0; i < apps.size(); i++) {
            appIds.add(apps.get(i).getId());
        }
        sParseUser.addAll(ParseKey.USER_APPS, appIds);
        sParseUser.saveInBackground();
    }

    public static void saveUserChanges(User user) {
        sParseUser.put(ParseKey.FIRST_NAME, user.getFirstName());
        sParseUser.put(ParseKey.LAST_NAME, user.getLastName());
        sParseUser.saveInBackground();
    }

    public static void removeAppsList(List<App> apps) {
        List<String> appIds = new ArrayList<>();
        for (int i = 0; i < apps.size(); i++) {
            appIds.add(apps.get(i).getId());
        }
        sParseUser.removeAll(ParseKey.USER_APPS, appIds);
        sParseUser.saveInBackground();
    }

    public static void getUser(String username, String password, final User.CompletionListener completionListener) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null) {
                    sParseUser =  user;
                    User u = parseUserToObject(user);
                    completionListener.responseReceived(u);
                } else {
                    completionListener.noResponseReceived();
                }
            }
        });
    }

    public static void getAllApps(final AddAppsActivity.CompletionListener completionListener) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseKey.APP);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> apps, ParseException e) {
                if (e == null) {
                    List<App> appsList = parseAppsList(apps);
                    completionListener.responseReceived(appsList);
                } else {
                    completionListener.noResponseReceived();
                    Log.d(TAG, "Error getting all apps...");
                }
            }
        });
    }

    public static void getApp(String appId, final App.CompletionListener completionListener) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseKey.APP);
        query.getInBackground(appId, new GetCallback<ParseObject>() {
            public void done(ParseObject app, ParseException e) {
                if (e == null) {
                    App a = parseApp(app);
                    completionListener.responseReceived(a);
                } else {
                    completionListener.noResponseReceived();
                }
            }
        });
    }

    public static void signup(User user, final DatabaseUtils.CompletionListener completionListener) {
        ParseUser parseUser = new ParseUser();
        parseUser.setUsername(user.getEmail());
        parseUser.setPassword(user.getPassword());
        parseUser.setEmail(user.getEmail());
        parseUser.put(ParseKey.FIRST_NAME, user.getFirstName());
        parseUser.put(ParseKey.LAST_NAME, user.getLastName());
        parseUser.put(ParseKey.USER_APPS, (new JSONArray()).put(ParseKey.DEFAULT_APP_ID));
        parseUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    completionListener.responseReceived();
                } else {
                    Log.d(TAG, e.getMessage());
                    completionListener.noResponseReceived();
                }
            }
        });
    }

    // Parse ``ParseUser'' object to ``User'' object
    public static User parseUserToObject(ParseUser user) {
        User u = new User();
        u.setId(user.getObjectId());
        u.setFirstName(user.getString(ParseKey.FIRST_NAME));
        u.setLastName(user.getString(ParseKey.LAST_NAME));
        u.setEmail(user.getEmail());

        // Get list of app ids
        JSONArray appIds = user.getJSONArray(ParseKey.USER_APPS);
        // Parse app IDs to list of App objects
        getUserAppsList(appIds);

        return u;
    }

    public static void getUserAppsList(JSONArray appIds) {
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
                        Log.d(TAG, "No response received");
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static List<App> parseAppsList(List<ParseObject> apps) {
        List<App> appsList = new ArrayList<>();
        for (int i = 0; i < apps.size(); i++) {
            App app = parseApp((apps.get(i)));
            appsList.add(app);
        }
        return appsList;
    }

    // Parse ``ParseObject'' object to ``App'' object
    public static App parseApp(ParseObject app) {
        App a = new App();
        a.setId(app.getObjectId());
        a.setName(app.getString(ParseKey.APP_NAME));
        a.setWelcomeMessage(app.getString(ParseKey.WELCOME_MESSAGE));
        a.setAppImgUrl(app.getParseFile(ParseKey.ICON).getUrl());
        return a;
    }

    public static void initParse(Context context) {
        try {
            // Enable Local Datastore.
            Parse.enableLocalDatastore(context);
            Parse.initialize(context, Constants.PARSE_APP_ID, Constants.PARSE_CLIENT_KEY);
        } catch (Exception e) {
            Log.d(TAG, "Parse already initialized.");
        }
    }

}
