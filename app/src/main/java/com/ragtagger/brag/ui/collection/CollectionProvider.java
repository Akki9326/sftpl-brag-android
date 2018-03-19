package com.ragtagger.brag.ui.collection;

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
