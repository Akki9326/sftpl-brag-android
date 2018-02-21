package com.pulse.brag.ui.profile;

import com.pulse.brag.ui.core.CoreNavigator;

/**
 * Created by alpesh.rathod on 2/15/2018.
 */

public interface UserProfileNavigator extends CoreNavigator{

    void backPress();

    void pushChangePassFragment();

    void pushForgotPassFragment();

    void pushUserProfileFragment();
}