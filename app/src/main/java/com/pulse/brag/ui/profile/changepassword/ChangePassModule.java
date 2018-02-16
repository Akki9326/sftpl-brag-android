package com.pulse.brag.ui.profile.changepassword;

import com.pulse.brag.data.IDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alpesh.rathod on 2/15/2018.
 */

@Module
public class ChangePassModule {

    @Provides
    ChangePassViewModel provideChangePassViewModel(IDataManager dataManager){
        return new ChangePassViewModel(dataManager);
    }
}
