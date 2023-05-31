package com.app.sportbetting.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {
    private static final String SHARED_PREF = "Workplace"; //change app name here...
    private static SharedPreference sSharedPrefs;
    private final SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;

    public enum Key {
        TOKEN,ISUSERLOGIN,FIRSTNAME,LASTNAME,USERID,ISLANDINGCOMPLETE,DEVICETOKEN,USERNAME,EMAIL,PASSWORD,PROFILEIMAGE,ONBOARDINGCOMPLETED,ISCHECKED,ISSUBSCRIBED,TIPPING
    }

    private SharedPreference(Context context) {
        mPref = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
    }

    public static SharedPreference getInstance(Context context) {
        if (sSharedPrefs == null) {
            sSharedPrefs = new SharedPreference(context);
        }
        return sSharedPrefs;
    }

    public void put(Key key, String val) {
        doEdit();
        mEditor.putString(key.name(), val);
        doApply();
    }

    public void put(Key key, int val) {
        doEdit();
        mEditor.putInt(key.name(), val);
        doApply();
    }

    public void put(Key key, boolean val) {
        doEdit();
        mEditor.putBoolean(key.name(), val);
        doApply();
    }

    public String getString(Key key, String defaultValue) {
        return mPref.getString(key.name(), defaultValue);
    }

    public int getInteger(Key key, int defaultValue) {
        return mPref.getInt(key.name(), defaultValue);
    }

    public boolean getBoolean(Key key, boolean defaultValue) {
        return mPref.getBoolean(key.name(), defaultValue);
    }

    @SuppressLint("CommitPrefEdits")
    private void doEdit() {
        if (mEditor == null) {
            mEditor = mPref.edit();
        }
    }

    private void doApply() {
        if (mEditor != null) {
            mEditor.commit();
            mEditor = null;
        }
    }

    public void clear() {
        doEdit();
        mEditor.clear();
        doApply();
    }

}
