package com.ragtagger.brag.ui.home.product.details;

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
