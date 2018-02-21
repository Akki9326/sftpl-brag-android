package com.pulse.brag.ui.editquantity;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import com.pulse.brag.data.IDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nikhil.vadoliya on 20-02-2018.
 */


@Module
public class EditQtytDialogModule {
    @Provides
    EditQtyDialogViewModel provideEditQtyDialogViewModel(IDataManager dataManager){
        return new EditQtyDialogViewModel(dataManager);
    }
}
