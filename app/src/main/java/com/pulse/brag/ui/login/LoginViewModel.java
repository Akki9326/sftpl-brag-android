package com.pulse.brag.ui.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pulse.brag.R;
import com.pulse.brag.data.IDataManager;
import com.pulse.brag.data.remote.ApiClient;
import com.pulse.brag.helper.PreferencesManager;
import com.pulse.brag.helper.Utility;
import com.pulse.brag.pojo.requests.LoginRequest;
import com.pulse.brag.pojo.response.LoginResponse;
import com.pulse.brag.ui.activities.MainActivity;
import com.pulse.brag.ui.core.CoreViewModel;
import com.pulse.brag.views.OnSingleClickListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        return getNavigator().onEditorActionPass(textView,actionId,keyEvent);
    }

    public void login(String mobile,String password){
        getNavigator().showLoginProgress();
        LoginRequest loginRequest = new LoginRequest(mobile, password);
        Call<LoginResponse> mLoginResponeCall = getDataManager().userLogin(loginRequest);
        mLoginResponeCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                getNavigator().hideLoginProgress();
                /*if (response.isSuccessful()) {
                    LoginResponse respone = response.body();
                    okhttp3.Headers headers = response.headers();
                    if (respone.isStatus()) {

                        setHeaderInPref(headers);
                        PreferencesManager.getInstance().setIsLogin(true);
                        PreferencesManager.getInstance().setUserData(new Gson().toJson(respone.getData()));
                        startActivity(new Intent(getActivity(), MainActivity.class));
                        getActivity().finish();
                        getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    } else {
                        Utility.showAlertMessage(getActivity(), respone.getErrorCode(), respone.getMessage());
                    }
                } else {
                    Utility.showAlertMessage(getActivity(), 1, null);
                }*/
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                getNavigator().hideLoginProgress();
                //Utility.showAlertMessage(getActivity(), t);
            }
        });
    }


}
