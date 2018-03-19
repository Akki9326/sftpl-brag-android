package com.ragtagger.brag.ui.authentication.profile;

import com.ragtagger.brag.data.IDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alpesh.rathod on 2/15/2018.
 */

@Module
public class UserProfileModule {

    @Provides
    UserProfileViewModel providePassMobViewModel(IDataManager dataManager){
        return new UserProfileViewModel(dataManager);
    }
}
