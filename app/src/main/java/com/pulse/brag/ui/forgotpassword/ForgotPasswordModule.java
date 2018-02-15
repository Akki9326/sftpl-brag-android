package com.pulse.brag.ui.forgotpassword;

import com.pulse.brag.data.IDataManager;
import com.pulse.brag.ui.login.LoginViewModel;

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
