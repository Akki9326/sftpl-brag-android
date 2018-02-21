package com.pulse.brag.ui.signup;

import android.view.KeyEvent;
import android.widget.TextView;

import com.pulse.brag.ui.core.CoreNavigator;

/**
 * Created by alpesh.rathod on 2/15/2018.
 */

public interface SignUpNavigator extends CoreNavigator {

    void signUp();

    boolean onEditorActionConfirmPass(TextView textView, int i, KeyEvent keyEvent);

    void pushOtpFragment();
}