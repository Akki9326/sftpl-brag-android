package com.pulse.brag.ui.home.product.list;

import com.pulse.brag.ui.login.LogInFragment;
import com.pulse.brag.ui.login.LoginFragmentModule;

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
