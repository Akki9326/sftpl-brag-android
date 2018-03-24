package com.ragtagger.brag.ui.authentication.otp;

import android.view.KeyEvent;
import android.widget.TextView;

import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.ui.core.CoreNavigator;

/**
 * Created by alpesh.rathod on 2/15/2018.
 */

public interface OTPNavigator extends CoreNavigator {

    void verifyOtp();

    void resendOtp();

    boolean onEditorActionPin(TextView textView, int i, KeyEvent keyEvent);

    //void pushChangeMobileFragment();

    void finishUserProfileActivity();

    void pushCreatePasswordFragment();

    void pushSignUpCompleteFragment();

    void onApiverifyOTPSuccess();

    void onApiverifyOTPError(ApiError error);
}
