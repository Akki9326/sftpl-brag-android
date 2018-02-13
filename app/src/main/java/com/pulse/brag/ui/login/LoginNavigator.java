package com.pulse.brag.ui.login;

import android.view.KeyEvent;
import android.widget.TextView;

import com.pulse.brag.data.model.ApiError;

/**
 * Created by alpesh.rathod on 2/12/2018.
 */

public interface LoginNavigator {

    void onApiSuccess();
    void onApiError(ApiError error);

    void openSignUpFragment();
    void openContactUsFragment();
    void openForgotPassFragment();
    void openMainActivity();
    void login();
    void hideUnhidePass();
    boolean onEditorActionPass(TextView textView, int i, KeyEvent keyEvent);



}
