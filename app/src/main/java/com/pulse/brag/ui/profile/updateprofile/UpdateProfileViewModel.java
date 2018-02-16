package com.pulse.brag.ui.profile.updateprofile;

import android.view.View;

import com.pulse.brag.data.IDataManager;
import com.pulse.brag.ui.core.CoreViewModel;
import com.pulse.brag.views.OnSingleClickListener;

/**
 * Created by alpesh.rathod on 2/15/2018.
 */

public class UpdateProfileViewModel extends CoreViewModel<UpdateProfileNavigator> {
    public UpdateProfileViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public View.OnClickListener onUpdateClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().update();
            }
        };
    }

    public void updateUser(String mobile, String firstName, String lastName, String email){
            getNavigator().showMsgProfileUpdated();
    }
}
