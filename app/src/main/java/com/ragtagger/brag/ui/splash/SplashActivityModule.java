package com.ragtagger.brag.ui.splash;

import com.ragtagger.brag.data.IDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alpesh.rathod on 2/9/2018.
 */

@Module
public class SplashActivityModule {

    @Provides
    SplashViewModel provideSplashViewModel(IDataManager dataManager){
        return new SplashViewModel(dataManager);
    }
}
