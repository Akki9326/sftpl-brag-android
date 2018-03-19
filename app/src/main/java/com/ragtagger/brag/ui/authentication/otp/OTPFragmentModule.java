package com.ragtagger.brag.ui.authentication.otp;

import com.ragtagger.brag.data.IDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alpesh.rathod on 2/15/2018.
 */

@Module
public class OTPFragmentModule {

    @Provides
    OTPViewModel provideOTPViewModel(IDataManager dataManager){
        return new OTPViewModel(dataManager);
    }
}
