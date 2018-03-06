package com.pulse.brag.ui.addeditaddress;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.pulse.brag.callback.OnSingleClickListener;
import com.pulse.brag.data.IDataManager;
import com.pulse.brag.ui.core.CoreViewModel;

/**
 * Created by nikhil.vadoliya on 06-03-2018.
 */


public class AddEditViewModel extends CoreViewModel<AddEditNavigator> {

    public AddEditViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public View.OnClickListener onAddOrUpdate(){
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().onAddOrUpdateAddress();
            }
        };
    }

    public boolean onEditorActionPincode(@NonNull final TextView textView, final int actionId,
                                             @Nullable final KeyEvent keyEvent) {
        return getNavigator().onEditorActionPincode(textView, actionId, keyEvent);
    }
}
