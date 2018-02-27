package com.pulse.brag.ui.authentication.forgotpassword;

import com.pulse.brag.data.IDataManager;

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
