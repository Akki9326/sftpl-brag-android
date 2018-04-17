package com.ragtagger.brag.ui.more;

import com.ragtagger.brag.ui.core.CoreNavigator;

/**
 * Created by alpesh.rathod on 2/26/2018.
 */

public interface MoreNavigator extends CoreNavigator {

    void performItemClickProfile();

    void performItemClickMyOrder();

    void performItemClickNotification();

    void performItemClickPrivacyPolicy();

    void performItemClickTermsConditions();

    void performItemClickFaqs();

    void performItemClickAboutUs();

    void performItemClickUpdateProfile();

    void performItemClickChangePassword();

    void performItemClickChangeMobileNumber();

    void performItemClickAddUpdateAddress();

    void performItemClickLogout();

    void onLogout();

}
