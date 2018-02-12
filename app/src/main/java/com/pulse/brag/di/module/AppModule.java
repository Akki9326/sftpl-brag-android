package com.pulse.brag.di.module;

import android.app.Application;
import android.content.Context;

import com.pulse.brag.data.AppDataManager;
import com.pulse.brag.data.IDataManager;
import com.pulse.brag.data.local.AppPrefsManager;
import com.pulse.brag.data.local.IPreferenceManager;
import com.pulse.brag.di.PreferenceInfo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alpesh.rathod on 2/9/2018.
 */

@Module
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName(Context context){
        return context.getPackageName()+".PREF_NAME";
    }

    /**
     *
     * @param appPrefsManager {@link AppPrefsManager#AppPrefsManager(Context, String)}
     * @return IPreferenceManager
     */
    @Provides
    @Singleton
    IPreferenceManager providePreferenceHelper(AppPrefsManager appPrefsManager){
        return appPrefsManager;
    }


    /**
     *
     * @param appDataManager {@link AppDataManager#AppDataManager(Context, IPreferenceManager)}
     * @return IPreferenceManager
     */
    @Provides
    @Singleton
    IDataManager providesDataManager(AppDataManager appDataManager){
        return appDataManager;
    }
}
