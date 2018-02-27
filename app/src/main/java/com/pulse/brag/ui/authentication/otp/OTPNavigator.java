package com.pulse.brag.ui.authentication.otp;

import android.view.KeyEvent;
import android.widget.TextView;

import com.pulse.brag.ui.core.CoreNavigator;

/**
 * Created by alpesh.rathod on 2/15/2018.
 */

public interface OTPNavigator extends CoreNavigator {

    void verifyOtp();

    void resendOtp();

    boolean onEditorActionPin(TextView textView, int i, KeyEvent keyEvent);

    void pushChangeMobileFragment();

    void pushCreatePasswordFragment();

    void pushSignUpCompleteFragment();
}
