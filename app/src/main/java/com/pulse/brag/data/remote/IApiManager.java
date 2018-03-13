package com.pulse.brag.data.remote;

import com.pulse.brag.data.model.DummeyRespone;
import com.pulse.brag.data.model.GeneralResponse;
import com.pulse.brag.data.model.requests.ChangeMobileNumberRequest;
import com.pulse.brag.data.model.requests.ChangePasswordRequest;
import com.pulse.brag.data.model.requests.LoginRequest;
import com.pulse.brag.data.model.requests.QAddAddress;
import com.pulse.brag.data.model.requests.QAddToCart;
import com.pulse.brag.data.model.requests.QGenerateOtpForChangeMobile;
import com.pulse.brag.data.model.requests.QGetFilter;
import com.pulse.brag.data.model.requests.QProductList;
import com.pulse.brag.data.model.requests.SignInRequest;
import com.pulse.brag.data.model.response.RCartList;
import com.pulse.brag.data.model.response.CategoryListResponse;
import com.pulse.brag.data.model.response.ChangePasswordResponse;
import com.pulse.brag.data.model.response.CollectionListResponse;
import com.pulse.brag.data.model.response.LoginResponse;
import com.pulse.brag.data.model.response.MyOrderListRespone;
import com.pulse.brag.data.model.response.OTPVerifyResponse;
import com.pulse.brag.data.model.response.OrderDetailResponse;
import com.pulse.brag.data.model.response.RAddToCart;
import com.pulse.brag.data.model.response.RFilter;
import com.pulse.brag.data.model.response.RProductList;
import com.pulse.brag.data.model.response.RStateList;
import com.pulse.brag.data.model.response.SignUpResponse;

import retrofit2.Call;

/**
 * Created by alpesh.rathod on 2/12/2018.
 */

public interface IApiManager extends ApiInterface {

    @Override
    Call<LoginResponse> userLogin(LoginRequest loginRequest);

    @Override
    Call<RProductList> getProductionList(int page, QProductList body);

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
    Call<GeneralResponse> generateOTPForMobileChange(QGenerateOtpForChangeMobile generateOtpForChangeMobile);

    @Override
    Call<RCartList> getCartList(String url);

    @Override
    Call<MyOrderListRespone> getOrderList(String url);

    @Override
    Call<OrderDetailResponse> getOrderDetail(String url);

    @Override
    Call<GeneralResponse> changeMobileNum(ChangeMobileNumberRequest changeMobileNumberRequest);

    @Override
    Call<CategoryListResponse> getCategoryProduct(String url);

    @Override
    Call<CollectionListResponse> getCollectionProduct(String url);

    @Override
    Call<GeneralResponse> notifyMe(String itemNo);

    @Override
    Call<RFilter> getFilter(QGetFilter reqGetFilter);

    @Override
    Call<GeneralResponse> addAddress(QAddAddress addAddress);

    @Override
    Call<RStateList> getStateList(String url);

    @Override
    Call<RAddToCart> addToCart(QAddToCart addToCart);


}
