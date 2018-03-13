package com.pulse.brag.data.remote;

import android.content.Context;

import com.pulse.brag.data.model.GeneralResponse;
import com.pulse.brag.data.model.datas.UserData;
import com.pulse.brag.data.model.requests.ChangeMobileNumberRequest;
import com.pulse.brag.data.model.requests.ChangePasswordRequest;
import com.pulse.brag.data.model.requests.LoginRequest;
import com.pulse.brag.data.model.requests.QAddToCart;
import com.pulse.brag.data.model.requests.QGenerateOtpForChangeMobile;
import com.pulse.brag.data.model.requests.QPlaceOrder;
import com.pulse.brag.data.model.requests.QProductList;
import com.pulse.brag.data.model.requests.QAddAddress;
import com.pulse.brag.data.model.requests.SignInRequest;
import com.pulse.brag.data.model.response.RCartList;
import com.pulse.brag.data.model.response.CategoryListResponse;
import com.pulse.brag.data.model.response.ChangePasswordResponse;
import com.pulse.brag.data.model.response.CollectionListResponse;
import com.pulse.brag.data.model.response.LoginResponse;
import com.pulse.brag.data.model.response.RMyOrder;
import com.pulse.brag.data.model.response.RMyOrderList;
import com.pulse.brag.data.model.response.OTPVerifyResponse;
import com.pulse.brag.data.model.response.OrderDetailResponse;
import com.pulse.brag.data.model.response.RAddToCart;
import com.pulse.brag.data.model.response.RProductList;
import com.pulse.brag.data.model.response.RStateList;
import com.pulse.brag.data.model.response.RUserAddress;
import com.pulse.brag.data.model.response.SignUpResponse;

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
    public Call<LoginResponse> userLogin(LoginRequest loginRequest) {
        return mApiInterface.userLogin(loginRequest);
    }

    @Override
    public Call<RProductList> getProductionList(int page, QProductList body) {
        return mApiInterface.getProductionList(page, body);
    }

    @Override
    public Call<SignUpResponse> userSignIn(SignInRequest signInRequest) {
        return mApiInterface.userSignIn(signInRequest);
    }

    @Override
    public Call<OTPVerifyResponse> verifyOtp(String mobile, String otp) {
        return mApiInterface.verifyOtp(mobile, otp);
    }

    @Override
    public Call<OTPVerifyResponse> verifyOtpForgetPass(String mobile, String otp) {
        return mApiInterface.verifyOtpForgetPass(mobile, otp);
    }

    @Override
    public Call<GeneralResponse> resendOtp(String mobile) {
        return mApiInterface.resendOtp(mobile);
    }

    @Override
    public Call<ChangePasswordResponse> changePassword(ChangePasswordRequest changePasswordRequest) {
        return mApiInterface.changePassword(changePasswordRequest);
    }

    @Override
    public Call<ChangePasswordResponse> resetPassword(ChangePasswordRequest changePasswordRequest) {
        return mApiInterface.resetPassword(changePasswordRequest);
    }

    @Override
    public Call<GeneralResponse> logoutCall() {
        return mApiInterface.logoutCall();
    }

    @Override
    public Call<GeneralResponse> generateOTPForMobileChange(QGenerateOtpForChangeMobile generateOtpForChangeMobile) {
        return mApiInterface.generateOTPForMobileChange(generateOtpForChangeMobile);
    }


    @Override
    public Call<GeneralResponse> changeMobileNum(ChangeMobileNumberRequest changeMobileNumberRequest) {
        return mApiInterface.changeMobileNum(changeMobileNumberRequest);
    }

    @Override
    public Call<CategoryListResponse> getCategoryProduct(String url) {
        return mApiInterface.getCategoryProduct(url);
    }

    @Override
    public Call<CollectionListResponse> getCollectionProduct(String url) {
        return mApiInterface.getCollectionProduct(url);
    }

    @Override
    public Call<GeneralResponse> notifyMe(String productId, String color, String size) {
        return mApiInterface.notifyMe(productId, color, size);
    }

    @Override
    public Call<RAddToCart> addToCart(QAddToCart addToCart) {
        return mApiInterface.addToCart(addToCart);
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
    public Call<OrderDetailResponse> getOrderDetail(String url) {
        return mApiInterface.getOrderDetail(url);
    }

    @Override
    public Call<RUserAddress> addAddress(QAddAddress addAddress) {
        return mApiInterface.addAddress(addAddress);
    }

    @Override
    public Call<LoginResponse> updateProfile(UserData userData) {
        return mApiInterface.updateProfile(userData);
    }

    @Override
    public Call<RStateList> getStateList(String url) {
        return mApiInterface.getStateList(url);
    }

    @Override
    public Call<GeneralResponse> removeFromCart(String id) {
        return mApiInterface.removeFromCart(id);
    }

    @Override
    public Call<LoginResponse> getUserProfile(String s) {
        return mApiInterface.getUserProfile(s);
    }

    @Override
    public Call<GeneralResponse> placeOrder(QPlaceOrder placeOrder) {
        return mApiInterface.placeOrder(placeOrder);
    }
}
