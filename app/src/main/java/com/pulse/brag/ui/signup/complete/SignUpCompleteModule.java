package com.pulse.brag.ui.signup.complete;

import com.pulse.brag.data.IDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alpesh.rathod on 2/16/2018.
 */

@Module
public class SignUpCompleteModule {

    @Provides
    SignUpCompleteViewModel provideSignUpCompleteViewModel(IDataManager dataManager){
        return new SignUpCompleteViewModel(dataManager);
    }
}
