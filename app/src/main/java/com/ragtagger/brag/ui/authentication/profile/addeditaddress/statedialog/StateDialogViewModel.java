package com.ragtagger.brag.ui.authentication.profile.addeditaddress.statedialog;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.view.View;

import com.ragtagger.brag.callback.OnSingleClickListener;
import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.ui.core.CoreViewModel;

/**
 * Created by nikhil.vadoliya on 07-03-2018.
 */


public class StateDialogViewModel extends CoreViewModel<StateDialogNavigator> {

    public StateDialogViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public View.OnClickListener onClose() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().onClose();
            }
        };
    }
}
