package com.pulse.brag.data.remote;

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
import com.pulse.brag.data.model.response.RCartList;
import com.pulse.brag.data.model.response.RCategoryList;
import com.pulse.brag.data.model.response.RChangePassword;
import com.pulse.brag.data.model.response.RCollectionList;
import com.pulse.brag.data.model.response.RLogin;
import com.pulse.brag.data.model.response.ROTPVerify;
import com.pulse.brag.data.model.response.ROrderDetail;
import com.pulse.brag.data.model.response.RAddToCart;
import com.pulse.brag.data.model.response.RFilter;
import com.pulse.brag.data.model.response.RMyOrder;
import com.pulse.brag.data.model.response.RProductList;
import com.pulse.brag.data.model.response.RStateList;
import com.pulse.brag.data.model.response.RUserAddress;
import com.pulse.brag.data.model.response.RSignUp;

import retrofit2.Call;

/**
 * Created by alpesh.rathod on 2/12/2018.
 */

public interface IApiManager extends ApiInterface {

    @Override
    Call<RLogin> userLogin(QLogin loginRequest);

    @Override
    Call<RProductList> getProductionList(int page, QProductList body);

    @Override
    Call<RSignUp> userSignIn(QSignUp signInRequest);

    @Override
    Call<ROTPVerify> verifyOtp(String mobile, String otp);

    @Override
    Call<ROTPVerify> verifyOtpForgetPass(String mobile, String otp);

    @Override
    Call<RGeneralData> resendOtp(String mobile);

    @Override
    Call<RChangePassword> changePassword(QChangePassword changePasswordRequest);

    @Override
    Call<RChangePassword> resetPassword(QChangePassword changePasswordRequest);

    @Override
    Call<RGeneralData> logoutCall();

    @Override
    Call<RGeneralData> generateOTPForMobileChange(QGenerateOtpForChangeMobile generateOtpForChangeMobile);

    @Override
    Call<RCartList> getCartList(String url);

    @Override
    Call<ROrderDetail> getOrderDetail(String url);

    @Override
    Call<RGeneralData> changeMobileNum(QChangeMobileNumber changeMobileNumberRequest);

    @Override
    Call<RCategoryList> getCategoryProduct();

    @Override
    Call<RCollectionList> getCollectionProduct();

    @Override
    Call<RGeneralData> notifyMe(String itemNo);

    @Override
    Call<RFilter> getFilter(QGetFilter reqGetFilter);

    @Override
    Call<RUserAddress> addAddress(QAddAddress addAddress);

    @Override
    Call<RStateList> getStateList(String url);

    @Override
    Call<RAddToCart> addToCart(QAddToCart addToCart);

    @Override
    Call<RGeneralData> contactUs(QContactUs qContactUs);

    @Override
    Call<RMyOrder> getOrderList(int page);

    @Override
    Call<RLogin> updateProfile(DataUser userData);

    @Override
    Call<RGeneralData> removeFromCart(String id);

    @Override
    Call<RLogin> getUserProfile(String s);

    @Override
    Call<RGeneralData> placeOrder(QPlaceOrder placeOrder);
}
