package com.ragtagger.brag.ui.more;

import com.ragtagger.brag.data.IDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alpesh.rathod on 2/26/2018.
 */

@Module
public class MoreModule {

    @Provides
    MoreViewModel provideMoreViewModel(IDataManager dataManager) {
        return new MoreViewModel(dataManager);
    }
}
