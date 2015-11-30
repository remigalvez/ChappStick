package com.remigalvez.chappstick.objects;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Remi on 11/12/15.
 */
public class App {

    private String mName;
    private String mAppImgUrl;
    private String mId;
    private String mWelcomeMessage;
    private int count;
    private String mObjectId;

    public interface CompletionListener {
        void responseReceived(App app);
        void noResponseReceived();
    }

    public String getName() {
        return mName;
    }

    public String getAppImgUrl() {
        return mAppImgUrl;
    }

    public void setId(String id) {
        mId = id;
    }

    public void setName(String title) {
        this.mName = title;
    }

    public void setAppImgUrl(String url) {
        mAppImgUrl = url;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getId() {
        return mId;
    }

    public static App createFromJSON(JSONObject data) {
        App app = new App();
        try {
            app.setId(data.getString("objectId"));
            app.setName(data.getString("name"));
            app.setWelcomeMessage(data.getString("welcome_message"));
            app.setAppImgUrl(data.getJSONObject("icon").getString("url"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return app;
    }

    public String getWelcomeMessage() {
        return mWelcomeMessage;
    }

    public void setWelcomeMessage(String welcomeMessage) {
        mWelcomeMessage = welcomeMessage;
    }
}
