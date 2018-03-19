package com.ragtagger.brag.ui.authentication.forgotpassword;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by alpesh.rathod on 2/14/2018.
 */

@Module
public abstract class ForgotPasswordProvider {

    @ContributesAndroidInjector(modules = ForgotPasswordModule.class)
    abstract ForgetPasswordFragment provideForgotPasswordFragmentFactory();
}
