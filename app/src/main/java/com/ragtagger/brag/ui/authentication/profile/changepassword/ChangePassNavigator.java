package com.ragtagger.brag.ui.authentication.profile.changepassword;

import android.view.KeyEvent;
import android.widget.TextView;

import com.ragtagger.brag.ui.core.CoreNavigator;

/**
 * Created by alpesh.rathod on 2/15/2018.
 */

public interface ChangePassNavigator extends CoreNavigator {

    void done();

    boolean onEditorActionConfirmPass(TextView textView, int i, KeyEvent keyEvent);

    void showMsgPasswordChange();
}
