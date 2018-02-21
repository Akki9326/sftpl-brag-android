package com.pulse.brag.ui.home.product.list.sorting;

import com.pulse.brag.data.IDataManager;
import com.pulse.brag.ui.home.product.quickadd.AddProductDialogViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alpesh.rathod on 2/21/2018.
 */

@Module
public class ProductSortingDialogModule {

    @Provides
    ProductSortingDialogViewModel provideProductSortingDialogViewModel(IDataManager dataManager){
        return new ProductSortingDialogViewModel(dataManager);
    }
}
