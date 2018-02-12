package com.pulse.brag.ui.login;

import com.pulse.brag.data.IDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alpesh.rathod on 2/12/2018.
 */

@Module
public class LoginFragmentModule {

    @Provides
    LoginViewModel provideLoginViewModel(IDataManager dataManager){
        return new LoginViewModel(dataManager);
    }
}
