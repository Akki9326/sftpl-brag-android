package com.pulse.brag.ui.authentication.login;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.pulse.brag.data.IDataManager;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.data.remote.ApiResponse;
import com.pulse.brag.pojo.requests.LoginRequest;
import com.pulse.brag.pojo.response.LoginResponse;
import com.pulse.brag.ui.core.CoreViewModel;
import com.pulse.brag.callback.OnSingleClickListener;

import okhttp3.Headers;
import retrofit2.Call;

/**
 * Created by alpesh.rathod on 2/12/2018.
 */

public class LoginViewModel extends CoreViewModel<LoginNavigator> {
    public LoginViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public View.OnClickListener onLoginClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().login();
            }
        };
    }

    public View.OnClickListener onSignUpClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().openSignUpFragment();
            }
        };
    }

    public View.OnClickListener onContactUsClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().openContactUsFragment();
            }
        };
    }

    public View.OnClickListener onPassHideUnhideClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().hideUnhidePass();
            }
        };
    }

    public View.OnClickListener onForgotPasswordClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().openForgotPassFragment();
            }
        };
    }

    public boolean onEditorActionPass(@NonNull final TextView textView, final int actionId,
                                      @Nullable final KeyEvent keyEvent) {
        return getNavigator().onEditorActionPass(textView, actionId, keyEvent);
    }

    public void login(String mobile, String password) {
        final LoginRequest loginRequest = new LoginRequest(mobile, password);
        Call<LoginResponse> mLoginResponseCall = getDataManager().userLogin(loginRequest);
        mLoginResponseCall.enqueue(new ApiResponse<LoginResponse>() {
            @Override
            public void onSuccess(LoginResponse loginResponse, Headers headers) {
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
