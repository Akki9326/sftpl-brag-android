package com.ragtagger.brag.ui.authentication.profile.changepassword;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by alpesh.rathod on 2/15/2018.
 */

@Module
public abstract class ChangePassProvider {

    @ContributesAndroidInjector(modules = ChangePassModule.class)
    abstract ChangePassFragment provideChangePasswordFragmentFactory();
}
