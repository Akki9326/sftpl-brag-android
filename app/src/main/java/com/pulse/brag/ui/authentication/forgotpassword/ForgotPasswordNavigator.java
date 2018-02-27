package com.pulse.brag.ui.authentication.forgotpassword;

import com.pulse.brag.ui.core.CoreNavigator;

/**
 * Created by alpesh.rathod on 2/14/2018.
 */

public interface ForgotPasswordNavigator extends CoreNavigator{

    void sendOtp();

    void pushOtpFragment();
    void pushFragmentOnSplash();
    void pushFragmentOnChangePassword();
}
