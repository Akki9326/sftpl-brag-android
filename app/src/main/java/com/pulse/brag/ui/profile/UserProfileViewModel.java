package com.pulse.brag.ui.profile;

import android.databinding.ObservableField;
import android.view.View;

import com.pulse.brag.data.IDataManager;
import com.pulse.brag.ui.core.CoreViewModel;
import com.pulse.brag.utils.Constants;
import com.pulse.brag.callback.OnSingleClickListener;

/**
 * Created by alpesh.rathod on 2/15/2018.
 */

public class UserProfileViewModel extends CoreViewModel<UserProfileNavigator> {

    private final ObservableField<String> toolbarTitle = new ObservableField<>();

    public UserProfileViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public ObservableField<String> getToolbarTitle() {
        return toolbarTitle;
    }

    public void updateToolbarTitle(String title) {
        toolbarTitle.set(title);
    }

    public View.OnClickListener onImgBackClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().backPress();
            }
        };
    }

    public void decideNextFragment(int type) {
        switch (Constants.ProfileIsFrom.values()[type]){
            case CHANGE_MOBILE:
                getNavigator().pushForgotPassFragment();
                break;
            case CHANGE_PASS:
                getNavigator().pushChangePassFragment();
                break;
            case UPDATE_PROFILE:
                getNavigator().pushUserProfileFragment();
                break;
        }

    }
}
