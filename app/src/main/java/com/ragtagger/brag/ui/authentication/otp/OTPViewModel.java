package com.ragtagger.brag.ui.authentication.otp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ragtagger.brag.R;
import com.ragtagger.brag.callback.OnSingleClickListener;
import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.datas.DataUser;
import com.ragtagger.brag.data.model.requests.QChangeMobileNumber;
import com.ragtagger.brag.data.model.requests.QGenerateOtpForChangeMobile;
import com.ragtagger.brag.data.remote.ApiResponse;
import com.ragtagger.brag.data.model.response.RGeneralData;
import com.ragtagger.brag.data.model.response.ROTPVerify;
import com.ragtagger.brag.ui.core.CoreViewModel;
import com.ragtagger.brag.utils.AlertUtils;
import com.ragtagger.brag.utils.Constants;
import com.ragtagger.brag.utils.Utility;
import com.ragtagger.brag.views.PinView;

import okhttp3.Headers;
import retrofit2.Call;

import static com.ragtagger.brag.utils.Constants.OTPValidationIsFrom.CHANGE_MOBILE;
import static com.ragtagger.brag.utils.Constants.OTPValidationIsFrom.FORGET_PASS;

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
                getNavigator().performClickVerifyOtp();
            }
        };
    }

    public View.OnClickListener onResendOtpClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().performClickResendOtp();
            }
        };
    }

    public boolean onEditorActionPin(@NonNull final TextView textView, final int actionId,
                                     @Nullable final KeyEvent keyEvent) {
        return getNavigator().onEditorActionPin(textView, actionId, keyEvent);
    }

    void validateOtpForm(Activity activity, PinView pinView) {
        if (pinView.getText().toString().equals("")) {
            getNavigator().inValidOtpForm(activity.getString(R.string.error_enter_otp));
        } else if (pinView.getText().toString().length() < 6) {
            getNavigator().inValidOtpForm(activity.getString(R.string.error_code_6));
        } else if (Utility.isConnection(activity)) {
            getNavigator().validOtpForm();
        } else {
            getNavigator().noInternetAlert();
        }
    }

    public void callResendOtpApi(String mobileNumber, final int formType, String password) {
        Call<RGeneralData> responeCall;
        switch (Constants.OTPValidationIsFrom.values()[formType]) {
            case SIGN_UP:
                responeCall = getDataManager().resendOtp(mobileNumber);
                break;
            case FORGET_PASS:
                responeCall = getDataManager().resendForgotOtp(mobileNumber);
                break;
            case CHANGE_MOBILE:
                responeCall = getDataManager().generateOTPForMobileChange(new QGenerateOtpForChangeMobile(mobileNumber, password));
                break;
            default:
                responeCall = getDataManager().resendOtp(mobileNumber);
                break;
        }


        responeCall.enqueue(new ApiResponse<RGeneralData>() {
            @Override
            public void onSuccess(RGeneralData generalResponse, Headers headers) {
                if (generalResponse.isStatus()) {
                    getNavigator().onOtpVerifySuccessfully();
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

    public void callVerifyOtpApi(String mobileNum, String otp, final int formType) {
        Call<ROTPVerify> mOtpVerifyResponeCall = getDataManager().verifyOtp(mobileNum, otp);

        mOtpVerifyResponeCall.enqueue(new ApiResponse<ROTPVerify>() {
            @Override
            public void onSuccess(ROTPVerify otpVerifyResponse, Headers headers) {
                if (otpVerifyResponse.isStatus()) {
                    getNavigator().onApiSuccess();
                    getNavigator().pushSignUpCompleteFragment();
                } else {
                    getNavigator().onApiError(new ApiError(otpVerifyResponse.getErrorCode(), otpVerifyResponse.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiError(t);
            }
        });
    }

    public void callVerifyOtpForForgotPassApi(String mobileNum, String otp, final int formType) {
        Call<ROTPVerify> mOtpVerifyResponeCall = getDataManager().verifyOtpForgetPass(mobileNum, otp);

        mOtpVerifyResponeCall.enqueue(new ApiResponse<ROTPVerify>() {
            @Override
            public void onSuccess(ROTPVerify otpVerifyResponse, Headers headers) {
                if (otpVerifyResponse.isStatus()) {
                    getNavigator().onApiSuccess();
                    getNavigator().pushCreatePasswordFragment();
                } else {
                    getNavigator().onApiError(new ApiError(otpVerifyResponse.getErrorCode(), otpVerifyResponse.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiError(t);
            }
        });
    }

    public void callVerifyOtpForChangeMobApi(final String mobile, String password, String otp) {
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
