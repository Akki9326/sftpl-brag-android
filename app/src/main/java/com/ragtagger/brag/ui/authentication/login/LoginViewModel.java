package com.ragtagger.brag.ui.authentication.login;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.ragtagger.brag.R;
import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.remote.ApiResponse;
import com.ragtagger.brag.data.model.requests.QLogin;
import com.ragtagger.brag.data.model.response.RLogin;
import com.ragtagger.brag.ui.core.CoreViewModel;
import com.ragtagger.brag.callback.OnSingleClickListener;
import com.ragtagger.brag.utils.AlertUtils;
import com.ragtagger.brag.utils.Utility;
import com.ragtagger.brag.utils.Validation;

import okhttp3.Headers;
import retrofit2.Call;

/**
 * Created by alpesh.rathod on 2/12/2018.
 */

public class LoginViewModel extends CoreViewModel<LoginNavigator> {
    public LoginViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public View.OnClickListener onPassHideUnhideClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().performClickHideUnhidePass();
            }
        };
    }

    public View.OnClickListener onLoginClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().performClickLogin();
            }
        };
    }

    public View.OnClickListener onSignUpClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().performClickSignUp(v);
            }
        };
    }

    public View.OnClickListener onContactUsClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().performClickContactUs(v);
            }
        };
    }

    public View.OnClickListener onForgotPasswordClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().performClickForgotPassword(v);
            }
        };
    }

    public boolean onEditorActionPass(@NonNull final TextView textView, final int actionId,
                                      @Nullable final KeyEvent keyEvent) {
        return getNavigator().onEditorActionPass(textView, actionId, keyEvent);
    }

    void validateLoginForm(Activity activity, EditText mobile, EditText password) {
        if (Validation.isEmpty(mobile)) {
            getNavigator().invalidLoginForm(activity.getString(R.string.error_enter_mobile));
        } else if (!Validation.isValidMobileNum(mobile)) {
            getNavigator().invalidLoginForm(activity.getString(R.string.error_mobile_valid));
        } else if (Validation.isEmptyPassword(password)) {
            getNavigator().invalidLoginForm(activity.getString(R.string.error_pass));
        } else if (Utility.isConnection(activity)) {
            getNavigator().validLoginForm();
        } else {
            getNavigator().noInternetAlert();
        }

    }

    public void callLoginApi(String mobile, String password) {
        final QLogin loginRequest = new QLogin(mobile, password);
        Call<RLogin> mLoginResponseCall = getDataManager().userLogin(loginRequest);
        mLoginResponseCall.enqueue(new ApiResponse<RLogin>() {
            @Override
            public void onSuccess(RLogin loginResponse, Headers headers) {
                if (loginResponse.isStatus()) {
                    getNavigator().onApiSuccess();
                    getDataManager().setAccessToken(headers.get("access-token"));
                    getDataManager().setDeviceToken(FirebaseInstanceId.getInstance().getToken());
                    getDataManager().setIsLogin(true);
                    getDataManager().setUserData(new Gson().toJson(loginResponse.getData()));
                    getNavigator().openMainActivity();
                } else {
                    getNavigator().onApiError(new ApiError(loginResponse.getErrorCode(), loginResponse.getMessage()));
                }

            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiError(t);
            }
        });
    }


}
