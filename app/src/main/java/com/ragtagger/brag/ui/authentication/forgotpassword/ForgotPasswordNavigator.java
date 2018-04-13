package com.ragtagger.brag.ui.authentication.forgotpassword;

import com.ragtagger.brag.ui.core.CoreNavigator;

/**
 * Created by alpesh.rathod on 2/14/2018.
 */

public interface ForgotPasswordNavigator extends CoreNavigator {

    void performClickSendOtp();

    void noInternetAlert();

    void validOtpForm();

    void invalidOtpForm(String msg);

    void pushOTPFragmentOnSplashActivity();
}
