package com.pulse.brag.ui.collection;

import com.pulse.brag.data.IDataManager;
import com.pulse.brag.ui.contactus.ContactUsViewModel;

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
