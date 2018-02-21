package com.pulse.brag.ui.cart.placeorder;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import com.pulse.brag.ui.cart.CartFragment;
import com.pulse.brag.ui.cart.CartFragmentModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by nikhil.vadoliya on 21-02-2018.
 */

@Module
public abstract  class PlaceOrderFragmentProvider {
    @ContributesAndroidInjector(modules = PlaceOrderFragmentModule.class)
    abstract PlaceOrderFragment provideAboutFragmentFactory();
}
