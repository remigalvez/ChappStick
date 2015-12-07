package com.remigalvez.chappstick.objects;

import android.util.Log;

import com.google.gson.Gson;
import com.remigalvez.chappstick.activity.HomescreenActivity;
import com.remigalvez.chappstick.util.DatabaseUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Remi on 11/21/15.
 */
public class User {
    public static final String TAG = "User";

    public static User USER;

    // Used to sign user up
    private String mFirstName;
    private String mLastName;
    private String mEmail;
    private String mPassword;


    private List<App> mApps;
    private String mUserId;

    public interface CompletionListener {
        void responseReceived(User user);
        void noResponseReceived();
    }

    public User(String username, String password) {
        // Get user from Parse
        DatabaseUtils.getUser(username, password, new User.CompletionListener() {
            @Override
            public void responseReceived(User user) {
                // Notify homescreen of new user
                if (HomescreenActivity.getInstance() != null) {
                    HomescreenActivity.getInstance().setUser(user);
                }
            }

            @Override
            public void noResponseReceived() {
                Log.d(TAG, "No User received...");
            }
        });
    }

    public User() {
        USER = this;
        mApps = new ArrayList<>();
    }

    // To sign user up
    public User(String firstName, String lastName, String email, String password) {
        mFirstName = firstName;
        mLastName = lastName;
        mEmail = email;
        mPassword = password;
    }

    public void addAppList(List<App> apps) {
        for (int i = 0; i < apps.size(); i++) {
            addApp(apps.get(i));
        }
    }

    public void removeFromAppList(List<App> apps) {
        for (int i = 0; i < apps.size(); i++) {
            removeApp(apps.get(i));
        }
    }

    public void removeApp(App app) {
        mApps.remove(app);
        if (HomescreenActivity.isActive()) {
            HomescreenActivity.getInstance().removeItemFromList(app);
        } else {
            Log.d(TAG, "Homescreen activity does not exist...");
        }
    }

    public void addApp(App app) {
        mApps.add(app);
        if (HomescreenActivity.isActive()) {
            HomescreenActivity.getInstance().addItemToList(app);
        } else {
            Log.d(TAG, "Homescreen activity does not exist...");
        }
    }

    public App getAppFromId(String appId) {
        for (int i = 0; i < mApps.size(); i++) {
            if (mApps.get(i).getId().equals(appId)) {
                return mApps.get(i);
            }
        }
        return null;
    }

    public static String toJson(User user) {
        Gson gson = new Gson();
        return gson.toJson(user);
    }

    public static User fromJson(String jsonUser) {
        return new Gson().fromJson(jsonUser, User.class);
    }

    public List<App> getApps() {
        return mApps;
    }

    public void setApps(List<App> apps) {
        mApps = apps;
    }

    public String getId() {
        return mUserId;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public void setId(String userId) {
        this.mUserId = userId;
    }

    public static void reset() {
        USER = new User();
    }

    public static User getInstance() {
        if (USER == null)
            USER = new User();
        return USER;
    }

}
