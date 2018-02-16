package com.pulse.brag.ui.contactus;

import com.pulse.brag.ui.createnewpassord.CreateNewPasswordFragment;
import com.pulse.brag.ui.createnewpassord.CreateNewPasswordModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by alpesh.rathod on 2/16/2018.
 */

@Module
public abstract class ContactUsProvider {
    @ContributesAndroidInjector(modules = ContactUsModule.class)
    abstract ContactUsFragment provideContactUsFragmentFragmentFactory();
}
