package com.pulse.brag.ui.home;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import com.pulse.brag.ui.login.LogInFragment;
import com.pulse.brag.ui.login.LoginFragmentModule;

import dagger.Component;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by nikhil.vadoliya on 15-02-2018.
 */

@Module
public abstract class HomeFragmentProvider {

    @ContributesAndroidInjector(modules = HomeFragmentModule.class)
    abstract HomeFragment provideAboutFragmentFactory();
}
