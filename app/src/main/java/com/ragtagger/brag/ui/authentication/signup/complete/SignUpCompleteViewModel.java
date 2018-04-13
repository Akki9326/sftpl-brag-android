package com.ragtagger.brag.ui.authentication.signup.complete;

import android.view.View;

import com.ragtagger.brag.callback.OnSingleClickListener;
import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.ui.core.CoreViewModel;

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
                getNavigator().performClickBackToLogin();
            }
        };
    }
}
