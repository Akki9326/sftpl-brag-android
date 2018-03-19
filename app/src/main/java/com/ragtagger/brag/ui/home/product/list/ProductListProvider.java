package com.ragtagger.brag.ui.home.product.list;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by alpesh.rathod on 2/19/2018.
 */

@Module
public abstract class ProductListProvider {

    @ContributesAndroidInjector(modules = ProductListModule.class)
    abstract ProductListFragment provideProductListFragmentFactory();
}
