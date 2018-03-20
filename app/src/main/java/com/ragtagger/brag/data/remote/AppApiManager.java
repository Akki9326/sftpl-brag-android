package com.ragtagger.brag.data.remote;

import android.content.Context;

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

import javax.inject.Inject;

import retrofit2.Call;

/**
 * Created by alpesh.rathod on 2/12/2018.
 */

public class AppApiManager implements IApiManager {

    private final ApiInterface mApiInterface;

    @Inject
    public AppApiManager(Context context, ApiInterface apiInterface) {
        this.mApiInterface = apiInterface;
    }


    @Override
    public Call<RLogin> userLogin(QLogin loginRequest) {
        return mApiInterface.userLogin(loginRequest);
    }

    @Override
    public Call<RProductList> getProductionList(int page, QProductList body) {
        return mApiInterface.getProductionList(page, body);
    }

    @Override
    public Call<RSignUp> userSignIn(QSignUp signInRequest) {
        return mApiInterface.userSignIn(signInRequest);
    }

    @Override
    public Call<ROTPVerify> verifyOtp(String mobile, String otp) {
        return mApiInterface.verifyOtp(mobile, otp);
    }

    @Override
    public Call<ROTPVerify> verifyOtpForgetPass(String mobile, String otp) {
        return mApiInterface.verifyOtpForgetPass(mobile, otp);
    }

    @Override
    public Call<RGeneralData> resendOtp(String mobile) {
        return mApiInterface.resendOtp(mobile);
    }

    @Override
    public Call<RChangePassword> changePassword(QChangePassword changePasswordRequest) {
        return mApiInterface.changePassword(changePasswordRequest);
    }

    @Override
    public Call<RChangePassword> resetPassword(QChangePassword changePasswordRequest) {
        return mApiInterface.resetPassword(changePasswordRequest);
    }

    @Override
    public Call<RGeneralData> logoutCall() {
        return mApiInterface.logoutCall();
    }

    @Override
    public Call<RGeneralData> generateOTPForMobileChange(QGenerateOtpForChangeMobile generateOtpForChangeMobile) {
        return mApiInterface.generateOTPForMobileChange(generateOtpForChangeMobile);
    }


    @Override
    public Call<RGeneralData> changeMobileNum(QChangeMobileNumber changeMobileNumberRequest) {
        return mApiInterface.changeMobileNum(changeMobileNumberRequest);
    }

    @Override
    public Call<RCategoryList> getCategoryProduct() {
        return mApiInterface.getCategoryProduct();
    }

    @Override
    public Call<RCollectionList> getCollectionProduct() {
        return mApiInterface.getCollectionProduct();
    }

    @Override
    public Call<RGeneralData> notifyMe(String itemNo) {
        return mApiInterface.notifyMe(itemNo);
    }

    @Override
    public Call<RFilter> getFilter(QGetFilter reqGetFilter) {
        return mApiInterface.getFilter(reqGetFilter);
    }

    @Override
    public Call<RAddToCart> addToCart(QAddToCart addToCart) {
        return mApiInterface.addToCart(addToCart);
    }

    @Override
    public Call<RGeneralData> contactUs(QContactUs qContactUs) {
        return mApiInterface.contactUs(qContactUs);
    }

    @Override
    public Call<RCartList> getCartList(String url) {
        return mApiInterface.getCartList(url);
    }

    @Override
    public Call<RMyOrder> getOrderList(int page) {
        return mApiInterface.getOrderList(page);
    }


    @Override
    public Call<ROrderDetail> getOrderDetail(String orderId) {
        return mApiInterface.getOrderDetail(orderId);
    }

    @Override
    public Call<RUserAddress> addAddress(QAddAddress addAddress) {
        return mApiInterface.addAddress(addAddress);
    }

    @Override
    public Call<RLogin> updateProfile(DataUser userData) {
        return mApiInterface.updateProfile(userData);
    }

    @Override
    public Call<RStateList> getStateList(String url) {
        return mApiInterface.getStateList(url);
    }

    @Override
    public Call<RGeneralData> removeFromCart(String id) {
        return mApiInterface.removeFromCart(id);
    }

    @Override
    public Call<RLogin> getUserProfile(String s) {
        return mApiInterface.getUserProfile(s);
    }

    @Override
    public Call<RGeneralData> placeOrder(QPlaceOrder placeOrder) {
        return mApiInterface.placeOrder(placeOrder);
    }

    @Override
    public Call<RGeneralData> reOrder(String id) {
        return mApiInterface.reOrder(id);
    }
}
