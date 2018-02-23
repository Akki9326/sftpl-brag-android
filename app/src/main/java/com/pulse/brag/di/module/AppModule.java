package com.pulse.brag.di.module;

import android.app.Application;
import android.content.Context;

import com.pulse.brag.data.AppDataManager;
import com.pulse.brag.data.IDataManager;
import com.pulse.brag.data.local.AppPrefsManager;
import com.pulse.brag.data.local.IPreferenceManager;
import com.pulse.brag.data.remote.ApiInterface;
import com.pulse.brag.data.remote.AppApiManager;
import com.pulse.brag.data.remote.IApiManager;
import com.pulse.brag.di.ApiBaseUrl;
import com.pulse.brag.di.ApiFullUrl;
import com.pulse.brag.di.ApiVersion;
import com.pulse.brag.di.OSInfo;
import com.pulse.brag.di.PreferenceInfo;
import com.pulse.brag.utils.Constants;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alpesh.rathod on 2/9/2018.
 */

@Module
public class AppModule {

                                            /*Common*/

    //==============================================================================================
    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @OSInfo
    String provideOS(){
        return Constants.ApiHelper.OS;
    }
    //==============================================================================================

                                        /*Preference provider*/

    //==============================================================================================
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

    //==============================================================================================

                                        /*Network provider*/

    //==============================================================================================
    @Provides
    @ApiBaseUrl
    String provideBaseUrlString() {
        return Constants.ApiHelper.BASE_URL;
    }

    @Provides
    @ApiVersion
    String provideApiVersion() {
        return Constants.ApiHelper.API_VERSION;
    }
    @Provides

    @ApiFullUrl
    String provideFullUrlString(@ApiBaseUrl String baseUrl, @ApiVersion String apiVersion) {
        return baseUrl+apiVersion;
    }

    /*@Provides
    @Singleton
    ApiInterface provideApiInterface(ApiClientNew apiClient){
        return apiClient.getApiResp();
    }*/


    @Provides
    @Singleton
    Retrofit provideCall(@ApiFullUrl String fullUrl,IPreferenceManager mPreferencesHelper,@OSInfo String os) {
        return new Retrofit.Builder()
                .baseUrl(fullUrl)
                .client(getHttpClientBuilder(mPreferencesHelper,os))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public ApiInterface providesNetworkService(Retrofit retrofit) {
        return retrofit.create(ApiInterface.class);
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

    //==============================================================================================

                                            /*Data Manager*/

    //==============================================================================================
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

    //==============================================================================================

                                            /*Local method*/

    //==============================================================================================


    private OkHttpClient getHttpClientBuilder(final IPreferenceManager mPreferencesHelper, final String os) {

        // Setup Logging interceptor
        HttpLoggingInterceptor mLoggingInterceptor = new HttpLoggingInterceptor();
        mLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();

                if (!mPreferencesHelper.getAccessToken().isEmpty()) {
                    builder.header(Constants.ApiHelper.MAP_KEY_ACCESS_TOKEN, mPreferencesHelper.getAccessToken());
                }

                if (!mPreferencesHelper.getDeviceType().isEmpty()) {
                    builder.header(Constants.ApiHelper.MAP_KEY_DEVICE_TYPE, mPreferencesHelper.getDeviceType());
                }

                if (!mPreferencesHelper.getDeviceToken().isEmpty()) {
                    builder.header(Constants.ApiHelper.MAP_KEY_DEVICE_TOKEN, mPreferencesHelper.getDeviceToken());
                }


                if (!mPreferencesHelper.getOsVersion().isEmpty()) {
                    builder.header(Constants.ApiHelper.MAP_KEY_OSV, mPreferencesHelper.getOsVersion());
                }

                builder.header(Constants.ApiHelper.MAP_KEY_OS, os);
                builder.header(Constants.ApiHelper.MAP_APP_VERSION, Constants.ApiHelper.APP_VERSION);
                return chain.proceed(builder.build());
            }
        };

        httpClient.addInterceptor(interceptor);
        httpClient.addInterceptor(mLoggingInterceptor);

        return httpClient.build();
    }






}
