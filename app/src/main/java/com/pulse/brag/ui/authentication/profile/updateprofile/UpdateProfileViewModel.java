package com.pulse.brag.ui.authentication.profile.updateprofile;

import android.databinding.ObservableField;
import android.view.View;

import com.pulse.brag.data.IDataManager;
import com.pulse.brag.ui.core.CoreViewModel;
import com.pulse.brag.callback.OnSingleClickListener;
import com.pulse.brag.utils.Common;

/**
 * Created by alpesh.rathod on 2/15/2018.
 */

public class UpdateProfileViewModel extends CoreViewModel<UpdateProfileNavigator> {

    private final ObservableField<String> firstName = new ObservableField<>();
    private final ObservableField<String> lastName = new ObservableField<>();
    private final ObservableField<String> email = new ObservableField<>();

    public ObservableField<String> getFirstName() {
        return firstName;
    }

    public void updateFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public ObservableField<String> getLastName() {
        return lastName;
    }

    public void updateLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public ObservableField<String> getEmail() {
        return email;
    }

    public void updateEmail(String email) {
        this.email.set(email);
    }

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

    public void updateUser(String mobile, String firstName, String lastName, String email) {
        getNavigator().showMsgProfileUpdated();
    }

    public void fetchUserProfile() {
        String firstName = getDataManager().getUserData().getFirstName();
        String lastName = getDataManager().getUserData().getLastName();
        String email = getDataManager().getUserData().getEmail();

        updateFirstName(Common.isNotNullOrEmpty(firstName) ? firstName : "");
        updateLastName(Common.isNotNullOrEmpty(lastName) ? lastName : "");
        updateEmail(Common.isNotNullOrEmpty(email) ? email : "");

    }
}
