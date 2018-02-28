package com.pulse.brag.data.remote;

import com.pulse.brag.data.model.DummeyRespone;
import com.pulse.brag.data.model.GeneralResponse;
import com.pulse.brag.data.model.requests.ChangeMobileNumberRequest;
import com.pulse.brag.data.model.requests.ChangePasswordRequest;
import com.pulse.brag.data.model.requests.LoginRequest;
import com.pulse.brag.data.model.requests.SignInRequest;
import com.pulse.brag.data.model.response.CategoryListResponse;
import com.pulse.brag.data.model.response.ChangePasswordResponse;
import com.pulse.brag.data.model.response.CollectionListResponse;
import com.pulse.brag.data.model.response.LoginResponse;
import com.pulse.brag.data.model.response.OTPVerifyResponse;
import com.pulse.brag.data.model.response.SignUpResponse;

import retrofit2.Call;

/**
 * Created by alpesh.rathod on 2/12/2018.
 */

public interface IApiManager extends ApiInterface {

    @Override
    Call<LoginResponse> userLogin(LoginRequest loginRequest);

    @Override
    Call<DummeyRespone> getProductionList(int page);

    @Override
    Call<SignUpResponse> userSignIn(SignInRequest signInRequest);

    @Override
    Call<OTPVerifyResponse> verifyOtp(String mobile, String otp);

    @Override
    Call<OTPVerifyResponse> verifyOtpForgetPass(String mobile, String otp);

    @Override
    Call<GeneralResponse> resendOtp(String mobile);

    @Override
    Call<ChangePasswordResponse> changePassword(ChangePasswordRequest changePasswordRequest);

    @Override
    Call<ChangePasswordResponse> resetPassword(ChangePasswordRequest changePasswordRequest);

    @Override
    Call<GeneralResponse> logoutCall();

    @Override
    Call<GeneralResponse> changeMobileNum(ChangeMobileNumberRequest changeMobileNumberRequest);

    @Override
    Call<CategoryListResponse> getCategoryProduct(String url);

    @Override
    Call<CollectionListResponse> getCollectionProduct(String url);

    @Override
    Call<GeneralResponse> notifyMe(String productId, String color, String size);
}
