package com.pulse.brag.ui.authentication.profile.changemobile;

import com.pulse.brag.data.IDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alpesh.rathod on 2/16/2018.
 */

@Module
public class ChangeMobNumberModule {

    @Provides
    ChangeMobNumberViewModel provideChangeMobNumberViewModel(IDataManager dataManager){
        return new ChangeMobNumberViewModel(dataManager);
    }
}
