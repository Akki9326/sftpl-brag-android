package com.pulse.brag.ui.otp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.pulse.brag.data.IDataManager;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.data.remote.ApiResponse;
import com.pulse.brag.pojo.GeneralResponse;
import com.pulse.brag.pojo.response.OTPVerifyResponse;
import com.pulse.brag.ui.core.CoreViewModel;
import com.pulse.brag.utils.Constants;
import com.pulse.brag.callback.OnSingleClickListener;

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

    public void verifyOtp(String mobileNum, String otp, final int formType){
        Call<OTPVerifyResponse> mOtpVerifyResponeCall = null;
        switch (Constants.OTPValidationIsFrom.values()[formType]) {
            case SIGN_UP:
                mOtpVerifyResponeCall = getDataManager().verifyOtp(mobileNum,otp);
                break;
            case FORGET_PASS:
                mOtpVerifyResponeCall = getDataManager().verifyOtpForgetPass(mobileNum,otp);
                break;
            case CHANGE_MOBILE:
                mOtpVerifyResponeCall = getDataManager().verifyOtp(mobileNum,otp);
                break;
        }

        mOtpVerifyResponeCall.enqueue(new ApiResponse<OTPVerifyResponse>() {
            @Override
            public void onSuccess(OTPVerifyResponse otpVerifyResponse, Headers headers) {
                if (otpVerifyResponse.isStatus()){
                    getNavigator().onApiSuccess();
                    switch (Constants.OTPValidationIsFrom.values()[formType]) {
                        case CHANGE_MOBILE:
                            getNavigator().pushChangeMobileFragment();
                            /*((UserProfileActivity) getActivity()).pushFragmentInChangeContainer(ChangeMobileNumberFragment.newInstance(mobileNum)
                                    , true, true, "");*/
                            break;
                        case FORGET_PASS:
                            getNavigator().pushCreatePasswordFragment();
                            /*((SplashActivity) getActivity()).pushFragments(CreateNewPasswordFragment.newInstance(mobileNum, otp),
                                    true, true, "Create_Pass_Frag");*/
                            break;

                        case SIGN_UP:
                            getNavigator().pushSignUpCompleteFragment();
                            /*((SplashActivity) getActivity()).pushFragments(new SignUpCompleteFragment(),
                                    true, true, "Signup_Complete_Frag");*/
                            break;
                    }
                }else {
                    getNavigator().onApiSuccess();
                    switch (Constants.OTPValidationIsFrom.values()[formType]) {
                        case CHANGE_MOBILE:
                            getNavigator().pushChangeMobileFragment();
                            /*((UserProfileActivity) getActivity()).pushFragmentInChangeContainer(ChangeMobileNumberFragment.newInstance(mobileNum)
                                    , true, true, "");*/
                            break;
                        case FORGET_PASS:
                            getNavigator().pushCreatePasswordFragment();
                            /*((SplashActivity) getActivity()).pushFragments(CreateNewPasswordFragment.newInstance(mobileNum, otp),
                                    true, true, "Create_Pass_Frag");*/
                            break;

                        case SIGN_UP:
                            getNavigator().pushSignUpCompleteFragment();
                            /*((SplashActivity) getActivity()).pushFragments(new SignUpCompleteFragment(),
                                    true, true, "Signup_Complete_Frag");*/
                            break;
                    }

                    //getNavigator().onApiError(new ApiError(otpVerifyResponse.getErrorCode(), otpVerifyResponse.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiError(t);
            }
        });
    }

    public void resendOtp(String mobileNumber){
        Call<GeneralResponse> responeCall = getDataManager().resendOtp(mobileNumber);
        responeCall.enqueue(new ApiResponse<GeneralResponse>() {
            @Override
            public void onSuccess(GeneralResponse generalResponse, Headers headers) {
                if (generalResponse.isStatus()){
                    getNavigator().onApiSuccess();
                }else {
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
