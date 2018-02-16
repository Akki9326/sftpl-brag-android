package com.pulse.brag.di.builder;

import com.pulse.brag.ui.category.CategoryFragmentProvider;
import com.pulse.brag.ui.home.HomeFragmentModule;
import com.pulse.brag.ui.home.HomeFragmentProvider;
import com.pulse.brag.ui.main.MainActivity;
import com.pulse.brag.ui.forgotpassword.ForgotPasswordProvider;
import com.pulse.brag.ui.login.LoginFragmentProvider;
import com.pulse.brag.ui.main.MainActivityModule;
import com.pulse.brag.ui.splash.SplashActivity;
import com.pulse.brag.ui.splash.SplashActivityModule;
import com.pulse.brag.ui.subcategory.SubCategoryFragmentProvider;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by alpesh.rathod on 2/9/2018.
 */

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {SplashActivityModule.class, LoginFragmentProvider.class, ForgotPasswordProvider.class})
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector(modules = {MainActivityModule.class, HomeFragmentProvider.class
            , CategoryFragmentProvider.class, SubCategoryFragmentProvider.class})
    abstract MainActivity bindMainActivity();
}
