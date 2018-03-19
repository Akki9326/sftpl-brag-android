package com.ragtagger.brag.ui.home.product.quickadd;

import com.ragtagger.brag.data.IDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alpesh.rathod on 2/20/2018.
 */

@Module
public class AddProductDialogModule {

    @Provides
    AddProductDialogViewModel provideAddProductDialogViewModel(IDataManager dataManager){
        return new AddProductDialogViewModel(dataManager);
    }
}
