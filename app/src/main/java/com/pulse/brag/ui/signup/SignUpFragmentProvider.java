package com.pulse.brag.ui.signup;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by alpesh.rathod on 2/15/2018.
 */

@Module
public abstract class SignUpFragmentProvider {
    @ContributesAndroidInjector(modules = SignUpFragmentModule.class)
    abstract SignUpFragment provideSignUpFragmentFactory();
}
