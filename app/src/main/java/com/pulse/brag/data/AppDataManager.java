package com.pulse.brag.data;

import android.app.Application;
import android.content.Context;

import com.pulse.brag.data.local.AppPrefsManager;
import com.pulse.brag.data.local.IPreferenceManager;
import com.pulse.brag.data.model.requests.QAddToCart;
import com.pulse.brag.data.model.requests.QContactUs;
import com.pulse.brag.data.model.requests.QGenerateOtpForChangeMobile;
import com.pulse.brag.data.model.requests.QGetFilter;
import com.pulse.brag.data.model.requests.QPlaceOrder;
import com.pulse.brag.data.model.requests.QProductList;
import com.pulse.brag.data.model.response.RAddToCart;
import com.pulse.brag.data.model.response.RFilter;
import com.pulse.brag.data.model.response.RMyOrder;
import com.pulse.brag.data.model.response.RProductList;
import com.pulse.brag.data.model.requests.QAddAddress;
import com.pulse.brag.data.model.response.RStateList;
import com.pulse.brag.data.model.response.RUserAddress;
import com.pulse.brag.data.remote.IApiManager;
import com.pulse.brag.data.model.GeneralResponse;
import com.pulse.brag.data.model.datas.UserData;
import com.pulse.brag.data.model.requests.ChangeMobileNumberRequest;
import com.pulse.brag.data.model.requests.QChangePassword;
import com.pulse.brag.data.model.requests.QLogin;
import com.pulse.brag.data.model.requests.QSignUp;
import com.pulse.brag.data.model.response.RCartList;
import com.pulse.brag.data.model.response.CategoryListResponse;
import com.pulse.brag.data.model.response.ChangePasswordResponse;
import com.pulse.brag.data.model.response.CollectionListResponse;
import com.pulse.brag.data.model.response.LoginResponse;
import com.pulse.brag.data.model.response.OTPVerifyResponse;
import com.pulse.brag.data.model.response.OrderDetailResponse;
import com.pulse.brag.data.model.response.SignUpResponse;

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
    public Call<CategoryListResponse> getCategoryProduct() {
        return mApiManager.getCategoryProduct();
    }

    @Override
    public Call<CollectionListResponse> getCollectionProduct() {
        return mApiManager.getCollectionProduct();
    }

    @Override
    public Call<GeneralResponse> notifyMe(String itemNo) {
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
    public Call<GeneralResponse> contactUs(QContactUs qContactUs) {
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
    public Call<OrderDetailResponse> getOrderDetail(String url) {
        return mApiManager.getOrderDetail(url);
    }

    @Override
    public Call<RUserAddress> addAddress(QAddAddress addAddress) {
        return mApiManager.addAddress(addAddress);
    }

    @Override
    public Call<LoginResponse> updateProfile(UserData userData) {
        return mApiManager.updateProfile(userData);
    }

    @Override
    public Call<RStateList> getStateList(String url) {
        return mApiManager.getStateList(url);
    }

    @Override
    public Call<GeneralResponse> removeFromCart(String id) {
        return mApiManager.removeFromCart(id);
    }

    @Override
    public Call<LoginResponse> getUserProfile(String s) {
        return mApiManager.getUserProfile(s);
    }

    @Override
    public Call<GeneralResponse> placeOrder(QPlaceOrder placeOrder) {
        return mApiManager.placeOrder(placeOrder);
    }

    @Override
    public Call<LoginResponse> userLogin(QLogin loginRequest) {
        return mApiManager.userLogin(loginRequest);
    }

    @Override
    public Call<RProductList> getProductionList(int page, QProductList body) {
        return mApiManager.getProductionList(page, body);
    }

    @Override
    public Call<SignUpResponse> userSignIn(QSignUp signInRequest) {
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
    public Call<ChangePasswordResponse> changePassword(QChangePassword changePasswordRequest) {
        return mApiManager.changePassword(changePasswordRequest);
    }

    @Override
    public Call<ChangePasswordResponse> resetPassword(QChangePassword changePasswordRequest) {
        return mApiManager.resetPassword(changePasswordRequest);
    }

    @Override
    public Call<GeneralResponse> logoutCall() {
        return mApiManager.logoutCall();
    }

    @Override
    public Call<GeneralResponse> generateOTPForMobileChange(QGenerateOtpForChangeMobile generateOtpForChangeMobile) {
        return mApiManager.generateOTPForMobileChange(generateOtpForChangeMobile);
    }

}
