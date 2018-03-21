package com.ragtagger.brag.data;

import android.app.Application;
import android.content.Context;

import com.ragtagger.brag.data.local.AppPrefsManager;
import com.ragtagger.brag.data.local.IPreferenceManager;
import com.ragtagger.brag.data.model.datas.DataUser;
import com.ragtagger.brag.data.model.requests.QAddAddress;
import com.ragtagger.brag.data.model.requests.QAddToCart;
import com.ragtagger.brag.data.model.requests.QChangeMobileNumber;
import com.ragtagger.brag.data.model.requests.QChangePassword;
import com.ragtagger.brag.data.model.requests.QContactUs;
import com.ragtagger.brag.data.model.requests.QGenerateOtpForChangeMobile;
import com.ragtagger.brag.data.model.requests.QGetFilter;
import com.ragtagger.brag.data.model.requests.QLogin;
import com.ragtagger.brag.data.model.requests.QPlaceOrder;
import com.ragtagger.brag.data.model.requests.QProductList;
import com.ragtagger.brag.data.model.response.RAddToCart;
import com.ragtagger.brag.data.model.response.RChangePassword;
import com.ragtagger.brag.data.model.response.RFilter;
import com.ragtagger.brag.data.model.response.RMyOrder;
import com.ragtagger.brag.data.model.response.RNotification;
import com.ragtagger.brag.data.model.response.RProductList;
import com.ragtagger.brag.data.model.requests.QAddAddress;
import com.ragtagger.brag.data.model.response.RStateList;
import com.ragtagger.brag.data.model.response.RUserAddress;
import com.ragtagger.brag.data.remote.IApiManager;
import com.ragtagger.brag.data.model.response.RGeneralData;
import com.ragtagger.brag.data.model.datas.DataUser;
import com.ragtagger.brag.data.model.requests.QChangeMobileNumber;
import com.ragtagger.brag.data.model.requests.QChangePassword;
import com.ragtagger.brag.data.model.requests.QLogin;
import com.ragtagger.brag.data.model.requests.QSignUp;
import com.ragtagger.brag.data.model.response.RAddToCart;
import com.ragtagger.brag.data.model.response.RCartList;
import com.ragtagger.brag.data.model.response.RCategoryList;
import com.ragtagger.brag.data.model.response.RChangePassword;
import com.ragtagger.brag.data.model.response.RCollectionList;
import com.ragtagger.brag.data.model.response.RFilter;
import com.ragtagger.brag.data.model.response.RGeneralData;
import com.ragtagger.brag.data.model.response.RLogin;
import com.ragtagger.brag.data.model.response.RMyOrder;
import com.ragtagger.brag.data.model.response.ROTPVerify;
import com.ragtagger.brag.data.model.response.ROrderDetail;
import com.ragtagger.brag.data.model.response.RProductList;
import com.ragtagger.brag.data.model.response.RSignUp;
import com.ragtagger.brag.data.model.response.RStateList;
import com.ragtagger.brag.data.model.response.RUserAddress;
import com.ragtagger.brag.data.remote.IApiManager;

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
     * @param mContext           {@link com.ragtagger.brag.di.module.AppModule#provideContext(Application)}
     * @param mPreferencesHelper {@link com.ragtagger.brag.di.module.AppModule#providePreferenceHelper(AppPrefsManager)}
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
    public DataUser getUserData() {
        return mPreferencesHelper.getUserData();
    }

    @Override
    public void logout() {
        mPreferencesHelper.logout();
    }

    @Override
    public Call<RGeneralData> changeMobileNum(QChangeMobileNumber changeMobileNumberRequest) {
        return mApiManager.changeMobileNum(changeMobileNumberRequest);
    }

    @Override
    public Call<RCategoryList> getCategoryProduct() {
        return mApiManager.getCategoryProduct();
    }

    @Override
    public Call<RCollectionList> getCollectionProduct() {
        return mApiManager.getCollectionProduct();
    }

    @Override
    public Call<RGeneralData> notifyMe(String itemNo) {
        return mApiManager.notifyMe(itemNo);
    }

    @Override
    public Call<RFilter> getFilter(QGetFilter reqGetFilter) {
        return mApiManager.getFilter(reqGetFilter);
    }

    @Override
    public Call<RAddToCart> addToCart(QAddToCart addToCart) {
        return mApiManager.addToCart(addToCart);
    }

    @Override
    public Call<RGeneralData> contactUs(QContactUs qContactUs) {
        return mApiManager.contactUs(qContactUs);
    }

    @Override
    public Call<RCartList> getCartList(String url) {
        return mApiManager.getCartList(url);
    }

    @Override
    public Call<RMyOrder> getOrderList(int page) {
        return mApiManager.getOrderList(page);
    }


    @Override
    public Call<ROrderDetail> getOrderDetail(String orderId) {
        return mApiManager.getOrderDetail(orderId);
    }

    @Override
    public Call<RUserAddress> addAddress(QAddAddress addAddress) {
        return mApiManager.addAddress(addAddress);
    }

    @Override
    public Call<RLogin> updateProfile(DataUser userData) {
        return mApiManager.updateProfile(userData);
    }

    @Override
    public Call<RStateList> getStateList(String url) {
        return mApiManager.getStateList(url);
    }

    @Override
    public Call<RGeneralData> removeFromCart(String id) {
        return mApiManager.removeFromCart(id);
    }

    @Override
    public Call<RLogin> getUserProfile(String s) {
        return mApiManager.getUserProfile(s);
    }

    @Override
    public Call<RGeneralData> placeOrder(QPlaceOrder placeOrder) {
        return mApiManager.placeOrder(placeOrder);
    }


    public Call<RGeneralData> reOrder(String id) {
        return mApiManager.reOrder(id);
    }

    @Override
    public Call<RNotification> getNotificationList(int page) {
        return mApiManager.getNotificationList(page);
    }

    @Override
    public Call<RGeneralData> notificationRead(String id) {
        return mApiManager.notificationRead(id);
    }

    @Override
    public Call<RLogin> userLogin(QLogin loginRequest) {
        return mApiManager.userLogin(loginRequest);
    }

    @Override
    public Call<RProductList> getProductionList(int page, QProductList body) {
        return mApiManager.getProductionList(page, body);
    }

    @Override
    public Call<RSignUp> userSignIn(QSignUp signInRequest) {
        return mApiManager.userSignIn(signInRequest);
    }

    @Override
    public Call<ROTPVerify> verifyOtp(String mobile, String otp) {
        return mApiManager.verifyOtp(mobile, otp);
    }

    @Override
    public Call<ROTPVerify> verifyOtpForgetPass(String mobile, String otp) {
        return mApiManager.verifyOtpForgetPass(mobile, otp);
    }

    @Override
    public Call<RGeneralData> resendOtp(String mobile) {
        return mApiManager.resendOtp(mobile);
    }

    @Override
    public Call<RGeneralData> resendForgotOtp(String mobile) {
        return mApiManager.resendForgotOtp(mobile);
    }

    @Override
    public Call<RChangePassword> changePassword(com.ragtagger.brag.data.model.requests.QChangePassword changePasswordRequest) {
        return mApiManager.changePassword(changePasswordRequest);
    }

    @Override
    public Call<RChangePassword> resetPassword(QChangePassword changePasswordRequest) {
        return mApiManager.resetPassword(changePasswordRequest);
    }

    @Override
    public Call<RGeneralData> logoutCall() {
        return mApiManager.logoutCall();
    }

    @Override
    public Call<RGeneralData> generateOTPForMobileChange(QGenerateOtpForChangeMobile generateOtpForChangeMobile) {
        return mApiManager.generateOTPForMobileChange(generateOtpForChangeMobile);
    }

}
