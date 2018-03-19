package com.ragtagger.brag.ui.home.product.list.filter;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by alpesh.rathod on 2/22/2018.
 */

@Module
public abstract class ProductFilterDialogProvider {

    @ContributesAndroidInjector(modules = ProductFilterDialogModule.class)
    abstract ProductFilterDialogFragment provideProductFilterDialogFragmentFactory();
}
