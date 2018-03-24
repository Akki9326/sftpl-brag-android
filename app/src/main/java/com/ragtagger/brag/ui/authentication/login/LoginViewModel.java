package com.ragtagger.brag.ui.authentication.login;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.remote.ApiResponse;
import com.ragtagger.brag.data.model.requests.QLogin;
import com.ragtagger.brag.data.model.response.RLogin;
import com.ragtagger.brag.ui.core.CoreViewModel;
import com.ragtagger.brag.callback.OnSingleClickListener;

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