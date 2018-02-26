package com.pulse.brag.ui.more;

import com.pulse.brag.ui.myorder.MyOrderFragment;
import com.pulse.brag.ui.myorder.MyOrderFragmentModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by alpesh.rathod on 2/26/2018.
 */

@Module
public abstract class MoreProvider {

    @ContributesAndroidInjector(modules = MoreModule.class)
    abstract MoreFragment provideMoreFragmentFactory();
}
