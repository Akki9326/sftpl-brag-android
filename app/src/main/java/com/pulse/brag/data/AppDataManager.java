package com.pulse.brag.data;

import android.app.Application;
import android.content.Context;

import com.pulse.brag.data.local.AppPrefsManager;
import com.pulse.brag.data.local.IPreferenceManager;
import com.pulse.brag.data.remote.IApiManager;
import com.pulse.brag.pojo.datas.UserData;
import com.pulse.brag.pojo.requests.LoginRequest;
import com.pulse.brag.pojo.response.LoginResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;

/**
 * Created by alpesh.rathod on 2/9/2018.
 */

@Singleton
public class AppDataManager implements IDataManager {

    private final Context mContext;
    private final IPreferenceManager mPreferencesHelper;
    private final IApiManager mApiManager;


    /**
     * @param mContext           {@link com.pulse.brag.di.module.AppModule#provideContext(Application)}
     * @param mPreferencesHelper {@link com.pulse.brag.di.module.AppModule#providePreferenceHelper(AppPrefsManager)}
     */
    @Inject
    public AppDataManager(Context mContext, IPreferenceManager mPreferencesHelper, IApiManager mApiManager) {
        this.mContext = mContext;
        this.mPreferencesHelper = mPreferencesHelper;
        this.mApiManager=mApiManager;
    }

    @Override
    public void setIsLogin(boolean isLogin) {
        mPreferencesHelper.setIsLogin(isLogin);
    }

    @Override
    public boolean isLogin() {
        return mPreferencesHelper.isLogin();
    }

    @Override
    public void setAccessToken(String token) {
        mPreferencesHelper.setAccessToken(token);
    }

    @Override
    public String getAccessToken() {
        return mPreferencesHelper.getAccessToken();
    }

    @Override
    public void setDeviceToken(String deviceToken) {
        mPreferencesHelper.setDeviceToken(deviceToken);
    }

    @Override
    public String getDeviceToken() {
        return mPreferencesHelper.getDeviceToken();
    }

    @Override
    public void setNotificationId(int notificationId) {
        mPreferencesHelper.setNotificationId(notificationId);
    }

    @Override
    public int getNotificationId() {
        return mPreferencesHelper.getNotificationId();
    }

    @Override
    public void setDeviceTypeAndOsVer(String deviceName, String osVersion) {
        mPreferencesHelper.setDeviceTypeAndOsVer(deviceName, osVersion);
    }

    @Override
    public String getOsVersion() {
        return mPreferencesHelper.getOsVersion();
    }

    @Override
    public String getDeviceType() {
        return mPreferencesHelper.getDeviceType();
    }

    @Override
    public void setUserData(String s) {
        mPreferencesHelper.setUserData(s);
    }

    @Override
    public UserData getUserData() {
        return mPreferencesHelper.getUserData();
    }

    @Override
    public void logout() {
        mPreferencesHelper.logout();
    }

    @Override
    public Call<LoginResponse> userLogin(LoginRequest loginRequest) {
        return mApiManager.userLogin(loginRequest);
    }
}
