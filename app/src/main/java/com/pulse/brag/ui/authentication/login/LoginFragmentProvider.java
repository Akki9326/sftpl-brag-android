package com.pulse.brag.ui.authentication.login;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by alpesh.rathod on 2/12/2018.
 */

@Module
public abstract class LoginFragmentProvider {

    @ContributesAndroidInjector(modules = LoginFragmentModule.class)
    abstract LogInFragment provideLoginFragmentFactory();
}
