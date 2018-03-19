package com.ragtagger.brag.ui.home.category;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import com.ragtagger.brag.data.IDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nikhil.vadoliya on 15-02-2018.
 */

@Module
public class CategoryFragmentModule {

    @Provides
    CategoryViewModel provideCategoryViewModel(IDataManager dataManager){
        return new CategoryViewModel(dataManager);
    }
}
