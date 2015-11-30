package com.remigalvez.chappstick;

import android.util.Log;

import com.remigalvez.chappstick.activity.HomescreenActivity;
import com.remigalvez.chappstick.objects.App;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Remi on 11/21/15.
 */
public class User {
    public static final String TAG = "User";

    public static User USER;

    private List<App> mApps;
    private String mUserId;

    public interface CompletionListener {
        void responseReceived(User user);
        void noResponseReceived();
    }

    public User(String username, String password) {
        // Get user from Parse
        ParseUtils.getUser(username, password, new User.CompletionListener() {
            @Override
            public void responseReceived(User user) {
                // Notify homescreen of new user
                if (HomescreenActivity.getInstance() != null) {
                    HomescreenActivity.getInstance().setUser(user);
                }
            }
            @Override
            public void noResponseReceived() {
                // TODO: Handle error
                Log.d(TAG, "No User received...");
            }
        });
    }

    public User() {
        USER = this;
        mApps = new ArrayList<>();
    }

    public void addApp(App app) {
        mApps.add(app);
        if (HomescreenActivity.getInstance() != null) {
            HomescreenActivity hs = HomescreenActivity.getInstance();
            hs.addItemToList(app);
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

    public List<App> getApps() {
        return mApps;
    }

    public void setApps(List<App> apps) {
        mApps = apps;
    }

    public String getId() {
        return mUserId;
    }

    public void setId(String userId) {
        this.mUserId = userId;
    }

    public static User getInstance() {
        return USER;
    }

}
