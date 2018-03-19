package com.ragtagger.brag.ui.main;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.databinding.ObservableField;

import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.ui.core.CoreViewModel;

/**
 * Created by nikhil.vadoliya on 14-02-2018.
 */


public class MainViewModel extends CoreViewModel<MainNavigator> {
    public MainViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    ObservableField<String> toolbarTitle = new ObservableField<>();

    public ObservableField<String> getToolbarTitle() {
        return toolbarTitle;
    }

    public void updateToolbartitle(String title) {
        toolbarTitle.set(title);
    }
}
