package com.pulse.brag.ui.home.product.list.sorting;

import com.pulse.brag.ui.home.product.quickadd.AddProductDialogFragment;
import com.pulse.brag.ui.home.product.quickadd.AddProductDialogModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by alpesh.rathod on 2/21/2018.
 */

@Module
public abstract class ProductSortingDialogProvider {

    @ContributesAndroidInjector(modules = ProductSortingDialogModule.class)
    abstract ProductSortingDialogFragment provideProductSortingDialogFragmentFactory();
}
