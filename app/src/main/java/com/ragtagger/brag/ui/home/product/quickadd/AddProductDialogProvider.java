package com.ragtagger.brag.ui.home.product.quickadd;

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
