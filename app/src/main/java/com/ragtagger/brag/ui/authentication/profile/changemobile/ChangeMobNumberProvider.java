package com.ragtagger.brag.ui.authentication.profile.changemobile;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by alpesh.rathod on 2/16/2018.
 */

@Module
public abstract class ChangeMobNumberProvider {

    @ContributesAndroidInjector(modules = ChangeMobNumberModule.class)
    abstract ChangeMobileNumberFragment provideChangeMobNumberFragmentFactory();
}
