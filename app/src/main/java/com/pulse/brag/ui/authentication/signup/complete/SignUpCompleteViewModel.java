package com.pulse.brag.ui.authentication.signup.complete;

import android.view.View;

import com.pulse.brag.callback.OnSingleClickListener;
import com.pulse.brag.data.IDataManager;
import com.pulse.brag.ui.core.CoreViewModel;

/**
 * Created by alpesh.rathod on 2/16/2018.
 */

public class SignUpCompleteViewModel extends CoreViewModel<SignUpCompleteNavigator> {
    public SignUpCompleteViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public View.OnClickListener onBackToLoginClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().backToLogin();
            }
        };
    }
}