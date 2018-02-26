package com.pulse.brag.ui.order;


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
 * Created by nikhil.vadoliya on 21-02-2018.
 */


@Module
public abstract class MyOrderFragmentProvider {
    @ContributesAndroidInjector(modules = MyOrderFragmentModule.class)
    abstract MyOrderFragment provideMyOrderFragmentFactory();
}
