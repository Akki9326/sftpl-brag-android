package com.ragtagger.brag.ui.authentication.otp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ragtagger.brag.callback.OnSingleClickListener;
import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.datas.DataUser;
import com.ragtagger.brag.data.model.requests.QChangeMobileNumber;
import com.ragtagger.brag.data.remote.ApiResponse;
import com.ragtagger.brag.data.model.response.RGeneralData;
import com.ragtagger.brag.data.model.response.ROTPVerify;
import com.ragtagger.brag.ui.core.CoreViewModel;

import okhttp3.Headers;
import retrofit2.Call;

/**
 * Created by alpesh.rathod on 2/15/2018.
 */

public class OTPViewModel extends CoreViewModel<OTPNavigator> {

    public OTPViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public View.OnClickListener onVerifyOtpClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().verifyOtp();
            }
        };
    }

    public View.OnClickListener onResendOtpClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().resendOtp();
            }
        };
    }

    public boolean onEditorActionPin(@NonNull final TextView textView, final int actionId,
                                     @Nullable final KeyEvent keyEvent) {
        return getNavigator().onEditorActionPin(textView, actionId, keyEvent);
    }

    public void resendOtp(String mobileNumber) {
        Call<RGeneralData> responeCall = getDataManager().resendOtp(mobileNumber);
        responeCall.enqueue(new ApiResponse<RGeneralData>() {
            @Override
            public void onSuccess(RGeneralData generalResponse, Headers headers) {
                if (generalResponse.isStatus()) {
                    getNavigator().onApiSuccess();
                } else {
                    getNavigator().onApiError(new ApiError(generalResponse.getErrorCode(), generalResponse.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiError(t);
            }
        });
    }

    public void verifyOtp(String mobileNum, String otp, final int formType) {
        Call<ROTPVerify> mOtpVerifyResponeCall = getDataManager().verifyOtp(mobileNum, otp);

        mOtpVerifyResponeCall.enqueue(new ApiResponse<ROTPVerify>() {
            @Override
            public void onSuccess(ROTPVerify otpVerifyResponse, Headers headers) {
                if (otpVerifyResponse.isStatus()) {
                    getNavigator().onApiSuccess();
                    getNavigator().pushSignUpCompleteFragment();
                } else {
                    // TODO: 3/5/2018 Display invalid msg
                    getNavigator().onApiError(new ApiError(otpVerifyResponse.getErrorCode(), otpVerifyResponse.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiError(t);
            }
        });
    }

    public void verifyOtpForForgotPass(String mobileNum, String otp, final int formType) {
        Call<ROTPVerify> mOtpVerifyResponeCall = getDataManager().verifyOtpForgetPass(mobileNum, otp);

        mOtpVerifyResponeCall.enqueue(new ApiResponse<ROTPVerify>() {
            @Override
            public void onSuccess(ROTPVerify otpVerifyResponse, Headers headers) {
                if (otpVerifyResponse.isStatus()) {
                    getNavigator().onApiSuccess();
                    getNavigator().pushCreatePasswordFragment();
                } else {
                    // TODO: 3/5/2018 Display invalid msg
                    getNavigator().onApiError(new ApiError(otpVerifyResponse.getErrorCode(), otpVerifyResponse.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiError(t);
            }
        });
    }

    public void verifyOtpForChangeMob(final String mobile, String password, String otp) {
        // TODO: 13-12-2017 if you display mobile number in more Fragment than isStatus==true update mobile number display
        QChangeMobileNumber changeMobileNumberRequest = new QChangeMobileNumber(mobile, password, otp);
        Call<RGeneralData> mResponeCall = getDataManager().changeMobileNum(changeMobileNumberRequest);
        mResponeCall.enqueue(new ApiResponse<RGeneralData>() {
            @Override
            public void onSuccess(RGeneralData generalResponse, Headers headers) {
                if (generalResponse.isStatus()) {
                    getNavigator().onApiSuccess();
                    getNavigator().finishUserProfileActivity();
                } else {
                    getNavigator().onApiError(new ApiError(generalResponse.getErrorCode(), generalResponse.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiError(t);
            }
        });

    }
}
