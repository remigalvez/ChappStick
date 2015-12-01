package com.remigalvez.chappstick.objects;

/**
 * Created by Remi on 11/12/15.
 */
public class App {
    public static final String TAG = "App";

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

    public String getWelcomeMessage() {
        return mWelcomeMessage;
    }

    public void setWelcomeMessage(String welcomeMessage) {
        mWelcomeMessage = welcomeMessage;
    }

    @Override
    public boolean equals(Object object) {
        boolean isEqual = false;
        if (object != null && object instanceof App) {
            String thisId = this.getId();
            String thatId = ((App) object).getId();
            isEqual = thisId.equals(thatId);
        }
        return isEqual;
    }
}
