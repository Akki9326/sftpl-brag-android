package com.pulse.brag.ui.home.product.details;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import com.pulse.brag.data.IDataManager;
import com.pulse.brag.ui.home.product.list.ProductListFragment;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alpesh.rathod on 2/19/2018.
 */

@Module
public class ProductDetailModule {

    @Provides
    ProductDetailViewModel provideProductDetailViewMode(IDataManager dataManager){
        return new ProductDetailViewModel(dataManager);
    }

    @Provides
    @Named("forColor")
    LinearLayoutManager provideColorLinearLayoutManager(ProductDetailFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity(),LinearLayoutManager.HORIZONTAL,false);
    }

    @Provides
    @Named("forSize")
    LinearLayoutManager provideSizeLinearLayoutManager(ProductDetailFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity(),LinearLayoutManager.HORIZONTAL,false);
    }
}
