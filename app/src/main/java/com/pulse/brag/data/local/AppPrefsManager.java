package com.pulse.brag.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.pulse.brag.di.PreferenceInfo;
import com.pulse.brag.data.model.datas.UserData;

import javax.inject.Inject;

/**
 * Created by alpesh.rathod on 2/9/2018.
 */

public class AppPrefsManager implements IPreferenceManager {

    //public static final String PREF_NAME = "com.pulse.brag.PREF_NAME";

    private static final String IS_LOGIN = "isLogin";
    private static final String DEVICE_TYPE = "deviceType";
    private static final String OS_VERSION = "osVersion";
    private static final String ACCESS_TOKEN = "accessToken";
    private static final String DEVICE_TOKEN = "deviceToken";
    private static final String NOTIFICATION_ID ="notificationid" ;
    private static final String USER_DATA = "userdata";

    private final SharedPreferences mPref;

    @Inject
    public AppPrefsManager(Context context, @PreferenceInfo String prefFileName) {
        this.mPref = context.getSharedPreferences(prefFileName,Context.MODE_PRIVATE);
    }

    @Override
    public void setIsLogin(boolean isLogin) {
        mPref.edit().putBoolean(IS_LOGIN, isLogin).commit();
    }

    @Override
    public boolean isLogin() {
        return mPref.getBoolean(IS_LOGIN, false);
    }

    @Override
    public void setAccessToken(String token) {
        mPref.edit().putString(ACCESS_TOKEN, token).commit();
    }

    @Override
    public String getAccessToken() {
        return mPref.getString(ACCESS_TOKEN, "");
    }

    @Override
    public void setDeviceToken(String deviceToken) {
        mPref.edit().putString(DEVICE_TOKEN, deviceToken).commit();
    }

    @Override
    public String getDeviceToken() {
        return mPref.getString(DEVICE_TOKEN, "");
    }

    @Override
    public void setNotificationId(int notificationId) {
        mPref.edit().putInt(NOTIFICATION_ID, notificationId).commit();
    }

    @Override
    public int getNotificationId() {
        return mPref.getInt(NOTIFICATION_ID, 0);
    }

    @Override
    public void setDeviceTypeAndOsVer(String deviceName, String osVersion) {
        mPref.edit().putString(DEVICE_TYPE, deviceName).commit();
        mPref.edit().putString(OS_VERSION, osVersion).commit();
    }

    @Override
    public String getOsVersion() {
        return mPref.getString(OS_VERSION, "");
    }

    @Override
    public String getDeviceType() {
        return mPref.getString(DEVICE_TYPE, "");
    }

    @Override
    public void setUserData(String s) {
        mPref.edit().putString(USER_DATA, s).commit();
    }

    @Override
    public UserData getUserData() {
        return new Gson().fromJson(mPref.getString(USER_DATA, ""), UserData.class);
    }

    @Override
    public void logout() {
        mPref.edit().clear().commit();
    }
}
