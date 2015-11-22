package com.remigalvez.chappstick.objects;

import android.graphics.drawable.Drawable;

/**
 * Created by Remi on 11/12/15.
 */
public class App {

    private String mName;
    private String mAppImgUrl;
    private String mId;
    private int count;

    public String getName() {
        return mName;
    }

    public String getAppImgUrl() {
        return mAppImgUrl;
    }

    public String setId(String id) {
        mId = id;
    }

    public void setName(String title) {
        this.mName = title;
    }

    public void setAppImgUrl(String url) {

    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
