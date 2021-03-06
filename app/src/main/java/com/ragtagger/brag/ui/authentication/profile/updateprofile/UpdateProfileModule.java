package com.ragtagger.brag.ui.authentication.profile.updateprofile;

import com.ragtagger.brag.data.IDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alpesh.rathod on 2/15/2018.
 */

@Module
public class UpdateProfileModule {
    @Provides
    UpdateProfileViewModel provideUserProfileViewModel(IDataManager dataManager){
        return new UpdateProfileViewModel(dataManager);
    }
}
