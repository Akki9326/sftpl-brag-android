package com.pulse.brag.data;

import android.app.Application;
import android.content.Context;

import com.pulse.brag.data.local.AppPrefsManager;
import com.pulse.brag.data.local.IPreferenceManager;
import com.pulse.brag.data.remote.IApiManager;
import com.pulse.brag.pojo.DummeyRespone;
import com.pulse.brag.pojo.GeneralResponse;
import com.pulse.brag.pojo.datas.UserData;
import com.pulse.brag.pojo.requests.ChangeMobileNumberRequest;
import com.pulse.brag.pojo.requests.ChangePasswordRequest;
import com.pulse.brag.pojo.requests.LoginRequest;
import com.pulse.brag.pojo.requests.SignInRequest;
import com.pulse.brag.pojo.response.CartListResponse;
import com.pulse.brag.pojo.response.CategoryListResponse;
import com.pulse.brag.pojo.response.ChangePasswordResponse;
import com.pulse.brag.pojo.response.CollectionListResponse;
import com.pulse.brag.pojo.response.LoginResponse;
import com.pulse.brag.pojo.response.MyOrderListRespone;
import com.pulse.brag.pojo.response.OTPVerifyResponse;
import com.pulse.brag.pojo.response.SignUpResponse;

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
        this.mApiManager = mApiManager;
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
    public Call<GeneralResponse> changeMobileNum(ChangeMobileNumberRequest changeMobileNumberRequest) {
        return mApiManager.changeMobileNum(changeMobileNumberRequest);
    }

    @Override
    public Call<CategoryListResponse> getCategoryProduct(String url) {
        return mApiManager.getCategoryProduct(url);
    }

    @Override
    public Call<CollectionListResponse> getCollectionProduct(String url) {
        return mApiManager.getCollectionProduct(url);
    }

    @Override
    public Call<CartListResponse> getCartList(String url) {
        return mApiManager.getCartList(url);
    }

    @Override
    public Call<MyOrderListRespone> getOrderList(String url) {
        return mApiManager.getOrderList(url);
    }

    @Override
    public Call<LoginResponse> userLogin(LoginRequest loginRequest) {
        return mApiManager.userLogin(loginRequest);
    }

    @Override
    public Call<DummeyRespone> getProductionList(int page) {
        return mApiManager.getProductionList(page);
    }

    @Override
    public Call<SignUpResponse> userSignIn(SignInRequest signInRequest) {
        return mApiManager.userSignIn(signInRequest);
    }

    @Override
    public Call<OTPVerifyResponse> verifyOtp(String mobile, String otp) {
        return mApiManager.verifyOtp(mobile, otp);
    }

    @Override
    public Call<OTPVerifyResponse> verifyOtpForgetPass(String mobile, String otp) {
        return mApiManager.verifyOtpForgetPass(mobile, otp);
    }

    @Override
    public Call<GeneralResponse> resendOtp(String mobile) {
        return mApiManager.resendOtp(mobile);
    }

    @Override
    public Call<ChangePasswordResponse> changePassword(ChangePasswordRequest changePasswordRequest) {
        return mApiManager.changePassword(changePasswordRequest);
    }

    @Override
    public Call<ChangePasswordResponse> resetPassword(ChangePasswordRequest changePasswordRequest) {
        return mApiManager.resetPassword(changePasswordRequest);
    }

    @Override
    public Call<GeneralResponse> logoutCall() {
        return mApiManager.logoutCall();
    }
}