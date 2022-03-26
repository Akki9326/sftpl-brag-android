package com.ragtagger.brag.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.ragtagger.brag.di.PreferenceInfo;
import com.ragtagger.brag.data.model.datas.DataUser;

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

    private final SharedPreferences sharedPreferences;

    @Inject
    public AppPrefsManager(Context context, @PreferenceInfo String prefFileName) {
        this.sharedPreferences = context.getSharedPreferences(prefFileName,Context.MODE_PRIVATE);
    }

    @Override
    public void setIsLogin(boolean isLogin) {
        sharedPreferences.edit().putBoolean(IS_LOGIN, isLogin).commit();
    }

    @Override
    public boolean isLogin() {
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    @Override
    public void setAccessToken(String token) {
        sharedPreferences.edit().putString(ACCESS_TOKEN, token).commit();
    }

    @Override
    public String getAccessToken() {
        return sharedPreferences.getString(ACCESS_TOKEN, "");
    }

    @Override
    public void setDeviceToken(String deviceToken) {
        sharedPreferences.edit().putString(DEVICE_TOKEN, deviceToken).commit();
    }

    @Override
    public String getDeviceToken() {
        return sharedPreferences.getString(DEVICE_TOKEN, "");
    }

    @Override
    public void setNotificationId(int notificationId) {
        sharedPreferences.edit().putInt(NOTIFICATION_ID, notificationId).commit();
    }

    @Override
    public int getNotificationId() {
        return sharedPreferences.getInt(NOTIFICATION_ID, 0);
    }

    @Override
    public void setDeviceTypeAndOsVer(String deviceName, String osVersion) {
        sharedPreferences.edit().putString(DEVICE_TYPE, deviceName).commit();
        sharedPreferences.edit().putString(OS_VERSION, osVersion).commit();
    }

    @Override
    public String getOsVersion() {
        return sharedPreferences.getString(OS_VERSION, "");
    }

    @Override
    public String getDeviceType() {
        return sharedPreferences.getString(DEVICE_TYPE, "");
    }

    @Override
    public void setUserData(String s) {
        sharedPreferences.edit().putString(USER_DATA, s).commit();
    }

    @Override
    public DataUser getUserData() {
        return new Gson().fromJson(sharedPreferences.getString(USER_DATA, ""), DataUser.class);
    }

    @Override
    public void logout() {
        sharedPreferences.edit().clear().commit();
    }
}
