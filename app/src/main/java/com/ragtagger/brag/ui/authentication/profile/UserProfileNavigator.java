package com.ragtagger.brag.ui.authentication.profile;

import com.ragtagger.brag.ui.core.CoreNavigator;

/**
 * Created by alpesh.rathod on 2/15/2018.
 */

public interface UserProfileNavigator extends CoreNavigator{

    void backPress();

    void pushChangePassFragment();

    void pushChangeMobileNoFragment();

    void pushUserProfileFragment();

    void pushAddEditAddress();

    void pushOtpFragment();
}
