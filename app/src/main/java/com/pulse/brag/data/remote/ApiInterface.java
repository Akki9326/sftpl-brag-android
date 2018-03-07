package com.pulse.brag.data.remote;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import com.pulse.brag.data.model.DummeyRespone;
import com.pulse.brag.data.model.GeneralResponse;
import com.pulse.brag.data.model.requests.ChangeMobileNumberRequest;
import com.pulse.brag.data.model.requests.ChangePasswordRequest;
import com.pulse.brag.data.model.requests.LoginRequest;
import com.pulse.brag.data.model.requests.SignInRequest;
import com.pulse.brag.data.model.response.CartListResponse;
import com.pulse.brag.data.model.response.CategoryListResponse;
import com.pulse.brag.data.model.response.ChangePasswordResponse;
import com.pulse.brag.data.model.response.CollectionListResponse;
import com.pulse.brag.data.model.response.LoginResponse;
import com.pulse.brag.data.model.response.MyOrderListRespone;
import com.pulse.brag.data.model.response.OTPVerifyResponse;
import com.pulse.brag.data.model.response.OrderDetailResponse;
import com.pulse.brag.data.model.response.SignUpResponse;

import retrofit2.Call;
import retrofit2.http.Body;
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
    Call<GeneralResponse> logoutCall();

    @POST("generateOTPForMobileChange")
    Call<GeneralResponse> generateOTPForMobileChange(@Body ChangeMobileNumberRequest changeMobileNumberRequest);

    @POST("changeMobileNumber")
    Call<GeneralResponse> changeMobileNum(@Body ChangeMobileNumberRequest changeMobileNumberRequest);

    @GET
    Call<CategoryListResponse> getCategoryProduct(@Url String url);

    @GET
    Call<CollectionListResponse> getCollectionProduct(@Url String url);

    @GET("notifyMe")
    Call<GeneralResponse> notifyMe(String productId, String color, String size);

    @GET
    Call<CartListResponse> getCartList(@Url String url);

    @GET
    Call<MyOrderListRespone> getOrderList(@Url String url);

    @GET
    Call<OrderDetailResponse> getOrderDetail(@Url String url);
}
