package com.ragtagger.brag.ui.home.product.list.filter;

import android.support.v7.widget.GridLayoutManager;

import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.utils.Common;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alpesh.rathod on 2/22/2018.
 */

@Module
public class ProductFilterDialogModule {

    @Provides
    ProductFilterDialogViewModel provideProductFilterDialogViewModel(IDataManager dataManager) {
        return new ProductFilterDialogViewModel(dataManager);
    }

    @Provides
    @Named("colorFilter")
    GridLayoutManager provideColorFilterLinearLayoutManager(ProductFilterDialogFragment fragment) {
        return new GridLayoutManager(fragment.getActivity(), Common.calculateNoOfColumns(fragment.getActivity(), 60));
    }

    @Provides
    @Named("sizeFilter")
    GridLayoutManager provideSizeFilterLinearLayoutManager(ProductFilterDialogFragment fragment) {
        return new GridLayoutManager(fragment.getActivity(), Common.calculateNoOfColumns(fragment.getActivity(), 60));
    }
}
