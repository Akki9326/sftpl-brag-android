package com.pulse.brag.ui.forgotpassword;

import com.pulse.brag.ui.login.LogInFragment;
import com.pulse.brag.ui.login.LoginFragmentModule;

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
