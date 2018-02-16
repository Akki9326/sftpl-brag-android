package com.pulse.brag.ui.signup;

import com.pulse.brag.data.IDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alpesh.rathod on 2/15/2018.
 */

@Module
public class SignUpFragmentModule {

    @Provides
    SignUpViewModel provideSignUpViewModel(IDataManager dataManager){
        return new SignUpViewModel(dataManager);
    }
}
