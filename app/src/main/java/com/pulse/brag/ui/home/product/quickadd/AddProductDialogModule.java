package com.pulse.brag.ui.home.product.quickadd;

import com.pulse.brag.data.IDataManager;
import com.pulse.brag.ui.home.product.list.ProductListViewModel;

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
