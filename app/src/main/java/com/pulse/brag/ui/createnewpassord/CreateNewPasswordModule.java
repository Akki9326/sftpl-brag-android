package com.pulse.brag.ui.createnewpassord;

import com.pulse.brag.data.IDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alpesh.rathod on 2/16/2018.
 */

@Module
public class CreateNewPasswordModule {

    @Provides
    CreateNewPasswordViewModel provideCreateNewPasswordViewModel(IDataManager dataManager){
        return new CreateNewPasswordViewModel(dataManager);
    }
}
