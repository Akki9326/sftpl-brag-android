package com.ragtagger.brag.ui.home.subcategory;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by nikhil.vadoliya on 16-02-2018.
 */

@Module
public abstract  class SubCategoryFragmentProvider {
    @ContributesAndroidInjector(modules = SubCategoryFragmentModule.class)
    abstract SubCategoryFragment provideAboutFragmentFactory();
}
