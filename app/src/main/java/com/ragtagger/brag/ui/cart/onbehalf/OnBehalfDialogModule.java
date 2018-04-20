package com.ragtagger.brag.ui.cart.onbehalf;

import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.ui.cart.editquantity.EditQtyDialogViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class OnBehalfDialogModule {

    @Provides
    OnBehalfDialogViewModel provideOnBehalfDialogViewModel(IDataManager dataManager) {
        return new OnBehalfDialogViewModel(dataManager);
    }
}
