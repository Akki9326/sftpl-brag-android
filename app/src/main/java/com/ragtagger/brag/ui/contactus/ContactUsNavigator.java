package com.ragtagger.brag.ui.contactus;

import com.ragtagger.brag.ui.core.CoreNavigator;

/**
 * Created by alpesh.rathod on 2/16/2018.
 */

public interface ContactUsNavigator extends CoreNavigator {

    void performClickSend();

    void noInternetAlert();

    void validContactUsForm();

    void invalidContactUsForm(String msg);

    void goBack();
}
