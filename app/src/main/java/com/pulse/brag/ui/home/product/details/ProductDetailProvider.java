package com.pulse.brag.ui.home.product.details;

import com.pulse.brag.ui.home.product.list.ProductListFragment;
import com.pulse.brag.ui.home.product.list.ProductListModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by alpesh.rathod on 2/19/2018.
 */

@Module
public abstract class ProductDetailProvider {

    @ContributesAndroidInjector(modules = ProductDetailModule.class)
    abstract ProductDetailFragment provideProductDetailFragmentFactory();
}
