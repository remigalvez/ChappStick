package com.remigalvez.chappstick.service;

import android.os.Bundle;

import com.remigalvez.chappstick.activity.MessagingActivity;

/**
 * Created by Remi on 11/11/15.
 */
public class Uber extends MessagingActivity {

    public void Uber() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Notify server of log on
        // Return app name and welcome message
        // *** Temporarily hard coded *** //
        this.mAppName = "Uber";
        this.mWelcomeMessage = "Hi Remi, welcome to Uber!";
        setTitle(mAppName);
        this.sendWelcomeMessage(mWelcomeMessage);
    }

}
