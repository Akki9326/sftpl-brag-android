package com.pulse.brag.interfaces;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import com.pulse.brag.pojo.DummeyDataRespone;
import com.pulse.brag.pojo.DummeyRespone;
import com.pulse.brag.pojo.GeneralRespone;
import com.pulse.brag.pojo.requests.ChangePasswordRequest;
import com.pulse.brag.pojo.requests.LoginRequest;
import com.pulse.brag.pojo.requests.SignInRequest;
import com.pulse.brag.pojo.respones.ChangePasswordRespone;
import com.pulse.brag.pojo.respones.LoginRespone;
import com.pulse.brag.pojo.respones.OTPVerifyRespone;
import com.pulse.brag.pojo.respones.SignUpRespone;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by nikhil.vadoliya on 25-09-2017.
 */


public interface ApiInterface {


    @GET("users")
    Call<DummeyRespone> getProductionList(@Query("pages") int page);

    @POST("login")
    Call<LoginRespone> userLogin(@Body LoginRequest loginRequest);

    @POST("signup")
    Call<SignUpRespone> userSignIn(@Body SignInRequest signInRequest);

    @GET("validate")
    Call<OTPVerifyRespone> verifyOtp(@Query("mobile") String mobile, @Query("otp") String otp);

    @GET("validateForgetPassword")
    Call<OTPVerifyRespone> verifyOtpForgetPass(@Query("mobile") String mobile, @Query("otp") String otp);

    @GET("resendotp")
    Call<GeneralRespone> resendOtp(@Query("mobile") String mobile);

    @POST("changePassword")
    Call<ChangePasswordRespone> changePassword(@Body ChangePasswordRequest changePasswordRequest);

    @POST("resetPassword")
    Call<ChangePasswordRespone> resetPassword(@Body ChangePasswordRequest changePasswordRequest);

    @GET("logout")
    Call<GeneralRespone> logout();
}
