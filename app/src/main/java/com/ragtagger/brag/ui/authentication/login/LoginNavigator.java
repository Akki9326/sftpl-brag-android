package com.ragtagger.brag.ui.authentication.login;

import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.ragtagger.brag.ui.core.CoreNavigator;

/**
 * Created by alpesh.rathod on 2/12/2018.
 */

public interface LoginNavigator extends CoreNavigator {

    boolean onEditorActionPass(TextView textView, int i, KeyEvent keyEvent);

    void performClickHideUnhidePass();

    void performClickLogin();

    void noInternetAlert();

    void validLoginForm();

    void invalidLoginForm(String msg);

    void performClickSignUp(View view);

    void openSignUpFragment();

    void performClickContactUs(View view);

    void openContactUsFragment();

    void performClickForgotPassword(View view);

    void openForgotPassFragment();

    void openMainActivity();


}
