package com.remigalvez.chappstick;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.remigalvez.chappstick.constant.Constants;

/**
 * Created by Remi on 12/6/15.
 */
public class PersistanceManager {

    private Context mContext;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    public PersistanceManager(Context context) {
        mContext = context;
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mSharedPreferences.edit();
    }

    public void saveCredentials(String username, String password) {
        mEditor.putString(Constants.USERNAME, username);
        mEditor.putString(Constants.PASSWORD, password);
        mEditor.apply();
    }

    public String loadUsername() {
        return mSharedPreferences.getString(Constants.USERNAME, "");
    }

    public String loadPassword() {
        return mSharedPreferences.getString(Constants.PASSWORD, "");
    }

    public void saveCreditCard(String ccNumber, String ccMonth, String ccYear) {
        mEditor.putString(Constants.CC_NUMBER_PREF_NAME, ccNumber);
        mEditor.putString(Constants.CC_MONTH_PREF_NAME, ccMonth);
        mEditor.putString(Constants.CC_YEAR_PREF_NAME, ccYear);
        mEditor.apply();
    }

    public String loadCreditCardNumber() {
        return mSharedPreferences.getString(Constants.CC_NUMBER_PREF_NAME, "");
    }

    public String loadCreditCardMonth() {
        return mSharedPreferences.getString(Constants.CC_MONTH_PREF_NAME, "");
    }

    public String loadCreditCardYear() {
        return mSharedPreferences.getString(Constants.CC_YEAR_PREF_NAME, "");
    }

    public void clear() {
        mEditor.clear();
        mEditor.apply();
    }
}
