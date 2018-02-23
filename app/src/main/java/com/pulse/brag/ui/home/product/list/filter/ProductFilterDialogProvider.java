package com.pulse.brag.ui.home.product.list.filter;

import com.pulse.brag.ui.home.product.list.sorting.ProductSortingDialogFragment;
import com.pulse.brag.ui.home.product.list.sorting.ProductSortingDialogModule;

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
