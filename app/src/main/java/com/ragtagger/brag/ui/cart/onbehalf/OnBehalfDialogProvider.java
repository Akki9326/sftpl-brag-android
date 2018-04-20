package com.ragtagger.brag.ui.cart.onbehalf;

import com.ragtagger.brag.ui.cart.editquantity.EditQtyDialogFragment;
import com.ragtagger.brag.ui.cart.editquantity.EditQtytDialogModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class OnBehalfDialogProvider {
    @ContributesAndroidInjector(modules = OnBehalfDialogModule.class)
    abstract OnBehalfDialogFragment provideOnBehalfFragmentFactory();
}
