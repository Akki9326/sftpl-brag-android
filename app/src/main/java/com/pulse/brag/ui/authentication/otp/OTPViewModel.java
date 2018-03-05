package com.pulse.brag.ui.authentication.otp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.pulse.brag.callback.OnSingleClickListener;
import com.pulse.brag.data.IDataManager;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.data.remote.ApiResponse;
import com.pulse.brag.data.model.GeneralResponse;
import com.pulse.brag.data.model.response.OTPVerifyResponse;
import com.pulse.brag.ui.core.CoreViewModel;
import com.pulse.brag.utils.Constants;

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

    public void verifyOtp(String mobileNum, String otp, final int formType) {
        Call<OTPVerifyResponse> mOtpVerifyResponeCall = null;
        switch (Constants.OTPValidationIsFrom.values()[formType]) {
            case SIGN_UP:
                mOtpVerifyResponeCall = getDataManager().verifyOtp(mobileNum, otp);
                break;
            case FORGET_PASS:
                mOtpVerifyResponeCall = getDataManager().verifyOtpForgetPass(mobileNum, otp);
                break;
            case CHANGE_MOBILE:
                mOtpVerifyResponeCall = getDataManager().verifyOtp(mobileNum, otp);
                break;
        }

        mOtpVerifyResponeCall.enqueue(new ApiResponse<OTPVerifyResponse>() {
            @Override
            public void onSuccess(OTPVerifyResponse otpVerifyResponse, Headers headers) {
                if (otpVerifyResponse.isStatus()) {
                    getNavigator().onApiSuccess();
                    switch (Constants.OTPValidationIsFrom.values()[formType]) {
                        case CHANGE_MOBILE:
                            getNavigator().pushChangeMobileFragment();
                            break;
                        case FORGET_PASS:
                            getNavigator().pushCreatePasswordFragment();
                            break;

                        case SIGN_UP:
                            getNavigator().pushSignUpCompleteFragment();
                            break;
                    }
                }else {
                    // TODO: 3/5/2018 Display invalid msg
                    getNavigator().onApiSuccess();
                    switch (Constants.OTPValidationIsFrom.values()[formType]) {
                        case CHANGE_MOBILE:
                            getNavigator().pushChangeMobileFragment();
                            break;
                        case FORGET_PASS:
                            getNavigator().pushCreatePasswordFragment();
                            break;

                        case SIGN_UP:
                            getNavigator().pushSignUpCompleteFragment();
                            break;
                    }
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiError(t);
            }
        });
    }

    public void resendOtp(String mobileNumber) {
        Call<GeneralResponse> responeCall = getDataManager().resendOtp(mobileNumber);
        responeCall.enqueue(new ApiResponse<GeneralResponse>() {
            @Override
            public void onSuccess(GeneralResponse generalResponse, Headers headers) {
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
}
