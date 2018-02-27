package com.pulse.brag.ui.authentication.login;

import android.view.KeyEvent;
import android.widget.TextView;

import com.pulse.brag.ui.core.CoreNavigator;

/**
 * Created by alpesh.rathod on 2/12/2018.
 */

public interface LoginNavigator extends CoreNavigator {

    void openSignUpFragment();
    void openContactUsFragment();
    void openForgotPassFragment();
    void openMainActivity();
    void login();
    void hideUnhidePass();
    boolean onEditorActionPass(TextView textView, int i, KeyEvent keyEvent);



}
