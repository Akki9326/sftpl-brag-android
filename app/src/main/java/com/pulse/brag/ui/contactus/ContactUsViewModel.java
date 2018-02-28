package com.pulse.brag.ui.contactus;

import android.view.View;

import com.pulse.brag.callback.OnSingleClickListener;
import com.pulse.brag.data.IDataManager;
import com.pulse.brag.ui.core.CoreViewModel;

/**
 * Created by alpesh.rathod on 2/16/2018.
 */

public class ContactUsViewModel extends CoreViewModel<ContactUsNavigator> {
    public ContactUsViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public View.OnClickListener onSendClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().send();
            }
        };
    }
}
