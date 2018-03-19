package com.ragtagger.brag.ui.authentication.forgotpassword;

import com.ragtagger.brag.data.IDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alpesh.rathod on 2/14/2018.
 */

@Module
public class ForgotPasswordModule {

    @Provides
    ForgotPasswordViewModel provideLoginViewModel(IDataManager dataManager){
        return new ForgotPasswordViewModel(dataManager);
    }
}
