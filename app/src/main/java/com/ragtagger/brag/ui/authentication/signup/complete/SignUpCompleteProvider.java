package com.ragtagger.brag.ui.authentication.signup.complete;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by alpesh.rathod on 2/16/2018.
 */

@Module
public abstract class SignUpCompleteProvider {
    @ContributesAndroidInjector(modules = SignUpCompleteModule.class)
    abstract SignUpCompleteFragment provideSignUpCompleteFragment();
}
