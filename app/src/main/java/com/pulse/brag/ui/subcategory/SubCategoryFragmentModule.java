package com.pulse.brag.ui.subcategory;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import com.pulse.brag.data.IDataManager;
import com.pulse.brag.ui.login.LoginViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nikhil.vadoliya on 16-02-2018.
 */


@Module
public class SubCategoryFragmentModule {

    @Provides
    SubCategoryViewModel provideLoginViewModel(IDataManager dataManager){
        return new SubCategoryViewModel(dataManager);
    }
}