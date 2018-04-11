package com.ragtagger.brag.ui.authentication.signup;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ragtagger.brag.R;
import com.ragtagger.brag.callback.OnSingleClickListener;
import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.remote.ApiResponse;
import com.ragtagger.brag.data.model.requests.QSignUp;
import com.ragtagger.brag.data.model.response.RSignUp;
import com.ragtagger.brag.ui.core.CoreViewModel;
import com.ragtagger.brag.utils.AlertUtils;
import com.ragtagger.brag.utils.Utility;
import com.ragtagger.brag.utils.Validation;

import okhttp3.Headers;
import retrofit2.Call;

/**
 * Created by alpesh.rathod on 2/15/2018.
 */

public class SignUpViewModel extends CoreViewModel<SignUpNavigator> {

    public SignUpViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public View.OnClickListener onTypeDropdownClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().performClickUserTypeDropdown(v);
            }
        };
    }

    public View.OnClickListener onChannelDropdownClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().performClickChannelDropdown(v);
            }
        };
    }

    public View.OnClickListener onSaleTypeDropdownClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().performClickSaleTypeDropdown(v);
            }
        };
    }

    public View.OnClickListener onStateClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().performClickState(v);
            }
        };
    }

    public View.OnClickListener onSignUpClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().performClickSignUp();
            }
        };
    }

    public boolean onEditorActionConfirmPass(@NonNull final TextView textView, final int actionId,
                                             @Nullable final KeyEvent keyEvent) {
        return getNavigator().onEditorActionConfirmPass(textView, actionId, keyEvent);
    }

    void callSignUpRequiredDataApi() {

    }

    void validateSignUpForm(Activity activity, EditText firstName, EditText email, EditText mobile, EditText password, EditText confirmPassword, EditText gstin) {
        if (Validation.isEmpty(firstName)) {
            getNavigator().invalidSignUpForm(activity.getString(R.string.error_please_enter_first_name));
        } else if (Validation.isEmpty(email)) {
            getNavigator().invalidSignUpForm(activity.getString(R.string.error_please_email));
        } else if (!Validation.isEmailValid(email)) {
            getNavigator().invalidSignUpForm(activity.getString(R.string.error_email_valid));
        } else if (Validation.isEmpty(mobile)) {
            getNavigator().invalidSignUpForm(activity.getString(R.string.error_enter_mobile));
        } else if (!Validation.isValidMobileNum(mobile)) {
            getNavigator().invalidSignUpForm(activity.getString(R.string.error_mobile_valid));
        } else if (Validation.isEmptyPassword(password)) {
            getNavigator().invalidSignUpForm(activity.getString(R.string.error_pass));
        } else if (Validation.isEmptyPassword(confirmPassword)) {
            getNavigator().invalidSignUpForm(activity.getString(R.string.error_confirm_pass));
        } else if (!(password.getText().toString().equals(confirmPassword.getText().toString()))) {
            getNavigator().invalidSignUpForm(activity.getString(R.string.error_password_not_match));
        } else if (Validation.isEmpty(gstin)) {
            getNavigator().invalidSignUpForm(activity.getString(R.string.error_enter_gst));
        } else if (!Validation.isValidGST(gstin)) {
            getNavigator().invalidSignUpForm(activity.getString(R.string.error_gst_valid));
        } else if (Utility.isConnection(activity)) {
            getNavigator().validSignUpForm();
        } else {
            getNavigator().noInternet();
        }
    }


    void callSignUpApi(String firstname, String lastname, String email, String mobilenumber, String password, int userType, String gstin) {
        QSignUp signInRequest = new QSignUp(firstname, lastname, email, mobilenumber, password, userType, gstin);
        Call<RSignUp> mSignUpResponeCall = getDataManager().userSignIn(signInRequest);
        mSignUpResponeCall.enqueue(new ApiResponse<RSignUp>() {
            @Override
            public void onSuccess(RSignUp signUpResponse, Headers headers) {
                if (signUpResponse.isStatus()) {
                    getNavigator().onApiSuccess();
                    getNavigator().pushOtpFragment();
                } else {
                    getNavigator().onApiError(new ApiError(signUpResponse.getErrorCode(), signUpResponse.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiError(t);
            }
        });
    }

    void signUp(QSignUp signInRequest) {
        Call<RSignUp> mSignUpResponeCall = getDataManager().userSignIn(signInRequest);
        mSignUpResponeCall.enqueue(new ApiResponse<RSignUp>() {
            @Override
            public void onSuccess(RSignUp signUpResponse, Headers headers) {
                if (signUpResponse.isStatus()) {
                    getNavigator().onApiSuccess();
                    getNavigator().pushOtpFragment();
                } else {
                    getNavigator().onApiError(new ApiError(signUpResponse.getErrorCode(), signUpResponse.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiError(t);
            }
        });

    }
}
