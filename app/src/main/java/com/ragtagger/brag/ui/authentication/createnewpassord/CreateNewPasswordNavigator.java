package com.ragtagger.brag.ui.authentication.createnewpassord;

import com.ragtagger.brag.ui.core.CoreNavigator;

/**
 * Created by alpesh.rathod on 2/16/2018.
 */

public interface CreateNewPasswordNavigator extends CoreNavigator {

    void performClickDone();

    void noInternetAlert();

    void validNewPasswordForm();

    void invalidNewPasswordForm(String msg);

    void onChangePasswordSuccess();
}
