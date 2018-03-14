package com.pulse.brag.data.remote;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import com.pulse.brag.data.model.GeneralResponse;
import com.pulse.brag.data.model.datas.UserData;
import com.pulse.brag.data.model.requests.ChangeMobileNumberRequest;
import com.pulse.brag.data.model.requests.ChangePasswordRequest;
import com.pulse.brag.data.model.requests.LoginRequest;
import com.pulse.brag.data.model.requests.QAddAddress;
import com.pulse.brag.data.model.requests.QAddToCart;
import com.pulse.brag.data.model.requests.QGenerateOtpForChangeMobile;
import com.pulse.brag.data.model.requests.QGetFilter;
import com.pulse.brag.data.model.requests.QPlaceOrder;
import com.pulse.brag.data.model.requests.QProductList;
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
import com.pulse.brag.data.model.response.RFilter;
import com.pulse.brag.data.model.response.RStateList;
import com.pulse.brag.data.model.response.RAddToCart;
import com.pulse.brag.data.model.response.RProductList;
import com.pulse.brag.data.model.response.RUserAddress;
import com.pulse.brag.data.model.response.SignUpResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by nikhil.vadoliya on 25-09-2017.
 */


public interface ApiInterface {


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
    Call<GeneralResponse> generateOTPForMobileChange(@Body QGenerateOtpForChangeMobile generateOtpForChangeMobile);

    @POST("changeMobileNumber")
    Call<GeneralResponse> changeMobileNum(@Body ChangeMobileNumberRequest changeMobileNumberRequest);

    @GET
    Call<CategoryListResponse> getCategoryProduct(@Url String url);

    @GET
    Call<CollectionListResponse> getCollectionProduct(@Url String url);

    @POST("item/list/{pages}")
    Call<RProductList> getProductionList(@Path("pages") int page, @Body QProductList body);


    @POST("item/addToCart")
    Call<RAddToCart> addToCart(@Body QAddToCart addToCart);

    @GET("item/notifyme/{itemNo}")
    Call<GeneralResponse> notifyMe(@Path("itemNo") String itemNo);

    @POST("item/getFilter")
    Call<RFilter> getFilter(@Body QGetFilter reqGetFilter);

    @GET
    Call<RCartList> getCartList(@Url String url);

    @POST("order/list/{pages}")
    Call<RMyOrder> getOrderList(@Path("pages") int page);

    @POST("order/list/{pages}")
    Call<OrderDetailResponse> getOrderDetail(@Url String url);

    @POST("user/addAddress")
    Call<RUserAddress> addAddress(@Body QAddAddress addAddress);

    @POST("user/updateProfile")
    Call<LoginResponse> updateProfile(@Body UserData userData);

    @GET
    Call<RStateList> getStateList(@Url String url);

    @GET
    Call<GeneralResponse> removeFromCart(@Url String id);

    @GET
    Call<LoginResponse> getUserProfile(@Url String s);

    @POST("order/add")
    Call<GeneralResponse> placeOrder(@Body QPlaceOrder  placeOrder);
}
