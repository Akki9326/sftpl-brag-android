package com.pulse.brag.ui.more;


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
