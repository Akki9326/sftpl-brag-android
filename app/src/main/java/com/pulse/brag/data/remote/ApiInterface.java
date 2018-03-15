package com.pulse.brag.data.remote;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import com.pulse.brag.data.model.response.RGeneralData;
import com.pulse.brag.data.model.datas.DataUser;
import com.pulse.brag.data.model.requests.QChangeMobileNumber;
import com.pulse.brag.data.model.requests.QChangePassword;
import com.pulse.brag.data.model.requests.QContactUs;
import com.pulse.brag.data.model.requests.QLogin;
import com.pulse.brag.data.model.requests.QAddAddress;
import com.pulse.brag.data.model.requests.QAddToCart;
import com.pulse.brag.data.model.requests.QGenerateOtpForChangeMobile;
import com.pulse.brag.data.model.requests.QGetFilter;
import com.pulse.brag.data.model.requests.QPlaceOrder;
import com.pulse.brag.data.model.requests.QProductList;
import com.pulse.brag.data.model.requests.QSignUp;
import com.pulse.brag.data.model.response.RChangePassword;
import com.pulse.brag.data.model.response.RCategoryList;
import com.pulse.brag.data.model.response.RCategoryList;
import com.pulse.brag.data.model.response.RChangePassword;
import com.pulse.brag.data.model.response.RCollectionList;
import com.pulse.brag.data.model.response.RLogin;
import com.pulse.brag.data.model.response.ROTPVerify;
import com.pulse.brag.data.model.response.ROrderDetail;
import com.pulse.brag.data.model.response.RAddToCart;
import com.pulse.brag.data.model.response.RCartList;
import com.pulse.brag.data.model.response.RFilter;
import com.pulse.brag.data.model.response.RMyOrder;
import com.pulse.brag.data.model.response.RProductList;
import com.pulse.brag.data.model.response.RStateList;
import com.pulse.brag.data.model.response.RUserAddress;
import com.pulse.brag.data.model.response.RSignUp;

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
    Call<RLogin> userLogin(@Body QLogin loginRequest);

    @POST("signup")
    Call<RSignUp> userSignIn(@Body QSignUp signInRequest);

    @GET("validate")
    Call<ROTPVerify> verifyOtp(@Query("mobile") String mobile, @Query("otp") String otp);

    @GET("validateForgetPassword")
    Call<ROTPVerify> verifyOtpForgetPass(@Query("mobile") String mobile, @Query("otp") String otp);

    @GET("resendotp")
    Call<RGeneralData> resendOtp(@Query("mobile") String mobile);

    @POST("changePassword")
    Call<RChangePassword> changePassword(@Body QChangePassword changePasswordRequest);

    @POST("resetPassword")
    Call<RChangePassword> resetPassword(@Body QChangePassword changePasswordRequest);

    @GET("logout")
    Call<RGeneralData> logoutCall();

    @POST("generateOTPForMobileChange")
    Call<RGeneralData> generateOTPForMobileChange(@Body QGenerateOtpForChangeMobile generateOtpForChangeMobile);

    @POST("changeMobileNumber")
    Call<RGeneralData> changeMobileNum(@Body QChangeMobileNumber changeMobileNumberRequest);

    @POST("contactUs")
    Call<RGeneralData> contactUs(@Body QContactUs qContactUs);

    @GET("home/get/1")
    Call<RCategoryList> getCategoryProduct();

    @GET("home/get/2")
    Call<RCollectionList> getCollectionProduct();

    @POST("item/list/{pages}")
    Call<RProductList> getProductionList(@Path("pages") int page, @Body QProductList body);

    @POST("item/addToCart")
    Call<RAddToCart> addToCart(@Body QAddToCart addToCart);

    @GET("item/notifyme/{itemNo}")
    Call<RGeneralData> notifyMe(@Path("itemNo") String itemNo);

    @POST("item/getFilter")
    Call<RFilter> getFilter(@Body QGetFilter reqGetFilter);

    @GET
    Call<RCartList> getCartList(@Url String url);

    @POST("order/list/{pages}")
    Call<RMyOrder> getOrderList(@Path("pages") int page);

    @POST("order/list/{pages}")
    Call<ROrderDetail> getOrderDetail(@Url String url);

    @POST("user/addAddress")
    Call<RUserAddress> addAddress(@Body QAddAddress addAddress);

    @POST("user/updateProfile")
    Call<RLogin> updateProfile(@Body DataUser userData);

    @GET
    Call<RStateList> getStateList(@Url String url);

    @GET
    Call<RGeneralData> removeFromCart(@Url String id);

    @GET
    Call<RLogin> getUserProfile(@Url String s);

    @POST("order/add")
    Call<RGeneralData> placeOrder(@Body QPlaceOrder placeOrder);

    @GET("order/reOrder/{orderId}")
    Call<RGeneralData> reOrder(@Path("orderId") String id);
}
