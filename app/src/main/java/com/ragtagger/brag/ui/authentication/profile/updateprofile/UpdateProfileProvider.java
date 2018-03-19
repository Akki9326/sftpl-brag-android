package com.ragtagger.brag.ui.authentication.profile.updateprofile;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by alpesh.rathod on 2/15/2018.
 */

@Module
public abstract class UpdateProfileProvider {

    @ContributesAndroidInjector(modules = UpdateProfileModule.class)
    abstract UpdateProfileFragment provideUserProfileFragmentFactory();
}
