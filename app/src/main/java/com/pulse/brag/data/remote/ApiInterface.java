package com.pulse.brag.data.remote;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import com.pulse.brag.pojo.DummeyRespone;
import com.pulse.brag.pojo.GeneralResponse;
import com.pulse.brag.pojo.requests.ChangeMobileNumberRequest;
import com.pulse.brag.pojo.requests.ChangePasswordRequest;
import com.pulse.brag.pojo.requests.LoginRequest;
import com.pulse.brag.pojo.requests.SignInRequest;
import com.pulse.brag.pojo.response.CategoryListResponse;
import com.pulse.brag.pojo.response.ChangePasswordResponse;
import com.pulse.brag.pojo.response.CollectionListResponse;
import com.pulse.brag.pojo.response.LoginResponse;
import com.pulse.brag.pojo.response.OTPVerifyResponse;
import com.pulse.brag.pojo.response.SignUpResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by nikhil.vadoliya on 25-09-2017.
 */


public interface ApiInterface {


    @GET("users")
    Call<DummeyRespone> getProductionList(@Query("pages") int page);

    @POST("login")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);

    @POST("signup")
    Call<SignUpResponse> userSignIn(@Body SignInRequest signInRequest);

    @GET("validate")
    Call<OTPVerifyResponse> verifyOtp(@Query("mobile") String mobile, @Query("otp") String otp);

    @GET("validateForgetPassword")
    Call<OTPVerifyResponse> verifyOtpForgetPass(@Query("mobile") String mobile, @Query("otp") String otp);

    @GET("resendotp")
    Call<GeneralResponse> resendOtp(@Query("mobile") String mobile);

    @POST("changePassword")
    Call<ChangePasswordResponse> changePassword(@Body ChangePasswordRequest changePasswordRequest);

    @POST("resetPassword")
    Call<ChangePasswordResponse> resetPassword(@Body ChangePasswordRequest changePasswordRequest);

    @GET("logout")
    Call<GeneralResponse> logout();

    @POST("changeMobileNumber")
    Call<GeneralResponse> changeMobileNum(@Body ChangeMobileNumberRequest changeMobileNumberRequest);

    @GET
    Call<CategoryListResponse> getCategoryProduct(@Url String url);

    @GET
    Call<CollectionListResponse> getCollectionProduct(@Url String url);
}
