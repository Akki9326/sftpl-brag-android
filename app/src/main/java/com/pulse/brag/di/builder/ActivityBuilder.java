package com.pulse.brag.di.builder;

import com.pulse.brag.ui.login.LoginFragmentProvider;
import com.pulse.brag.ui.splash.SplashActivity;
import com.pulse.brag.ui.splash.SplashActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by alpesh.rathod on 2/9/2018.
 */

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {SplashActivityModule.class, LoginFragmentProvider.class})
    abstract SplashActivity bindSplashActivity();
}
