package com.pulse.brag.data.remote;

import com.pulse.brag.data.model.GeneralResponse;
import com.pulse.brag.data.model.datas.UserData;
import com.pulse.brag.data.model.requests.ChangeMobileNumberRequest;
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
import com.pulse.brag.data.model.response.RCartList;
import com.pulse.brag.data.model.response.CategoryListResponse;
import com.pulse.brag.data.model.response.ChangePasswordResponse;
import com.pulse.brag.data.model.response.CollectionListResponse;
import com.pulse.brag.data.model.response.LoginResponse;
import com.pulse.brag.data.model.response.OTPVerifyResponse;
import com.pulse.brag.data.model.response.OrderDetailResponse;
import com.pulse.brag.data.model.response.RAddToCart;
import com.pulse.brag.data.model.response.RFilter;
import com.pulse.brag.data.model.response.RMyOrder;
import com.pulse.brag.data.model.response.RProductList;
import com.pulse.brag.data.model.response.RStateList;
import com.pulse.brag.data.model.response.RUserAddress;
import com.pulse.brag.data.model.response.SignUpResponse;

import retrofit2.Call;

/**
 * Created by alpesh.rathod on 2/12/2018.
 */

public interface IApiManager extends ApiInterface {

    @Override
    Call<LoginResponse> userLogin(QLogin loginRequest);

    @Override
    Call<RProductList> getProductionList(int page, QProductList body);

    @Override
    Call<SignUpResponse> userSignIn(QSignUp signInRequest);

    @Override
    Call<OTPVerifyResponse> verifyOtp(String mobile, String otp);

    @Override
    Call<OTPVerifyResponse> verifyOtpForgetPass(String mobile, String otp);

    @Override
    Call<GeneralResponse> resendOtp(String mobile);

    @Override
    Call<ChangePasswordResponse> changePassword(QChangePassword changePasswordRequest);

    @Override
    Call<ChangePasswordResponse> resetPassword(QChangePassword changePasswordRequest);

    @Override
    Call<GeneralResponse> logoutCall();

    @Override
    Call<GeneralResponse> generateOTPForMobileChange(QGenerateOtpForChangeMobile generateOtpForChangeMobile);

    @Override
    Call<RCartList> getCartList(String url);

    @Override
    Call<OrderDetailResponse> getOrderDetail(String url);

    @Override
    Call<GeneralResponse> changeMobileNum(ChangeMobileNumberRequest changeMobileNumberRequest);

    @Override
    Call<CategoryListResponse> getCategoryProduct();

    @Override
    Call<CollectionListResponse> getCollectionProduct();

    @Override
    Call<GeneralResponse> notifyMe(String itemNo);

    @Override
    Call<RFilter> getFilter(QGetFilter reqGetFilter);

    @Override
    Call<RUserAddress> addAddress(QAddAddress addAddress);

    @Override
    Call<RStateList> getStateList(String url);

    @Override
    Call<RAddToCart> addToCart(QAddToCart addToCart);

    @Override
    Call<GeneralResponse> contactUs(QContactUs qContactUs);

    @Override
    Call<RMyOrder> getOrderList(int page);

    @Override
    Call<LoginResponse> updateProfile(UserData userData);

    @Override
    Call<GeneralResponse> removeFromCart(String id);

    @Override
    Call<LoginResponse> getUserProfile(String s);

    @Override
    Call<GeneralResponse> placeOrder(QPlaceOrder placeOrder);
}
