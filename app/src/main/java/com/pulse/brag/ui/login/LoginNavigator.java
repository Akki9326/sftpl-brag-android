package com.pulse.brag.ui.login;

import android.view.KeyEvent;
import android.widget.TextView;

/**
 * Created by alpesh.rathod on 2/12/2018.
 */

public interface LoginNavigator {

    void openSignUpFragment();
    void openContactUsFragment();
    void openForgotPassFragment();
    void login();
    void hideUnhidePass();
    boolean onEditorActionPass(TextView textView, int i, KeyEvent keyEvent);

    void showLoginProgress();
    void hideLoginProgress();


}
