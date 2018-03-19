package com.ragtagger.brag.ui.authentication.profile.changemobile;

import android.view.KeyEvent;
import android.widget.TextView;

import com.ragtagger.brag.ui.core.CoreNavigator;

/**
 * Created by alpesh.rathod on 2/16/2018.
 */

public interface ChangeMobNumberNavigator extends CoreNavigator {

    void done();

    void hideUnhidePass();

    boolean onEditorActionPass(TextView textView, int i, KeyEvent keyEvent);

    void finishActivity();

    void pushOTPFragment();
}
