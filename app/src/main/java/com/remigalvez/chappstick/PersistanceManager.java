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

    public PersistanceManager(Context context) {
        mContext = context;
    }

    public void saveCreditCard(String ccNumber, String ccMonth, String ccYear) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(Constants.CC_NUMBER_PREF_NAME, ccNumber);
        editor.putString(Constants.CC_MONTH_PREF_NAME, ccMonth);
        editor.putString(Constants.CC_YEAR_PREF_NAME, ccYear);
        editor.apply();
    }

    public String loadCreditCardNumber() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return sharedPreferences.getString(Constants.CC_NUMBER_PREF_NAME, "-");
    }

    public String loadCreditCardMonth() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return sharedPreferences.getString(Constants.CC_MONTH_PREF_NAME, "-");
    }

    public String loadCreditCardYear() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return sharedPreferences.getString(Constants.CC_YEAR_PREF_NAME, "-");
    }


}