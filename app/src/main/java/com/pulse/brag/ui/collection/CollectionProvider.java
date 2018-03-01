package com.pulse.brag.ui.collection;

import com.pulse.brag.ui.contactus.ContactUsFragment;
import com.pulse.brag.ui.contactus.ContactUsModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by alpesh.rathod on 2/28/2018.
 */

@Module
public abstract class CollectionProvider {

    @ContributesAndroidInjector(modules = CollectionModule.class)
    abstract CollectionFragment provideCollectionFragmentFactory();
}
