package com.ragtagger.brag.ui.authentication.createnewpassord;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by alpesh.rathod on 2/16/2018.
 */

@Module
public abstract class CreateNewPasswordProvider {

    @ContributesAndroidInjector(modules = CreateNewPasswordModule.class)
    abstract CreateNewPasswordFragment provideCreateNewPasswordFragmentFactory();
}
