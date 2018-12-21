package com.example.user.catalogfilm.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreference {

    private String DAILY_REMINDER = "daily_reminder";
    private String DAILY_TODAY_RELEASE = "daily_today_release";

    private SharedPreferences preferences;

    public UserPreference(Context context) {
        String PREFS_NAME = "UserPref";
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void setDailyReminder(boolean status) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(DAILY_REMINDER, status);
        editor.apply();
    }

    public boolean getDailyReminder(){
        return preferences.getBoolean(DAILY_REMINDER, false);
    }

    public void setDailyTodayRelease(boolean status){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(DAILY_TODAY_RELEASE, status);
        editor.apply();
    }

   public boolean getDailyTodayRelease(){
        return preferences.getBoolean(DAILY_TODAY_RELEASE, false);
    }

}
