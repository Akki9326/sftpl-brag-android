package com.pulse.brag.ui.authentication.otp;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by alpesh.rathod on 2/15/2018.
 */

@Module
public abstract class OTPFragmentProvider {

    @ContributesAndroidInjector(modules = OTPFragmentModule.class)
    abstract OTPFragment provideOTPFragmentFactory();
}
