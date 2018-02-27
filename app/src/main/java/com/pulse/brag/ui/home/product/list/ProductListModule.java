package com.pulse.brag.ui.home.product.list;

import android.support.v7.widget.GridLayoutManager;

import com.pulse.brag.data.IDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alpesh.rathod on 2/19/2018.
 */

@Module
public class ProductListModule {

    @Provides
    ProductListViewModel provideProductListViewModel(IDataManager dataManager){
        return new ProductListViewModel(dataManager);
    }

    @Provides
    GridLayoutManager provideLinearLayoutManager(ProductListFragment fragment) {
        return new GridLayoutManager(fragment.getActivity(),2);
    }
}
