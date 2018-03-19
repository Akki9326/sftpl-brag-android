package com.ragtagger.brag.ui.collection;

import com.ragtagger.brag.data.IDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alpesh.rathod on 2/28/2018.
 */

@Module
public class CollectionModule {

    @Provides
    CollectionViewModel provideCollectionViewModel(IDataManager dataManager) {
        return new CollectionViewModel(dataManager);
    }
}
