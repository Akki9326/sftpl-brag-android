package com.ragtagger.brag.ui.authentication.signup;

import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.ragtagger.brag.ui.core.CoreNavigator;

/**
 * Created by alpesh.rathod on 2/15/2018.
 */

public interface SignUpNavigator extends CoreNavigator {

    boolean onEditorActionConfirmPass(TextView textView, int i, KeyEvent keyEvent);

    void performClickUserTypeDropdown(View view);

    void performClickChannelDropdown(View view);

    void performClickSaleTypeDropdown(View view);

    void performClickState(View view);

    void performClickSignUp();

    void noInternet();

    void validSignUpForm();

    void invalidSignUpForm(String msg);

    void pushOtpFragment();
}
