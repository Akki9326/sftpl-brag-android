package com.pulse.brag.ui.home.product.quickadd;

import com.pulse.brag.ui.home.product.list.ProductListFragment;
import com.pulse.brag.ui.home.product.list.ProductListModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by alpesh.rathod on 2/20/2018.
 */

@Module
public abstract class AddProductDialogProvider {
    @ContributesAndroidInjector(modules = AddProductDialogModule.class)
    abstract AddProductDialogFragment provideAddProductDialogFragmentFactory();
}
