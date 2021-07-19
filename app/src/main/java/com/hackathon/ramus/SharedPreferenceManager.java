package com.hackathon.ramus;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager {

    public static final String PREFERENCE_DATA = "EMAIL";
    public static final boolean DEFAULT_VALUE_BOOLEAN = false;
    public static final String DEFAULT_EMAIL_VALUE = "NOT_REGISTERED";
    public static final String IS_EMAIL_REGISTERED ="IS_EMAIL_REGISTERED";
    public static final String KNU_EMAIL = "KNU_EMAIL";

    private static SharedPreferences getPreferences(Context context){
        return context.getSharedPreferences(PREFERENCE_DATA,Context.MODE_PRIVATE);
    }

    public static void setEmail(Context context,String key,String email){
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key,email);
        editor.commit();
    }

    public static String getEmail(Context context,String key){
        SharedPreferences prefs = getPreferences(context);
        String email = prefs.getString(key,DEFAULT_EMAIL_VALUE);
        return email;
    }

    public static void setFirstCheckBooleanData(Context context, String key, boolean value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getFirstCheckBooleanData(Context context, String key) {
        SharedPreferences prefs = getPreferences(context);
        boolean value = prefs.getBoolean(key, DEFAULT_VALUE_BOOLEAN);
        return value;
    }
}
