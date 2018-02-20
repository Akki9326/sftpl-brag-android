package com.pulse.brag.data.remote;

import android.content.Context;

import com.pulse.brag.di.PreferenceInfo;
import com.pulse.brag.pojo.DummeyRespone;
import com.pulse.brag.pojo.GeneralResponse;
import com.pulse.brag.pojo.requests.ChangeMobileNumberRequest;
import com.pulse.brag.pojo.requests.ChangePasswordRequest;
import com.pulse.brag.pojo.requests.LoginRequest;
import com.pulse.brag.pojo.requests.SignInRequest;
import com.pulse.brag.pojo.response.CartListResponse;
import com.pulse.brag.pojo.response.CategoryListResponse;
import com.pulse.brag.pojo.response.ChangePasswordResponse;
import com.pulse.brag.pojo.response.CollectionListResponse;
import com.pulse.brag.pojo.response.LoginResponse;
import com.pulse.brag.pojo.response.OTPVerifyResponse;
import com.pulse.brag.pojo.response.SignUpResponse;

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
    public Call<DummeyRespone> getProductionList(int page) {
        return mApiInterface.getProductionList(page);
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
    public Call<CartListResponse> getCartList(String url) {
        return mApiInterface.getCartList(url);
    }
}
