package com.pulse.brag.di.module;

import android.app.Application;
import android.content.Context;

import com.pulse.brag.data.AppDataManager;
import com.pulse.brag.data.IDataManager;
import com.pulse.brag.data.local.AppPrefsManager;
import com.pulse.brag.data.local.IPreferenceManager;
import com.pulse.brag.data.remote.ApiClientNew;
import com.pulse.brag.data.remote.ApiInterface;
import com.pulse.brag.data.remote.AppApiManager;
import com.pulse.brag.data.remote.IApiManager;
import com.pulse.brag.di.ApiInfo;
import com.pulse.brag.di.OSInfo;
import com.pulse.brag.di.PreferenceInfo;
import com.pulse.brag.utils.Constants;

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


    @Provides
    @OSInfo
    String provideOS(){
        return Constants.ApiHelper.OS;
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

    //=====================================================================================


    @Provides
    @ApiInfo
    String provideBaseUrlString() {
        return Constants.ApiHelper.BASE_URL;
    }

    @Provides
    @Singleton
    ApiInterface provideApiInterface(ApiClientNew apiClient){
        return apiClient.getApiResp();
    }

    /**
     *
     * @param appApiManager {@link AppApiManager#(Context, String)}
     * @return IPreferenceManager
     */
    @Provides
    @Singleton
    IApiManager provideApiHelper(AppApiManager appApiManager){
        return appApiManager;
    }

    //=====================================================================================1


    /**
     *
     * @param appDataManager {@link AppDataManager#AppDataManager(Context, IPreferenceManager, IApiManager)}
     * @return IPreferenceManager
     */
    @Provides
    @Singleton
    IDataManager providesDataManager(AppDataManager appDataManager){
        return appDataManager;
    }


}
