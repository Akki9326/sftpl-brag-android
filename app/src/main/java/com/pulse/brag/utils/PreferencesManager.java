package com.pulse.brag.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.pulse.brag.pojo.datas.UserData;


public class PreferencesManager {

    private static final String PREF_NAME = "com.pulse.brag.PREF_NAME";
    private static PreferencesManager sInstance;

    private final SharedPreferences mPref;
    public String POST_QUERY_CONTENT_ID = "postQueryContentId";

    private static final String IS_LOGIN = "isLogin";
    public String SELECTED_CONTENT_CATEGORY_POSITION = "selectedContentPosition";
    private static final String ACCESS_TOKEN = "accessToken";
    private static final String DEVICE_TOKEN = "deviceToken";
    private static final String DEVICE_TYPE = "deviceType";
    private static final String OS_VERSION = "osVersion";
    private static final String OS = "os";
    private static final String USER_DATA = "userdata";
    private static final String NOTIFICATION_ID ="notificationid" ;


    private PreferencesManager(Context context) {
        mPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized PreferencesManager getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException(PreferencesManager.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }
        return sInstance;
    }

    public static synchronized void initializeInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PreferencesManager(context);
        }
    }


    public void setIsLogin(boolean isLogin) {
        mPref.edit().putBoolean(IS_LOGIN, isLogin).commit();
    }

    public boolean isLogin() {
        return mPref.getBoolean(IS_LOGIN, false);
    }

    public void setSELECTED_CONTENT_CATEGORY_POSITION(String value) {
        mPref.edit().putString(SELECTED_CONTENT_CATEGORY_POSITION, value).commit();
    }

    public String getPOST_QUERY_CONTENT_ID() {
        return mPref.getString(POST_QUERY_CONTENT_ID, "1");
    }

    public void setPOST_QUERY_CONTENT_ID(String value) {
        mPref.edit().putString(POST_QUERY_CONTENT_ID, value).commit();
    }

    public String getSELECTED_CONTENT_CATEGORY_POSITION() {
        return mPref.getString(SELECTED_CONTENT_CATEGORY_POSITION, "0");
    }

    public void setAccessToken(String token) {
        mPref.edit().putString(ACCESS_TOKEN, token).commit();
    }

    public String getAccessToken() {
        return mPref.getString(ACCESS_TOKEN, "");
    }

    public void setDeviceToken(String deviceToken) {
        mPref.edit().putString(DEVICE_TOKEN, deviceToken).commit();
    }

    public int getNotificationId() {
        return mPref.getInt(NOTIFICATION_ID, 0);
    }

    public void setNotificationId(int notificationId) {

        mPref.edit().putInt(NOTIFICATION_ID, notificationId).commit();
    }

    public String getDeviceToken() {
        return mPref.getString(DEVICE_TOKEN, "");
    }

    public void setDeviceTypeAndOsVer(String deviceName, String osVersion) {
        mPref.edit().putString(DEVICE_TYPE, deviceName).commit();
        mPref.edit().putString(OS_VERSION, osVersion).commit();
    }

    public String getOsVersion() {
        return mPref.getString(OS_VERSION, "");
    }

    public String getDeviceType() {
        return mPref.getString(DEVICE_TYPE, "");
    }

    public void setUserData(String s) {
        mPref.edit().putString(USER_DATA, s).commit();
    }

    public UserData getUserData() {
        return new Gson().fromJson(mPref.getString(USER_DATA, ""), UserData.class);
    }

    public void logout() {
        mPref.edit().clear().commit();
    }
}
